package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionProcedureParameterAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class which loads details of parameters of functions of stored procedures.
 */
@LoaderAnnotation(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER)
public class ProcedureFunctionParameteresLoader extends AbstractLoader {
    public ProcedureFunctionParameteresLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        try {
            String procedureFunctionName = (String) procedureFunctionContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader()
                .get(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER);
            String query = String.format(lazyLoaderQuery,
                procedureFunctionContainer.getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA),
                procedureFunctionName);
            super.executeLazyLoaderQuery(procedureFunctionContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container parameterContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) parameterContainer.getAttributes()
                .get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
            String procedureFunctionName = (String) parameterContainer.getParent().getAttributes()
                .get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader()
                .get(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER);
            String listOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                parameterContainer.getParent().getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA),
                procedureFunctionName, elementName);
            this.executeDetailedLoaderQuery(parameterContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        lazyLoad(procedureFunctionContainer);
        List<Container> parameters = procedureFunctionContainer.getChildren();
        if (parameters != null && !parameters.isEmpty()) {
            for (Container parameter : parameters) {
                detailedLoad(parameter);
            }
        }
    }
}
