package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 *The class-loader of the function category.
 */
@LoaderAnnotation(NameConstants.FUNCTIONS)
public class FunctionCategoryLoader extends AbstractLoader {
    /*public FunctionCategoryLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.FUNCTIONLAZY,
                functionCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(functionCategoryContainer, query,
                NameConstants.FUNCTION, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        if (functionCategoryContainer.hasChildren()) {
            for (Container function : (List<Container>) functionCategoryContainer.getChildren()) {
                FunctionLoader functionLoader = new FunctionLoader();
                functionLoader.setConnection(super.getConnection());
                functionLoader.detailedLoad(function);
            }
        }
    }

    @Override
    public void fullLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        if (functionCategoryContainer.hasChildren()) {
            for (Container function : (List<Container>) functionCategoryContainer.getChildren()) {
                FunctionLoader functionLoader = new FunctionLoader();
                functionLoader.setConnection(super.getConnection());
                functionLoader.fullLoad(function);
            }
        }
    }
}
