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

    public SchemaLoader(Connection connection) {
        super(connection);
    }

    @Override
    public void lazyLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        if (!schemaContainer.hasName()) {
            schemaContainer.setName(NameConstants.SCHEMA);
        }
        try {
            String schemaName = super.getConnection().getCatalog();
            schemaContainer.addAttribute(CustomAttributes.IS_CATEGORY, false);
            schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, schemaName);
            this.lazyLoadCategory(schemaContainer, NameConstants.TABLES,
                new TableCategoryLoader(super.getConnection()), NameConstants.TABLE);
            this.lazyLoadCategory(schemaContainer, NameConstants.VIEWS,
                new ViewCategoryLoader(super.getConnection()), NameConstants.VIEW);
            this.lazyLoadCategory(schemaContainer, NameConstants.FUNCTIONS,
                new FunctionCategoryLoader(super.getConnection()), NameConstants.FUNCTION);
            this.lazyLoadCategory(schemaContainer, NameConstants.STORED_PROCEDURES,
                new StoredProcedureCategoryLoader(super.getConnection()), NameConstants.STORED_PROCEDURE);
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
        new TableCategoryLoader(super.getConnection()).fullLoad(schemaContainer.getChildByName(NameConstants.TABLES));
        new ViewCategoryLoader(super.getConnection()).fullLoad(schemaContainer.getChildByName(NameConstants.VIEWS));
        new StoredProcedureCategoryLoader(super.getConnection())
            .fullLoad(schemaContainer.getChildByName(NameConstants.STORED_PROCEDURES));
        new FunctionCategoryLoader(super.getConnection()).fullLoad(schemaContainer.getChildByName(NameConstants.FUNCTIONS));
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
