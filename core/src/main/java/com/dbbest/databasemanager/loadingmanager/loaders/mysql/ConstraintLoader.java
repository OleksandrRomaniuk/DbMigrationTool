package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
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
@LoaderAnnotation(LoaderPrinterName.CONSTRAINT)
public class ConstraintLoader extends AbstractLoader {
    public ConstraintLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container constraintContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container constraintContainer) throws DatabaseException, ContainerException {
        try {
            String constraintName = (String) constraintContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_NAME);
            String tableName = (String) constraintContainer.getParent()
                .getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) constraintContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
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
