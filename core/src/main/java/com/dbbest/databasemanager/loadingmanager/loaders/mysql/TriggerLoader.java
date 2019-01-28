package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LoaderAnnotation(LoaderPrinterName.TRIGGER)
public class TriggerLoader extends AbstractLoader {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Container categoryTriggers) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadTableChildren(categoryTriggers);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container triggerContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableChildren(triggerContainer);
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
