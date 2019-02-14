package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the stored procedure category.
 */
@LoaderAnnotation(NameConstants.STORED_PROCEDURES)
public class StoredProcedureCategoryLoader extends AbstractLoader {
    public StoredProcedureCategoryLoader(Connection connection) {
        super(connection);
    }

    @Override
    public void lazyLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.STOREDPROCEDURELAZY,
                storedProcedureCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(storedProcedureCategoryContainer, query,
                NameConstants.STORED_PROCEDURE, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        if (storedProcedureCategoryContainer.hasChildren()) {
            for (Container table : (List<Container>) storedProcedureCategoryContainer.getChildren()) {
                new StoredProcedureLoader(super.getConnection()).detailedLoad(table);
            }
        }
    }

    @Override
    public void fullLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        if (storedProcedureCategoryContainer.hasChildren()) {
            for (Container storedProcedure : (List<Container>) storedProcedureCategoryContainer.getChildren()) {
                new StoredProcedureLoader(super.getConnection()).fullLoad(storedProcedure);
            }
        }
    }
}
