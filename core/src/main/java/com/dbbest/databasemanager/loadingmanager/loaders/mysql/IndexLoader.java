package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the indexes.
 */
@LoaderAnnotation(LoaderPrinterName.INDEX)
public class IndexLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container indexCategory) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) indexCategory.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.INDEX);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName(), tableName);
            super.executeLazyLoaderQuery(indexCategory, query);
            //super.executeLazyLoadTableChildren(indexCategory);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container indexContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME);
            String tableName = (String) indexContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance()
                .getSqlQueriesDetailLoader().get(LoaderPrinterName.INDEX);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes,
                Context.getInstance().getSchemaName(), tableName, elementName);
            //super.executeDetailedLoaderIndexQuery(indexContainer, query);
            //super.executeDetailedLoadTableIndexey(indexContainer);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Container index = new Container();
                index.setName(LoaderPrinterName.INDEX);
                for (String attribute : MySQLAttributeFactory.getInstance().getAttributes(this)) {
                    index.addAttribute(attribute, resultSet.getString(attribute));
                }
                indexContainer.addChild(index);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container indexCategory) throws DatabaseException, ContainerException {
        lazyLoad(indexCategory);
        List<Container> indexes = indexCategory.getChildren();
        if (indexes != null && !indexes.isEmpty()) {
            for (Container index : indexes) {
                detailedLoad(index);
            }
        }
    }
}
