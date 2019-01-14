package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndexLoader implements Loaders {
    private static final Logger logger = Logger.getLogger("Connection logger");

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
    public void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {
        if (new ContainerValidator().ifThereAreTablesInCategoryTables(tree)) {
            List<Container> tables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();
            for (Container table : tables) {
                if (new ContainerValidator().ifThereAreIndexesInCategoryIndexes(tree, table)) {
                    executeDetailedLoad(tree, table, connection);
                } else {
                    logger.log(Level.INFO, "The table " + table.getName() + " does not contain indexes.");
                }
            }
        } else {
            logger.log(Level.INFO, "There are no tables in the category Tables.");
        }
    }


    @Override
    public void fullLoad(Connection connection, Container container) {

    }

    private void executeLazyLoad(Container tree, Container table, Connection connection) throws ContainerException, DatabaseException {

        if (new ContainerValidator().ifTableContainsCategoryIndexes(tree, table)) {
            try {
                ResultSet indexes = connection.getMetaData().getIndexInfo(null, null, table.getName(), false, false);

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

    private void executeDetailedLoad(Container tree, Container table, Connection connection) throws DatabaseException {
        List<Container> indexes = null;
        try {
            indexes = table.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()).getChildren();

            for (Container index : indexes) {
                String query =
                    String.format(MySqlQueriesConstants.IndexInformationSchemaSelectAll.getQuery(),
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
