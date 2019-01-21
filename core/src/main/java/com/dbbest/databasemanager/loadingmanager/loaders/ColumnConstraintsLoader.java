package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.KeyColumnUsageAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.ColumnTagNameConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ColumnConstraintsLoader implements Loader {
    @Override
    public void lazyLoad(Connection connection, Container columnContainer) throws DatabaseException, ContainerException {
        if (columnContainer.getName() == null || columnContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the column does not contain the name.");
        }
        try {
            Container constraintCategory = new Container();
            constraintCategory.setName(ColumnTagNameConstants.ConstraintCategory.getElement());
            columnContainer.addChild(constraintCategory);

            String query =
                String.format(MySqlQueriesConstants.KeyColumnUSageConstarintsSelectAll.getQuery(),
                    columnContainer.getParent().getParent().getParent().getParent().getName(),
                    columnContainer.getParent().getParent().getName(),
                    columnContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container constraintContainer = new Container();
                constraintContainer.setName(resultSet.getString(TableConstraintAttributes.CONSTRAINT_NAME.getElement()));
                constraintCategory.addChild(constraintContainer);
                for (FkAttributes attributeKey : FkAttributes.values()) {
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
                String.format(MySqlQueriesConstants.TableConstraintsSelectAll.getQuery(),
                    constraintContainer.getParent().getParent().getParent().getParent().getParent().getParent().getName(),
                    constraintContainer.getParent().getParent().getParent().getParent().getName(),
                    constraintContainer.getName());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                constraintContainer.addAttribute(KeyColumnUsageAttributes.CONSTRAINT_TYPE.getElement(),
                    resultSet.getString(KeyColumnUsageAttributes.CONSTRAINT_TYPE.getElement()));
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container columnContainer) throws DatabaseException, ContainerException {
        lazyLoad(connection, columnContainer);
        List<Container> constraintContainers = columnContainer
            .getChildByName(ColumnTagNameConstants.ConstraintCategory.getElement()).getChildren();
        for (Container constraint : constraintContainers) {
            detailedLoad(connection, constraint);
        }
    }
}
