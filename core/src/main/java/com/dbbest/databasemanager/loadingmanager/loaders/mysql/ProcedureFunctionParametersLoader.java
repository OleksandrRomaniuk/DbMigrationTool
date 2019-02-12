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
import java.util.logging.Level;

/**
 * The class which loads details of parameters of functions of stored procedures.
 */
@LoaderAnnotation(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER)
public class ProcedureFunctionParametersLoader extends AbstractLoader {
    public ProcedureFunctionParametersLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container parameterContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container parameterContainer) throws DatabaseException, ContainerException {
        try {
            String parameterName = (String) parameterContainer.getParent().getAttributes()
                .get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
            String procedureFunctionName = (String) parameterContainer.getParent().getAttributes()
                .get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String listOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERDETAILED, listOfAttributes,
                parameterContainer.getParent().getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA),
                procedureFunctionName, parameterName);
            this.executeDetailedLoaderQuery(parameterContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container parameterContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(parameterContainer);
        this.detailedLoad(parameterContainer);
    }
}
