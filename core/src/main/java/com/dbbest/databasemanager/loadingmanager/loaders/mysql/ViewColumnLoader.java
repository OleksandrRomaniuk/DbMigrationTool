package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.VIEW_COLUMN)
public class ViewColumnLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadViewColumns(viewContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container viewColumnContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadViewColumns(viewColumnContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container viewContainer) throws DatabaseException, ContainerException {
        lazyLoad(viewContainer);
        List<Container> columns = viewContainer.getChildren();
        if (columns != null && !columns.isEmpty()) {
            for (Container column : columns) {
                detailedLoad(column);
            }
        }
    }
}
