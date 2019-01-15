package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.StoredProceduresAndFunctionsAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class StoredProcedureLoader implements Loaders {
    @Override
    public void lazyLoad(Connection connection, Container storedProceduresCategoryContainer) throws DatabaseException, ContainerException {
        if (storedProceduresCategoryContainer.getName() == null || storedProceduresCategoryContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Stored procedures does not contain the name.");
        }
        try {
            ResultSet storedProcedures = connection.getMetaData().getProcedures((String) storedProceduresCategoryContainer
                .getParent().getAttributes().get(SchemaAttributes.SCHEMA_CATALOG_NAME.getElement()), null, null);
            while (storedProcedures.next()) {
                Container storedProcedure = new Container();
                storedProcedure.setName(storedProcedures.getString(StoredProceduresAndFunctionsAttributes.SPECIFIC_NAME.getElement());
                storedProceduresCategoryContainer.addChild(storedProcedure);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container storedProcedure) throws DatabaseException, ContainerException {
        if (storedProcedure.getName() == null || storedProcedure.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The view container does not contain the name.");
        }

        try {
            String query =
                String.format(MySqlQueriesConstants.StoredProceduresAndFunctionsSchemaSelectAll.getQuery(),
                    storedProcedure.getParent().getParent().getName(),
                    "PROCEDURE",
                    storedProcedure.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (StoredProceduresAndFunctionsAttributes attributeKey : StoredProceduresAndFunctionsAttributes.values()) {
                storedProcedure.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container tree) {

    }
}
