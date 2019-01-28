package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.FOREIGN_KEY)
public class ForeignKeyLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {

        try {
            super.executeLazyLoadTableChildren(containerOfCategoryFks);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container fkContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableChildren(fkContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {
        lazyLoad(containerOfCategoryFks);
        List<Container> foreignKeys = containerOfCategoryFks.getChildren();
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (Container fk : foreignKeys) {
                detailedLoad(fk);
            }
        }
    }
}
