package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
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
import java.util.logging.Level;

/**
 * The class loader of constraints of tables.
 */
@LoaderAnnotation(NameConstants.CONSTRAINT)
public class ConstraintLoader extends AbstractLoader {
    /*public ConstraintLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container constraintContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container constraintContainer) throws DatabaseException, ContainerException {
        try {
            String constraintName = (String) constraintContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_NAME);
            String tableName = (String) constraintContainer.getParent().getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) constraintContainer.getParent().getParent().getParent()
                .getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(MySQLQueries.TABLECONSTRAINTDETAIED, listRepresentationOfAttributes,
                schemaName, tableName, constraintName);
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childNode = new Container();
                childNode.setName((String) constraintContainer.getAttributes()
                    .get(ConstraintAttributes.CONSTRAINT_NAME));
                childNode.addAttribute(ConstraintAttributes.CONSTRAINT_NAME, (String) constraintContainer.getAttributes()
                    .get(ConstraintAttributes.CONSTRAINT_NAME));
                childNode.addAttribute(CustomAttributes.IS_CATEGORY, false);
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
    public void fullLoad(Container constraintContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(constraintContainer);
        this.detailedLoad(constraintContainer);
    }
}
