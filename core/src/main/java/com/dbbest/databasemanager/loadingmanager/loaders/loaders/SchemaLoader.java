package com.dbbest.databasemanager.loadingmanager.loaders.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderTypeEnum;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.support.ContainerValidator;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

@com.dbbest.databasemanager.loadingmanager.annotations.Loader(LoaderTypeEnum.Schema)
public class SchemaLoader implements Loader {

    @Override
    public void lazyLoad(Connection connection, Container schemaContainer) throws ContainerException {
        if ((!new ContainerValidator().ifContainerContainsSchemaName(schemaContainer))) {
            throw new ContainerException(Level.SEVERE, "The schema container does not contain the schema name");
        }

        for (SchemaCategoriesTagNameConstants schemaChildName : SchemaCategoriesTagNameConstants.values()) {
            Container child = new Container();
            child.setName(schemaChildName.getElement());
            schemaContainer.addChild(child);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container schemaContainer) throws DatabaseException, ContainerException {

        try {
            if (schemaContainer.getName() == null || schemaContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The schema container does not contain the schema name");
            }
            String informationSchemataSelectAllQuery =
                String.format(MySqlQueriesConstants.IinformationSchemaSelectAll.getQuery(), connection.getCatalog());
            PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
            ResultSet schemaAttributes = preparedStatement.executeQuery();

            if (schemaAttributes.next()) {
                for (SchemaAttributes attributeKey : SchemaAttributes.values()) {
                    schemaContainer.addAttribute(attributeKey.getElement(), schemaAttributes.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container schemaContainer) throws ContainerException, DatabaseException {
        lazyLoad(connection, schemaContainer);
        detailedLoad(connection, schemaContainer);
        new TableLoader().fullLoad(connection, schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()));
        new ViewLoader().fullLoad(connection, schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Views.getElement()));
        new StoredProcedureLoader().fullLoad(connection, schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Stored_Procedures.getElement()));
        new FunctionLoader().fullLoad(connection, schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Functions.getElement()));
    }
}