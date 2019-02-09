package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of foreign keys.
 */
@LoaderAnnotation(LoaderPrinterName.FOREIGN_KEY)
public class ForeignKeyLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {

        try {

            String tableName = (String) containerOfCategoryFks.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.FOREIGN_KEY);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), tableName);
            super.executeLazyLoaderQuery(containerOfCategoryFks, query);
            //super.executeLazyLoadTableChildren(containerOfCategoryFks);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container fkContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) fkContainer.getAttributes().get(ForeignKeyAttributes.FUNCTION_PROCEDURE_NAME);
            String tableName = (String) fkContainer.getParent().getParent()
                .getAttributes().get(AttributeSingleConstants.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.FOREIGN_KEY);
            String query = String.format(detailedLoaderQuery,
                super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this)),
                Context.getInstance().getSchemaName(), tableName, elementName);
            this.executeDetailedLoaderQuery(fkContainer, query);
            //super.executeDetailedLoadTableChildren(fkContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {
        lazyLoad(containerOfCategoryFks);
        List<Container> foreignKeys = containerOfCategoryFks.getChildren();
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (Container fk : foreignKeys) {
                detailedLoad(fk);
            }
        }
    }
}
