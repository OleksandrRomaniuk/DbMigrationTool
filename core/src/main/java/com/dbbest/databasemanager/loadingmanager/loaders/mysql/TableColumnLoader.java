package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
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
 * The class-loader of the table columns.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE_COLUMN)
public class TableColumnLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container categoryColumnsContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) categoryColumnsContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.TABLE_COLUMN);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), tableName);
            super.executeLazyLoaderQuery(categoryColumnsContainer, query);
            //super.executeLazyLoadTableChildren(categoryColumnsContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container columnContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) columnContainer.getAttributes().get(TableColumnAttributes.COLUMN_NAME);
            String tableName = (String) columnContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.TABLE_COLUMN);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), tableName, elementName);
            super.executeDetailedLoaderQuery(columnContainer, query);
            //super.executeDetailedLoadTableChildren(columnContainer);
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
