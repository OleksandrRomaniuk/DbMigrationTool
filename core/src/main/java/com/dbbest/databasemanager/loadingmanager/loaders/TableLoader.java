package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.support.ContainerValidator;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class TableLoader implements Loaders {

    @Override
    public void lazyLoad(Connection connection, Container categoryTablesContainer) throws DatabaseException, ContainerException {
        try {
            if (categoryTablesContainer.getName() == null || categoryTablesContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The container with the category Tables does not contain the name.");
            }
            ResultSet resultSet = connection.getMetaData()
                .getTables(categoryTablesContainer.getName(), null, "%", new String[] {"TABLE"});

            while (resultSet.next()) {
                Container table = new Container();
                table.setName(resultSet.getString(TableAttributes.TABLE_NAME.getElement()));
                categoryTablesContainer.addChild(table);
                for (TableCategoriesTagNameCategories tagName : TableCategoriesTagNameCategories.values()) {
                    Container tableCategory = new Container();
                    tableCategory.setName(tagName.getElement());
                    table.addChild(tableCategory);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tableContainer) throws DatabaseException, ContainerException {

        try {
            if (tableContainer.getName() == null || tableContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The table container does not contain the name");
            }
            String informationSchemataSelectAllQuery =
                String.format(MySqlQueriesConstants.TableInformationSchemaSelectAll.getQuery(),
                    connection.getCatalog(), tableContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (TableAttributes attributeKey : TableAttributes.values()) {
                tableContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }
}
