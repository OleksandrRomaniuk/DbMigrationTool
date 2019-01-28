package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.INDEX)
public class IndexLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container indexCategory) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadTableChildren(indexCategory);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container indexContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableChildren(indexContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container indexCategory) throws DatabaseException, ContainerException {
        lazyLoad(indexCategory);
        List<Container> indexes = indexCategory.getChildren();
        if (indexes != null && !indexes.isEmpty()) {
            for (Container index : indexes) {
                detailedLoad(index);
            }
        }
    }
}
