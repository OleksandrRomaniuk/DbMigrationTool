package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ProcedureFunctionParameterAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ProcedureFunctionParameteresLoader implements Loader {
    @Override
    public void lazyLoad(Connection connection, Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        if (procedureFunctionContainer.getName() == null || procedureFunctionContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container with Procedure or Function does not contain the name.");
        }
        try {
            String query =
                String.format(MySqlQueriesConstants.ProcedureFunctionParametersGetListOfParameters.getQuery(),
                    procedureFunctionContainer.getParent().getParent().getName(),
                    procedureFunctionContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container parameter = new Container();
                parameter.setName(resultSet.getString(ProcedureFunctionParameterAttributes.PARAMETER_NAME.getElement()));
                procedureFunctionContainer.addChild(parameter);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container parameter) throws DatabaseException, ContainerException {
        try {

            String query =
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
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container procedureFunctionContainer) throws DatabaseException, ContainerException {
        lazyLoad(connection, procedureFunctionContainer);
        List<Container> parameters = procedureFunctionContainer.getChildren();
        if (parameters != null && !parameters.isEmpty()) {
            for (Container parameter : parameters) {
                detailedLoad(connection, parameter);
            }
        }
    }
}
