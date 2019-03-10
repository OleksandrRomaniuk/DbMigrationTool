package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.databasemanager.dbmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the schema.
 */
@LoaderAnnotation(NameConstants.SCHEMA)
public class SchemaLoader extends AbstractLoader {

    /*public SchemaLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        if (!schemaContainer.hasLabel()) {
            schemaContainer.setName(NameConstants.SCHEMA);
        }
        try {
            String schemaName = super.getConnection().getCatalog();
            schemaContainer.addAttribute(CustomAttributes.IS_CATEGORY, false);
            schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, schemaName);

            TableCategoryLoader tableCategoryLoader = new TableCategoryLoader();
            tableCategoryLoader.setConnection(super.getConnection());
            this.lazyLoadCategory(schemaContainer, NameConstants.TABLES,
                    tableCategoryLoader, NameConstants.TABLE);

            ViewCategoryLoader viewCategoryLoader = new ViewCategoryLoader();
            viewCategoryLoader.setConnection(super.getConnection());
            this.lazyLoadCategory(schemaContainer, NameConstants.VIEWS,
                    viewCategoryLoader, NameConstants.VIEW);

            FunctionCategoryLoader functionCategoryLoader = new FunctionCategoryLoader();
            functionCategoryLoader.setConnection(super.getConnection());
            this.lazyLoadCategory(schemaContainer, NameConstants.FUNCTIONS,
                    functionCategoryLoader, NameConstants.FUNCTION);

            StoredProcedureCategoryLoader storedProcedureCategoryLoader = new StoredProcedureCategoryLoader();
            storedProcedureCategoryLoader.setConnection(super.getConnection());
            this.lazyLoadCategory(schemaContainer, NameConstants.STORED_PROCEDURES,
                    storedProcedureCategoryLoader, NameConstants.STORED_PROCEDURE);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
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

        TableCategoryLoader tableCategoryLoader = new TableCategoryLoader();
        tableCategoryLoader.setConnection(super.getConnection());
        tableCategoryLoader.fullLoad(schemaContainer.getChildByName(NameConstants.TABLES));

        ViewCategoryLoader viewCategoryLoader = new ViewCategoryLoader();
        viewCategoryLoader.setConnection(super.getConnection());
        viewCategoryLoader.fullLoad(schemaContainer.getChildByName(NameConstants.VIEWS));

        StoredProcedureCategoryLoader storedProcedureCategoryLoader = new StoredProcedureCategoryLoader();
        storedProcedureCategoryLoader.setConnection(super.getConnection());
        storedProcedureCategoryLoader
            .fullLoad(schemaContainer.getChildByName(NameConstants.STORED_PROCEDURES));

        FunctionCategoryLoader functionCategoryLoader = new FunctionCategoryLoader();
        functionCategoryLoader.setConnection(super.getConnection());
        functionCategoryLoader.fullLoad(schemaContainer.getChildByName(NameConstants.FUNCTIONS));
    }

    private void lazyLoadCategory(Container schemaContainer, String categoryName, Loader categoryLoader, String childType)
        throws ContainerException, DatabaseException {
        Container category = new Container();
        category.setName(categoryName);
        category.addAttribute(CustomAttributes.IS_CATEGORY, true);
        category.addAttribute(CustomAttributes.CHILD_TYPE, childType);
        schemaContainer.addChild(category);
        categoryLoader.lazyLoad(category);
    }
}
