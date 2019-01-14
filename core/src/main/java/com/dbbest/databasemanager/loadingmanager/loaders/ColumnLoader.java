package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColumnLoader implements Loaders {
    private static final Logger logger = Logger.getLogger("Connection logger");

    @Override
    public void lazyLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {
        try {
            if (new ContainerValidator().ifThereAreTablesInCategoryTables(tree)) {
                List<Container> tableList = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();

                for (Container table : tableList) {
                    if (new ContainerValidator().ifTableContainesCategoryColumns(tree, table)) {
                        ResultSet resultSet = connection.getMetaData().getColumns(tree.getName(), null, table.getName(), null);

                        while (resultSet.next()) {
                            Container column = new Container();
                            column.setName(resultSet.getString(ColumnAttributes.COLUMN_NAME.getElement()));
                            table.getChildByName(TableCategoriesTagNameCategories.Columns.getElement()).addChild(column);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {

        if (new ContainerValidator().ifThereAreTablesInCategoryTables(tree)) {
            List<Container> tables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();
            for (Container table : tables) {
                executeDetailedLoad(tree, table, tree.getName(), connection);
            }
        } else {
            logger.log(Level.INFO, "No tables to load.");
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }

    private void executeDetailedLoad(Container tree, Container table, String schemaname, Connection connection) throws ContainerException, DatabaseException {
        try {
            if (new ContainerValidator().ifTableContainesCategoryColumns(tree, table)) {
                List<Container> columns = table.getChildByName(TableCategoriesTagNameCategories.Columns.getElement()).getChildren();
                for (Container column : columns) {
                    String query =
                        String.format(MySqlQueriesConstants.ColumnInformationSchemaSelectAll.getQuery(),
                            schemaname, table.getName(), column.getName());
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    for (ColumnAttributes attributeKey : ColumnAttributes.values()) {
                        column.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }
}