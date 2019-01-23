package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ConstraintsLoader implements Loader {
    @Override
    public void lazyLoad(Connection connection, Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        if (constraintCategoryContainer.getName() == null || constraintCategoryContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the table does not contain the name.");
        }
        try {
            String query =
                String.format(MySqlQueriesConstants.TableConstraintsSelectAll.getQuery(),
                    constraintCategoryContainer.getParent().getParent().getParent().getName(),
                    constraintCategoryContainer.getParent().getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container constraintContainer = new Container();
                constraintContainer.setName(resultSet.getString(TableConstraintAttributes.CONSTRAINT_NAME.getElement()));
                constraintCategoryContainer.addChild(constraintContainer);

                for (TableConstraintAttributes attributeKey : TableConstraintAttributes.values()) {
                    constraintContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container constraintContainer) throws DatabaseException, ContainerException {

        if (constraintContainer.getName() == null || constraintContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the constraint category does not contain the name.");
        }

        try {
            String query =
                String.format(MySqlQueriesConstants.KeyColumnUSageConstarintsSelectAll.getQuery(),
                    constraintContainer.getParent().getParent().getParent().getParent().getName(),
                    constraintContainer.getParent().getParent().getName(),
                    constraintContainer.getName());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container constraint = new Container();
                constraint.setName(resultSet.getString(FkAttributes.CONSTRAINT_NAME.getElement()));
                constraintContainer.addChild(constraint);

                for (FkAttributes attributeKey : FkAttributes.values()) {
                    constraint.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(connection, constraintCategoryContainer);
        List<Container> constraintContainers = constraintCategoryContainer.getChildren();
        for (Container constraint : constraintContainers) {
            detailedLoad(connection, constraint);
        }
    }
}
