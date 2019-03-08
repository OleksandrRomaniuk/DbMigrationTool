package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the view category.
 */
@LoaderAnnotation(NameConstants.VIEWS)
public class ViewCategoryLoader extends AbstractLoader {
    /*public ViewCategoryLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.VIEWLAZY,
                viewCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(viewCategoryContainer, query, NameConstants.VIEW, ViewAttributes.TABLE_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        if (viewCategoryContainer.hasChildren()) {
            for (Container view : (List<Container>) viewCategoryContainer.getChildren()) {
                ViewLoader viewLoader = new ViewLoader();
                viewLoader.setConnection(super.getConnection());
                viewLoader.detailedLoad(view);
            }
        }
    }

    @Override
    public void fullLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        if (viewCategoryContainer.hasChildren()) {
            for (Container view : (List<Container>) viewCategoryContainer.getChildren()) {
                ViewLoader viewLoader = new ViewLoader();
                viewLoader.setConnection(super.getConnection());
                viewLoader.fullLoad(view);
            }
        }
    }
}
