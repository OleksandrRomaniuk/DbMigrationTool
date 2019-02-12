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

@LoaderAnnotation(LoaderPrinterName.TABLE_INDEXES)
public class IndexCategoryLoader extends AbstractLoader {
    public IndexCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) indexCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) indexCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String query = String.format(MySQLQueries.INDEXLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(indexCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        if (indexCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) indexCategoryContainer.getChildren()) {
                new IndexLoader(super.getContext()).detailedLoad(tableColumn);
            }
        }
    }

    @Override
    public void fullLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(indexCategoryContainer);
        if (indexCategoryContainer.hasChildren()) {
            for (Container tableIndex : (List<Container>) indexCategoryContainer.getChildren()) {
                new IndexLoader(super.getContext()).fullLoad(tableIndex);
            }
        }
    }
}
