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
 * The class-loader of the mysql views.
 */
@LoaderAnnotation(LoaderPrinterName.VIEW)
public class ViewLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadSchemaChildren(viewCategoryContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadSchemaChildren(viewContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(viewCategoryContainer);
        List<Container> views = viewCategoryContainer.getChildren();
        if (views != null && !views.isEmpty()) {
            for (Container view : views) {
                detailedLoad(view);
                new ViewColumnLoader().fullLoad(view);
            }
        }
    }
}
