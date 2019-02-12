package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ConstraintCategoryLoader extends AbstractLoader {

    public ConstraintCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {

        if (!constraintCategoryContainer.hasName()) {
            constraintCategoryContainer.setName(LoaderPrinterName.TABLE_CONSTRAINTS);
        }
        constraintCategoryContainer.addAttribute(CustomAttributes.IS_CATEGORY, true);
        constraintCategoryContainer.addAttribute(CustomAttributes.CHILD_TYPE, LoaderPrinterName.CONSTRAINT);

        try {
            String tableName = (String) constraintCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) constraintCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String query = String.format(
                MySQLQueries.TABLECONSTRAINTLAZY,
                super.listToString(ConstraintAttributes.getListOfLazyLoadAttributeNames()),
                schemaName,
                tableName);

            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childNode = new Container();
                childNode.setName(LoaderPrinterName.CONSTRAINT);
                constraintCategoryContainer.addChild(childNode);
                for (String attribute : ConstraintAttributes.getListOfLazyLoadAttributeNames()) {
                    childNode.addAttribute(attribute, resultSet.getString(attribute));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        if (constraintCategoryContainer.hasChildren()) {
            for (Container tableConstraint : (List<Container>) constraintCategoryContainer.getChildren()) {
                new ConstraintLoader(super.getContext()).detailedLoad(tableConstraint);
            }
        }
    }

    @Override
    public void fullLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(constraintCategoryContainer);
        if (constraintCategoryContainer.hasChildren()) {
            for (Container constraint : (List<Container>) constraintCategoryContainer.getChildren()) {
                new ConstraintLoader(super.getContext()).fullLoad(constraint);
            }
        }
    }
}
