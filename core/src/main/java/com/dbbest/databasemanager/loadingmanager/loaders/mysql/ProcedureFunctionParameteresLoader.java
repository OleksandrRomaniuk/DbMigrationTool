package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
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
    @Override
    public void lazyLoad(Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        try {
            String procedureFunctionName = (String) procedureFunctionContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader()
                .get(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), procedureFunctionName);
            super.executeLazyLoaderQuery(procedureFunctionContainer, query);
            //super.executeLazyLoadProcedureFunctionParameters(procedureFunctionContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container parameter) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) parameter.getAttributes()
                .get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
            String procedureFunctionName = (String) parameter.getParent().getAttributes()
                .get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader()
                .get(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), procedureFunctionName, elementName);
            this.executeDetailedLoaderQuery(parameter, query);
            //super.executeDetailedLoadProcedureFunctionParameter(parameter);
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
