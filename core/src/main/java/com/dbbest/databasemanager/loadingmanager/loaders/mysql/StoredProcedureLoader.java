package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
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

    public StoredProcedureLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        try {
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.STORED_PROCEDURE);
            String query = String.format(lazyLoaderQuery,
                storedProcedureContainer.getParent().getParent().getAttributes().get(TableAttributes.TABLE_SCHEMA));
            super.executeLazyLoaderQuery(storedProcedureContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) storedProcedureContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance()
                .getSqlQueriesDetailLoader().get(LoaderPrinterName.STORED_PROCEDURE);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery,
                listRepresentationOfAttributes, storedProcedureContainer.getParent().getParent()
                    .getAttributes().get(TableAttributes.TABLE_SCHEMA),
                elementName);
            super.executeDetailedLoaderQuery(storedProcedureContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        new ProcedureFunctionParameteresLoader(super.getContext()).fullLoad(storedProcedureContainer);
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
