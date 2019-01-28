package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER)
public class ProcedureFunctionParameteresLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        /*if (procedureFunctionContainer.getName() == null || procedureFunctionContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container with Procedure or Function does not contain the name.");
        }*/
        try {
            super.executeLazyLoadProcedureFunctionParameters(procedureFunctionContainer);
            /*String query =
                String.format(MySqlQueriesConstants.ProcedureFunctionParametersGetListOfParameters.getQuery(),
                    procedureFunctionContainer.getParent().getParent().getName(),
                    procedureFunctionContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container parameter = new Container();
                parameter.setName(resultSet.getString(ProcedureFunctionParameterAttributes.PARAMETER_NAME.getElement()));
                procedureFunctionContainer.addChild(parameter);
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container parameter) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadProcedureFunctionParameter(parameter);
            /*String query =
                String.format(MySqlQueriesConstants.ProcedureFunctionParametersSelectAll.getQuery(),
                    parameter.getParent().getParent().getParent().getName(),
                    parameter.getParent().getName(),
                    parameter.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (ProcedureFunctionParameterAttributes attributeKey : ProcedureFunctionParameterAttributes.values()) {
                    parameter.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
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