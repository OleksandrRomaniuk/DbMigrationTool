package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TriggerAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class-loader of the mysql triggers.
 */
@LoaderAnnotation(NameConstants.TRIGGER)
public class TriggerLoader extends AbstractLoader {
    private static final Logger logger = Logger.getLogger("Database logger");

    /*public TriggerLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container triggerContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container triggerContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) triggerContainer.getAttributes().get(TriggerAttributes.TRIGGER_NAME);
            String tableName = (String) triggerContainer.getParent().getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) triggerContainer.getParent().getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.TRIGGERDEATILED, listRepresentationOfAttributes,
                schemaName, tableName, elementName);
            super.executeDetailedLoaderQuery(triggerContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container triggerContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(triggerContainer);
        this.detailedLoad(triggerContainer);
    }
}
