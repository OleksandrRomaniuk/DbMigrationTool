package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
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
            super.executeLazyLoadProcedureFunctionParameters(procedureFunctionContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container parameter) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadProcedureFunctionParameter(parameter);
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
