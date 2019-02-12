package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the indexes.
 */
@LoaderAnnotation(LoaderPrinterName.INDEX)
public class IndexLoader extends AbstractLoader {

    public IndexLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container indexCategory) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container indexContainer) throws DatabaseException, ContainerException {
        try {
            String indexName = (String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME);
            String tableName = (String) indexContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) indexContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(MySQLQueries.INDEXDETAILED, listRepresentationOfAttributes,
                schemaName, tableName, indexName);
            Connection connection = super.getConnection();
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
    public void fullLoad(Container indexContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(indexContainer);
        this.detailedLoad(indexContainer);
    }
}
