package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TriggerAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class-loader of the mysql triggers.
 */
@LoaderAnnotation(LoaderPrinterName.TRIGGER)
public class TriggerLoader extends AbstractLoader {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Container categoryTriggers) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) categoryTriggers.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.TRIGGER);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), tableName);
            super.executeLazyLoaderQuery(categoryTriggers, query);
            //super.executeLazyLoadTableChildren(categoryTriggers);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container triggerContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) triggerContainer.getAttributes().get(TriggerAttributes.TRIGGER_NAME);
            String tableName = (String) triggerContainer.getParent().getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.TRIGGER);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), tableName, elementName);
            super.executeDetailedLoaderQuery(triggerContainer, query);
            //super.executeDetailedLoadTableChildren(triggerContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container categoryTriggers) throws DatabaseException, ContainerException {
        lazyLoad(categoryTriggers);
        List<Container> trigers = categoryTriggers.getChildren();
        if (trigers != null && !trigers.isEmpty()) {
            for (Container triger : trigers) {
                detailedLoad(triger);
            }
        }
    }
}
