package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
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

public class TableLoader implements Loaders {

    @Override
    public void lazyLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {
        try {
            if (new ContainerValidator().ifSchemaContainsCategoryTables(tree)) {
                ResultSet resultSet = connection.getMetaData()
                    .getTables(tree.getName(), null, "%", new String[] {"TABLE"});

                Container childContainerWithTables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement());

                while (resultSet.next()) {
                    Container table = new Container();
                    table.setName(resultSet.getString(TableAttributes.TABLE_NAME.getElement()));
                    childContainerWithTables.addChild(table);
                    for (TableCategoriesTagNameCategories tagName : TableCategoriesTagNameCategories.values()) {
                        Container tableCategory = new Container();
                        tableCategory.setName(tagName.getElement());
                        table.addChild(tableCategory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {

        try {
            if (new ContainerValidator().ifThereAreTablesInCategoryTables(tree)) {
                List<Container> tables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();
                for (Container table : tables) {
                    String informationSchemataSelectAllQuery =
                        String.format(MySqlQueriesConstants.TableInformationSchemaSelectAll.getQuery(),
                            tree.getName(), table.getName());
                    PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    for (TableAttributes attributeKey : TableAttributes.values()) {
                        table.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }
}
