package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the schema.
 */
@LoaderAnnotation(LoaderPrinterName.SCHEMA)
public class SchemaLoader extends AbstractLoader {

    private String schemaName = super.getContext().getSchemaName();

    public SchemaLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        if (!schemaContainer.hasName()) {
            schemaContainer.setName(LoaderPrinterName.SCHEMA);
        }
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, schemaName);
        this.lazyLoadCategory(schemaContainer, LoaderPrinterName.TABLES, new TableCategoryLoader(super.getContext()));
        this.lazyLoadCategory(schemaContainer, LoaderPrinterName.VIEWS, new ViewCategoryLoader(super.getContext()));
        this.lazyLoadCategory(schemaContainer, LoaderPrinterName.FUNCTIONS, new FunctionCategoryLoader(super.getContext()));
        this.lazyLoadCategory(schemaContainer, LoaderPrinterName.STORED_PROCEDURES,
            new StoredProcedureCategoryLoader(super.getContext()));
    }

    @Override
    public void detailedLoad(Container schemaContainer) throws DatabaseException {
        List<String> attributeNames = MySQLAttributeFactory.getInstance().getAttributes(this);
        String stringRepresentationOfOfAttributes = super.listToString(attributeNames);

        try {
            String query = String.format(MySQLQueries.SCHEMADETAILED, stringRepresentationOfOfAttributes,
                schemaContainer.getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeDetailedLoaderQuery(schemaContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        this.lazyLoad(schemaContainer);
        this.detailedLoad(schemaContainer);
        new TableCategoryLoader(super.getContext()).fullLoad(schemaContainer.getChildByName(LoaderPrinterName.TABLES));
        new ViewCategoryLoader(super.getContext()).fullLoad(schemaContainer.getChildByName(LoaderPrinterName.VIEWS));
        new StoredProcedureCategoryLoader(super.getContext())
            .fullLoad(schemaContainer.getChildByName(LoaderPrinterName.STORED_PROCEDURES));
        new FunctionCategoryLoader(super.getContext()).fullLoad(schemaContainer.getChildByName(LoaderPrinterName.FUNCTIONS));
    }

    private void lazyLoadCategory(Container schemaContainer, String categoryName, Loader categoryLoader)
        throws ContainerException, DatabaseException {
        Container category = new Container();
        category.setName(categoryName);
        schemaContainer.addChild(category);
        categoryLoader.lazyLoad(category);
    }
}
