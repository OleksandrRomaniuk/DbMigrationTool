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
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of foreign keys.
 */
@LoaderAnnotation(NameConstants.FOREIGN_KEY)
public class ForeignKeyLoader extends AbstractLoader {

    /*public ForeignKeyLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container fkContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container fkContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) fkContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_NAME);
            String tableName = (String) fkContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) fkContainer.getParent().getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            List<String> attributes = MySQLAttributeFactory.getInstance().getAttributes(this);
            String query = String.format(MySQLQueries.FOREIGNKEYDETAILED, super.listToString(attributes),
                schemaName, tableName, elementName);
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container childContainer = new Container();
                fkContainer.addChild(childContainer);
                childContainer.setName(elementName);
                childContainer.addAttribute(ConstraintAttributes.CONSTRAINT_NAME, elementName);
                childContainer.addAttribute(CustomAttributes.IS_CATEGORY, false);
                for (String attribute : attributes) {
                    childContainer.addAttribute(attribute, resultSet.getString(attribute));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container fkContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(fkContainer);
        this.detailedLoad(fkContainer);
    }
}
