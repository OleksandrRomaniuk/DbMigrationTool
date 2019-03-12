package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
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
 * The class-loader of the table category.
 */
@LoaderAnnotation(NameConstants.TABLES)
public class TableCategoryLoader extends AbstractLoader {
    /*public TableCategoryLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container tableCategory) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.TABLELAZY,
                tableCategory.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(tableCategory, query, NameConstants.TABLE, TableAttributes.TABLE_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container tableCategory) throws DatabaseException, ContainerException {
        if (tableCategory.hasChildren()) {
            for (Container table : (List<Container>) tableCategory.getChildren()) {
                TableLoader tableLoader = new TableLoader();
                tableLoader.setConnection(super.getConnection());
                tableLoader.detailedLoad(table);
            }
        }
    }

    @Override
    public void fullLoad(Container tableCategory) throws DatabaseException, ContainerException {
        this.lazyLoad(tableCategory);
        if (tableCategory.hasChildren()) {
            for (Container table : (List<Container>) tableCategory.getChildren()) {
                TableLoader tableLoader = new TableLoader();
                tableLoader.setConnection(super.getConnection());
                tableLoader.fullLoad(table);
            }
        }
    }
}
