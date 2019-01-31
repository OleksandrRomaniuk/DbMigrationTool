package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the table columns.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE_COLUMN)
public class TableColumnLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container categoryColumnsContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadTableChildren(categoryColumnsContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container columnContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableChildren(columnContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container categoryColumnsContainer) throws DatabaseException, ContainerException {
        lazyLoad(categoryColumnsContainer);
        List<Container> columns = categoryColumnsContainer.getChildren();
        if (columns != null && !columns.isEmpty()) {
            for (Container column : columns) {
                detailedLoad(column);
            }
        }
    }
}
