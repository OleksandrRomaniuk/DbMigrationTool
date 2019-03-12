package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TriggerAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the trigger category.
 */
@LoaderAnnotation(NameConstants.TABLE_TRIGGERS)
public class TriggerCategoryLoader extends AbstractLoader {

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) triggerCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) triggerCategoryContainer.getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.TRIGGERLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(triggerCategoryContainer, query,
                NameConstants.TRIGGER, TriggerAttributes.TRIGGER_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        if (triggerCategoryContainer.hasChildren()) {
            for (Container trigger : (List<Container>) triggerCategoryContainer.getChildren()) {
                TriggerLoader triggerLoader = new TriggerLoader();
                triggerLoader.setConnection(super.getConnection());
                triggerLoader.detailedLoad(trigger);
            }
        }
    }

    @Override
    public void fullLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        if (triggerCategoryContainer.hasChildren()) {
            for (Container trigger : (List<Container>) triggerCategoryContainer.getChildren()) {
                TriggerLoader triggerLoader = new TriggerLoader();
                triggerLoader.setConnection(super.getConnection());
                triggerLoader.fullLoad(trigger);
            }
        }
    }
}
