package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
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
            String lazyLoaderQuery = MySQLQueries.getInstance()
                .getSqlQueriesLazyLoader().get(LoaderPrinterName.VIEW);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName());
            super.executeLazyLoaderQuery(viewCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) viewContainer.getAttributes().get(ViewAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader()
                .get(LoaderPrinterName.VIEW);
            String listOfAttributes = getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes, Context.getInstance().getSchemaName(), elementName);
            super.executeDetailedLoaderQuery(viewContainer, query);
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
