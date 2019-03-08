package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the index category.
 */
@LoaderAnnotation(NameConstants.TABLE_INDEXES)
public class IndexCategoryLoader extends AbstractLoader {
    /*public IndexCategoryLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) indexCategoryContainer.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) indexCategoryContainer.getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.INDEXLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(indexCategoryContainer, query, NameConstants.INDEX, IndexAttributes.INDEX_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        if (indexCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) indexCategoryContainer.getChildren()) {
                IndexLoader indexLoader = new IndexLoader();
                indexLoader.setConnection(super.getConnection());
                indexLoader.detailedLoad(tableColumn);
            }
        }
    }

    @Override
    public void fullLoad(Container indexCategoryContainer) throws DatabaseException, ContainerException {
        if (indexCategoryContainer.hasChildren()) {
            for (Container tableIndex : (List<Container>) indexCategoryContainer.getChildren()) {
                IndexLoader indexLoader = new IndexLoader();
                indexLoader.setConnection(super.getConnection());
                indexLoader.fullLoad(tableIndex);
            }
        }
    }
}
