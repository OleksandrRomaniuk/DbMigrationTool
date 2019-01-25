package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeListConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.queries.MySQLQueries;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.TABLE)
public class TableLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Connection connection, Container categoryTablesContainer) throws DatabaseException, ContainerException {
        try {

            super.executeLazyLoad(categoryTablesContainer);

/*
            ResultSet resultSet = connection.getMetaData()
                .getTables(categoryTablesContainer.getParent().getName(), null, null, new String[] {"TABLE"});

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
            */
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tableContainer) throws DatabaseException, ContainerException {


        try {
            String schemaName = tableContainer.getParent().getParent().getName();
            String detailedLoaderQuery = MySQLQueries.TABLEDETAILED;
            List<String> attributes = AttributeListConstants.CONSTANTS
                .get((LoaderAnnotation) this.getClass().getAnnotation(LoaderAnnotation.class));
            super.executeDetailedLoad(connection, tableContainer, detailedLoaderQuery, attributes, schemaName);

            /*
            String informationSchemataSelectAllQuery =
                String.format(MySqlQueriesConstants.TableInformationSchemaSelectAll.getQuery(),
                    connection.getCatalog(), tableContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (TableAttributes attributeKey : TableAttributes.values()) {
                    tableContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
            */
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container categoryTablesContainer) throws DatabaseException, ContainerException {
        lazyLoad(connection, categoryTablesContainer);
        List<Container> tables = categoryTablesContainer.getChildren();
        if (tables != null && !tables.isEmpty()) {
            for (Container table : tables) {
                detailedLoad(connection, table);
                new TableColumnLoader().fullLoad(connection, table.getChildByName(TableCategoriesTagNameCategories.Columns.getElement()));
                new IndexLoader().fullLoad(connection, table.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()));
                new ForeignKeyLoader().fullLoad(connection, table.getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement()));
                new TriggerLoader().fullLoad(connection, table.getChildByName(TableCategoriesTagNameCategories.Triggers.getElement()));
                new ConstraintsLoader().fullLoad(connection, table.getChildByName(TableCategoriesTagNameCategories.ConstraintCategory.getElement()));
            }
        }
    }
}
