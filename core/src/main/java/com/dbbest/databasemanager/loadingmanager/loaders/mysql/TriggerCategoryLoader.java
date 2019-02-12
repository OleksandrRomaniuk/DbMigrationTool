package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.TABLE_TRIGGERS)
public class TriggerCategoryLoader extends AbstractLoader {
    public TriggerCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) triggerCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) triggerCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String query = String.format(MySQLQueries.TRIGGERLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(triggerCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        if (triggerCategoryContainer.hasChildren()) {
            for (Container trigger : (List<Container>) triggerCategoryContainer.getChildren()) {
                new TriggerLoader(super.getContext()).detailedLoad(trigger);
            }
        }
    }

    @Override
    public void fullLoad(Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(triggerCategoryContainer);
        if (triggerCategoryContainer.hasChildren()) {
            for (Container trigger : (List<Container>) triggerCategoryContainer.getChildren()) {
                new TriggerLoader(super.getContext()).fullLoad(trigger);
            }
        }
    }
}
