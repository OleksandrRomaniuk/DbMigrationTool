package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
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
@LoaderAnnotation(LoaderPrinterName.VIEW_COLUMN)
public class ViewColumnLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) viewContainer.getAttributes().get(TableAttributes.TABLE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.VIEW_COLUMN);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), tableName);
            this.executeLazyLoaderQuery(viewContainer, query);
            //super.executeLazyLoadViewColumns(viewContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container viewColumnContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) viewColumnContainer.getAttributes().get(TableColumnAttributes.COLUMN_NAME);
            String tableName = (String) viewColumnContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.VIEW_COLUMN);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), tableName, elementName);
            super.executeDetailedLoaderQuery(viewColumnContainer, query);
            //super.executeDetailedLoadViewColumns(viewColumnContainer);
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
