package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.printers.TablePrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TriggerLoaderTest {

    @Test
    public void lazyLoad() {
    }

    @Test
    public void detailedLoad() throws ContainerException, DatabaseException, ParsingException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection("mysql");

        SchemaLoader schemaLoader = new SchemaLoader();
        Container schemaContainer = new Container();
        schemaContainer.setName("sakila");

        schemaLoader.lazyLoad(connection, schemaContainer);
        schemaLoader.detailedLoad(connection, schemaContainer);

        Container viewCategoryContainer = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Views.getElement());
        ViewLoader viewLoader = new ViewLoader();
        viewLoader.lazyLoad(connection, viewCategoryContainer);
        Container view = viewCategoryContainer.getChildByName("actor_info");
        viewLoader.detailedLoad(connection, view);
/*
        Map<String, String> viewAtt = view.getAttributes();
        for(Map.Entry<String, String> entry: viewAtt.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/
      //  System.out.println("-----------------------------------------------------");
        ViewColumnLoader viewColumnLoader = new ViewColumnLoader();
        viewColumnLoader.lazyLoad(connection, view);
        Container viewCol = view.getChildByName("first_name");
        viewColumnLoader.detailedLoad(connection, viewCol);
/*
        Map<String, String> viewAttributes = viewCol.getAttributes();
        for(Map.Entry<String, String> entry: viewAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
  */
        Container spCategoryContainer = schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Stored_Procedures.getElement());
        StoredProcedureLoader spLoader = new StoredProcedureLoader();
        spLoader.lazyLoad(connection, spCategoryContainer);
        Container sp = spCategoryContainer.getChildByName("film_in_stock");
        spLoader.detailedLoad(connection, sp);

/*
        Map<String, String> spAttributes = sp.getAttributes();
        for(Map.Entry<String, String> entry: spAttributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/

        //System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        SchemaLoader schemaLoader1 = new SchemaLoader();
        Container schemaContainer1 = new Container();
        schemaContainer1.setName("sakila");
        schemaLoader1.fullLoad(connection, schemaContainer1);
        Container procCategory = schemaContainer1.getChildByName(SchemaCategoriesTagNameConstants.Stored_Procedures.getElement());
/*
        Container procedure = procCategory.getChildByName("film_in_stock");
        List<Container> parameters = procedure.getChildren();

        for(Container parameter: parameters) {
            Map<String, String> attributes = parameter.getAttributes();

            for(Map.Entry<String, String> entry: attributes.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
*/
/*
        SchemaLoader schemaLoader2 = new SchemaLoader();
        Container schemaContainer2 = new Container();
        schemaContainer2.setName("sakila");
        schemaLoader1.fullLoad(connection, schemaContainer2);
        Container columnContainer = schemaContainer2.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement())
            .getChildByName("address").getChildByName(TableCategoriesTagNameCategories.Columns.getElement()).getChildByName("address_id");

        Map<String, String> attributes = columnContainer.getAttributes();

        for(Map.Entry<String, String> entry: attributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
*/

        SchemaLoader schemaLoader2 = new SchemaLoader();
        Container schemaContainer2 = new Container();
        schemaContainer2.setName("sakila");
        schemaLoader1.fullLoad(connection, schemaContainer2);
        Container tableContainer = schemaContainer2.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement())
            .getChildByName("rental");
/*
        Map<String, String> attributes = tableContainer.getAttributes();

        for(Map.Entry<String, String> entry: attributes.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        */
        TablePrinter tablePrinter = new TablePrinter();
        System.out.println(tablePrinter.execute(tableContainer));
        //Container indexContainer = tableContainer
    }
}