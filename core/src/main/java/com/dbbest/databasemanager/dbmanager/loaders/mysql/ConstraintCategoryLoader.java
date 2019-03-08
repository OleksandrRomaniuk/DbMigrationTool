package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the table constraints.
 */
@LoaderAnnotation(NameConstants.TABLE_CONSTRAINTS)
public class ConstraintCategoryLoader extends AbstractLoader {

    /*public ConstraintCategoryLoader(Connection connection) {
        super(connection);
    }*/

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) constraintCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) constraintCategoryContainer.getParent().getParent().getParent()
                    .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.TABLECONSTRAINTLAZY,
                    super.listToString(ConstraintAttributes.getListOfLazyLoadAttributeNames()), schemaName, tableName);
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childNode = new Container();
                childNode.setName(NameConstants.CONSTRAINT);
                constraintCategoryContainer.addChild(childNode);
                childNode.addAttribute(CustomAttributes.IS_CATEGORY, false);
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
                ConstraintLoader constraintLoader = new ConstraintLoader();
                constraintLoader.setConnection(super.getConnection());
                constraintLoader.detailedLoad(tableConstraint);
            }
        }
    }

    @Override
    public void fullLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        if (constraintCategoryContainer.hasChildren()) {
            for (Container constraint : (List<Container>) constraintCategoryContainer.getChildren()) {
                ConstraintLoader constraintLoader = new ConstraintLoader();
                constraintLoader.setConnection(super.getConnection());
                constraintLoader.fullLoad(constraint);
            }
        }
    }
}

