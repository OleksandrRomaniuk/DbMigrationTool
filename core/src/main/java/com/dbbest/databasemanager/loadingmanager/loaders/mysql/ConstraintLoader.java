package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.*;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class loader of constraints of tables.
 */
@LoaderAnnotation(LoaderPrinterName.CONSTRAINT)
public class ConstraintLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) constraintCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String query = String.format(
                MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.CONSTRAINT),
                super.getListOfAttributes(new ConstraintAttributes().getListOfLazyLoadAttributeNames()),
                Context.getInstance().getSchemaName(),
                tableName);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childNode = new Container();
                childNode.setName(LoaderPrinterName.CONSTRAINT);
                childNode.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.CONSTRAINT
                    + resultSet.getString(ConstraintAttributes.CONSTRAINT_NAME));
                constraintCategoryContainer.addChild(childNode);
                for (String attributeName : new ConstraintAttributes().getListOfLazyLoadAttributeNames()) {
                    childNode.addAttribute(attributeName, resultSet.getString(attributeName));
                }
            }
            //super.executeLazyLoadTableConstraint(constraintCategoryContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container constraintContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) constraintContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_NAME);
            String tableName = (String) constraintContainer.getParent()
                .getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.CONSTRAINT);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), tableName, elementName);
            //super.executeDetailedLoaderConstraintQuery(constraintContainer, query);
            //super.executeDetailedLoadTableConstraint(constraintContainer);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childNode = new Container();
                childNode.setName(ConstraintAttributes.CONSTRAINT_NAME);
                constraintContainer.addChild(childNode);
                for (String attribute : MySQLAttributeFactory.getInstance().getAttributes(this)) {
                    childNode.addAttribute(attribute, resultSet.getString(attribute));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(constraintCategoryContainer);
        List<Container> constraintContainers = constraintCategoryContainer.getChildren();
        if (constraintContainers != null && !constraintContainers.isEmpty()) {
            for (Container constraint : constraintContainers) {
                detailedLoad(constraint);
            }
        }
    }
}
