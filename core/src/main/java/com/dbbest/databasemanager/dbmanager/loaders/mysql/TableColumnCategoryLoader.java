package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the table columns category.
 */
@LoaderAnnotation(NameConstants.TABLE_COLUMNS)
public class TableColumnCategoryLoader extends AbstractLoader {
    /*public TableColumnCategoryLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) tableColumnCategoryContainer.getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) tableColumnCategoryContainer.getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.COLUMNLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(tableColumnCategoryContainer, query,
                NameConstants.TABLE_COLUMN, TableColumnAttributes.COLUMN_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        if (tableColumnCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) tableColumnCategoryContainer.getChildren()) {
                TableColumnLoader tableColumnLoader = new TableColumnLoader();
                tableColumnLoader.setConnection(super.getConnection());
                tableColumnLoader.detailedLoad(tableColumn);
            }
        }
    }

    @Override
    public void fullLoad(Container tableColumnCategoryContainer) throws DatabaseException, ContainerException {
        if (tableColumnCategoryContainer.hasChildren()) {
            for (Container tableColumn : (List<Container>) tableColumnCategoryContainer.getChildren()) {
                TableColumnLoader tableColumnLoader = new TableColumnLoader();
                tableColumnLoader.setConnection(super.getConnection());
                tableColumnLoader.fullLoad(tableColumn);
            }
        }
    }
}
