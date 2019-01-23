package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.loaders.*;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class SchemaLoaderTestDelete {

    private Connection connection;
    @Before
    public void setUp() throws Exception {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        connection = simpleConnectionBuilder.getConnection("mysql");
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null)
        connection.close();
    }

    @Test
    public void lazyLoad() throws DatabaseException, ContainerException, SQLException {

        Container schemaContainer = new Container();
        schemaContainer.setName("sakila");
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.lazyLoad(connection, schemaContainer);

        List<Container> schemaCatalogs = schemaContainer.getChildren();
      /*
        for(Container catalog: schemaCatalogs) {
            System.out.println(catalog.getName());
        }
*/
        schemaLoader.detailedLoad(connection, schemaContainer);

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
/*
        for(Map.Entry<String, String> entry: schemaAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
        TableLoader tableLoader = new TableLoader();
        Container categoryTablesContainer = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement());
        //System.out.println(categoryTablesContainer.getName());
        tableLoader.lazyLoad(connection, categoryTablesContainer);
        List<Container> tables = categoryTablesContainer.getChildren();

/*
        for(Container table: tables) {
            System.out.println(table.getName());
        }
*/

        tableLoader.detailedLoad(connection, categoryTablesContainer.getChildByName("customer"));
        Map<String, String> tableAttributes = categoryTablesContainer.getChildByName("customer").getAttributes();
/*
        for(Map.Entry<String, String> entry: tableAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
        Container table = categoryTablesContainer.getChildByName("customer");
        Container tableColumnCategory = table.getChildByName(TableCategoriesTagNameCategories.Columns.getElement());
        TableColumnLoader columnLoader = new TableColumnLoader();
        columnLoader.lazyLoad(connection, tableColumnCategory);
/*
        List<Container> columns = tableColumnCategory.getChildren();
        for (Container column: columns) {
            System.out.println(column.getName());
        }
*/
        Container column = tableColumnCategory.getChildByName("first_name");
        columnLoader.detailedLoad(connection, column);
/*
        Map<String, String> columnAttributes = column.getAttributes();
        for(Map.Entry<String, String> entry: columnAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
        IndexLoader indexLoader = new IndexLoader();
        Container indexCategory = table.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement());
        indexLoader.lazyLoad(connection, indexCategory);
        List<Container> indexes = indexCategory.getChildren();
        Container index = indexCategory.getChildByName("PRIMARY");
        indexLoader.detailedLoad(connection, index);


        IndexLoader indexLoader2 = new IndexLoader();
        Container indexCategory2 = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement())
            .getChildByName("city").getChildByName(TableCategoriesTagNameCategories.Indexes.getElement());
        indexLoader.lazyLoad(connection, indexCategory2);
        List<Container> indexes2 = indexCategory2.getChildren();
        //Container index2 = indexCategory2.getChildByName("CountryCode");
        //indexLoader.detailedLoad(connection, index2);

        for (Container index3: indexes2) {
            System.out.println(index3.getName());

        }
        System.out.println(".........................................");
        /*
        Map<String, String> indexAttributes = index.getAttributes();
        for(Map.Entry<String, String> entry: indexAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */
/*
        for (Container index: indexes) {
            System.out.println(index.getName());
        }
*/
        ForeignKeyLoader foreignKeyLoader = new ForeignKeyLoader();
        Container fkCategory = table.getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement());
        foreignKeyLoader.lazyLoad(connection, fkCategory);
/*
        List<Container> fks = fkCategory.getChildren();
        for (Container fk: fks) {
            System.out.println(fk.getName());
        }
*/
        Container fk = fkCategory.getChildByName("fk_customer_address");
        foreignKeyLoader.detailedLoad(connection, fk);
/*
        Map<String, String> fkAttributes = fk.getAttributes();
        for(Map.Entry<String, String> entry: fkAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
        TriggerLoader triggerLoader = new TriggerLoader();
        Container triggerCategory = table.getChildByName(TableCategoriesTagNameCategories.Triggers.getElement());
        triggerLoader.lazyLoad(connection, triggerCategory);
/*
        List<Container> triggers = triggerCategory.getChildren();
        for (Container trigger: triggers) {
            System.out.println(trigger.getName());
        }
        */
/*
        Container trigger = triggerCategory.getChildByName("customer_create_date");
        triggerLoader.detailedLoad(connection, trigger);

        Map<String, String> triggerAttributes = trigger.getAttributes();
        for(Map.Entry<String, String> entry: triggerAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */

        ViewLoader viewLoader = new ViewLoader();
        Container viewCategory = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Views.getElement());
        viewLoader.lazyLoad(connection, viewCategory);
/*
        List<Container> views = viewCategory.getChildren();
        for (Container view: views) {
            System.out.println(view.getName());
        }
*/
        Container view = viewCategory.getChildByName("actor_info");
        viewLoader.detailedLoad(connection, view);
/*
        Map<String, String> viewAttributes = view.getAttributes();
        for(Map.Entry<String, String> entry: viewAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */

        ViewColumnLoader viewColumnLoader = new ViewColumnLoader();
        viewColumnLoader.lazyLoad(connection, view);
/*
        List<Container> viewColumns = view.getChildren();
        for (Container viewColumn: viewColumns) {
            System.out.println(viewColumn.getName());
        }
*/
        Container viewColumn = view.getChildByName("first_name");
        viewColumnLoader.detailedLoad(connection, viewColumn);
/*
        Map<String, String> viewAttributes = viewColumn.getAttributes();
        for(Map.Entry<String, String> entry: viewAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */
        StoredProcedureLoader storedProcedureLoader = new StoredProcedureLoader();
        Container storedProcedureCategory = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Stored_Procedures.getElement());
        storedProcedureLoader.lazyLoad(connection, storedProcedureCategory);
/*
        List<Container> storedProcedures = storedProcedureCategory.getChildren();
        for (Container storedProcedure: storedProcedures) {
            System.out.println("SP: " + storedProcedure.getName());
        }
*/
        Container storedProcedure = storedProcedureCategory.getChildByName("film_in_stock");
        storedProcedureLoader.detailedLoad(connection, storedProcedure);
/*
        Map<String, String> spAttributes = storedProcedure.getAttributes();
        for(Map.Entry<String, String> entry: spAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
        FunctionLoader functionLoader = new FunctionLoader();
        Container functionCategory = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Functions.getElement());
        functionLoader.lazyLoad(connection, functionCategory);
/*
        List<Container> functions = functionCategory.getChildren();
        for (Container function: functions) {
            System.out.println(function.getName());
        }
*/
        Container function = functionCategory.getChildByName("get_customer_balance");
        functionLoader.detailedLoad(connection, function);
/*
        Map<String, String> fAttributes = function.getAttributes();
        for(Map.Entry<String, String> entry: fAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */

        Container schemaContainer1 = new Container();
        schemaContainer1.setName("sakila");
        SchemaLoader schemaLoader1 = new SchemaLoader();
        schemaLoader1.fullLoad(connection, schemaContainer1);
        System.out.println(schemaContainer1.getName());
        System.out.println("-----------------------------------------------");
        List<Container> schemaCategories = schemaContainer1.getChildren();
        for (Container schemaCategory: schemaCategories) {
            System.out.println("-----------------------------------------------");
            System.out.println(schemaCategory.getName());
            System.out.println("-----------------------------------------------");
            List<Container> subCategories = schemaCategory.getChildren();
            for (Container subCategory: subCategories) {
                System.out.println("-----------------------------------------------");
                System.out.println(subCategory.getName());
                System.out.println("-----------------------------------------------");
                List<Container> tableCategories = subCategory.getChildren();
                if (tableCategories != null && !tableCategories.isEmpty()) {
                    for (Container tableCategory: tableCategories) {
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println(tableCategory.getName());
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        List<Container> subTableCategories = tableCategory.getChildren();
                        if (subTableCategories != null && !subCategories.isEmpty()) {
                            for (Container child: subTableCategories) {
                                System.out.println("*********************************************");
                                System.out.println(child.getParent().getParent().getParent().getParent().getName() + " - " + child.getParent().getParent().getParent().getName() + " - " + child.getParent().getParent().getName() + " + " + child.getParent().getName() + " - " + child.getName());
                                System.out.println("*********************************************");

                                Map<String, String> attrs = child.getAttributes();
                                for (Map.Entry<String, String> enty: attrs.entrySet()) {
                                    System.out.println(enty.getKey() + " - " + enty.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void fullLoad() throws SQLException, ContainerException, DatabaseException {

    }
}