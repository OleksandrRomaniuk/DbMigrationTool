package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the stored procedures.
 */
@LoaderAnnotation(LoaderPrinterName.STORED_PROCEDURE)
public class StoredProcedureLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container storedProceduresCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.STORED_PROCEDURE);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName());
            super.executeLazyLoaderQuery(storedProceduresCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container storedProcedure) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) storedProcedure.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance()
                .getSqlQueriesDetailLoader().get(LoaderPrinterName.STORED_PROCEDURE);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes, Context.getInstance().getSchemaName(), elementName);
            super.executeDetailedLoaderQuery(storedProcedure, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        new ProcedureFunctionParameteresLoader().fullLoad(storedProcedure);
    }

    @Override
    public void fullLoad(Container storedProceduresCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(storedProceduresCategoryContainer);
        List<Container> storedProcedures = storedProceduresCategoryContainer.getChildren();
        if (storedProcedures != null && !storedProcedures.isEmpty()) {
            for (Container storedProcedure : storedProcedures) {
                detailedLoad(storedProcedure);
            }
        }
    }
}
