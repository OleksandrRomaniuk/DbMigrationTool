package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.support.ContainerValidator;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ForeignKeyLoader implements Loaders {

    @Override
    public void lazyLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {

        if (new ContainerValidator().ifThereAreTablesInCategoryTables(tree)) {
            List<Container> tables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();
            for (Container table : tables) {
                executeLazyLoad(tree, table, connection);
            }
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container container) throws DatabaseException, ContainerException {

    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }

    private void executeLazyLoad(Container tree, Container table, Connection connection) {
        if (new ContainerValidator().ifTableContainsCategoryForeignKeys(tree, table)) {
            try {
                ResultSet indexes = connection.getMetaData().getImportedKeys(null, null, table.getName(), false, false);

                while (indexes.next()) {
                    Container index = new Container();
                    index.setName(indexes.getString(IndexAttributes.INDEX_NAME.getElement()));
                    table.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()).addChild(index);
                }
            } catch (SQLException e) {
                throw new DatabaseException(Level.SEVERE, e);
            }
        } else {
            throw new DatabaseException(Level.SEVERE, "The table " + table.getName() + " does not contain the category Indexes.");
        }
    }

    private void executeDetailedLoad(Container tree, Container table, Connection connection) {
        List<Container> foreignKeys = null;
        try {
            foreignKeys = table.getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement()).getChildren();

            for (Container index : foreignKeys) {
                String query =
                    String.format(MySqlQueriesConstants..getQuery(),
                        tree.getName(), table.getName(), index.getName());
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                for (ColumnAttributes attributeKey : ColumnAttributes.values()) {
                    index.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (ContainerException e) {
            logger.log(Level.SEVERE, "There is no Indexes category in the table " + table.getName());
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }
}
