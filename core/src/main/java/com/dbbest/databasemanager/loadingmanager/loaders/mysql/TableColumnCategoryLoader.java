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

@LoaderAnnotation(LoaderPrinterName.TABLE_COLUMNS)
public class TableColumnCategoryLoader extends AbstractLoader {
    public TableColumnCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) tableColumnCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) tableColumnCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String query = String.format(MySQLQueries.COLUMNLAZY, schemaName, tableName);
            System.out.println(query);
            super.executeLazyLoaderQuery(tableColumnCategoryContainer, query, LoaderPrinterName.TABLE_COLUMN);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        if (tableColumnCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) tableColumnCategoryContainer.getChildren()) {
                new TableColumnLoader(super.getContext()).detailedLoad(tableColumn);
            }
        }
    }

    @Override
    public void fullLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(tableColumnCategoryContainer);
        if (tableColumnCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) tableColumnCategoryContainer.getChildren()) {
                new TableColumnLoader(super.getContext()).fullLoad(tableColumn);
            }
        }
    }
}
