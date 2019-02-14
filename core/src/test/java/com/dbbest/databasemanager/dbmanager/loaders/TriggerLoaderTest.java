package com.dbbest.databasemanager.dbmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.SchemaLoader;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.StoredProcedureLoader;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.ViewColumnLoader;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.ViewLoader;
import com.dbbest.databasemanager.dbmanager.printers.mysql.TablePrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import java.sql.Connection;

public class TriggerLoaderTest {

    @Test
    public void lazyLoad() {
    }

    @Test
    public void detailedLoad() throws ContainerException, DatabaseException, ParsingException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection("mysql");


        Context context = new Context();
        SchemaLoader schemaLoader = new SchemaLoader(connection);
        Container schemaContainer = new Container();
        schemaContainer.setName("sakila");

        schemaLoader.lazyLoad(schemaContainer);
        schemaLoader.detailedLoad(schemaContainer);

        Container viewCategoryContainer = schemaContainer.getChildByName(NameConstants.VIEWS);
        ViewLoader viewLoader = new ViewLoader(connection);
        viewLoader.lazyLoad(viewCategoryContainer);
        Container view = viewCategoryContainer.getChildByName("actor_info");
        viewLoader.detailedLoad(view);

        ViewColumnLoader viewColumnLoader = new ViewColumnLoader(connection);
        viewColumnLoader.lazyLoad(view);
        Container viewCol = view.getChildByName("first_name");
        viewColumnLoader.detailedLoad(viewCol);

        Container spCategoryContainer = schemaContainer.getChildByName(NameConstants.STORED_PROCEDURES);
        StoredProcedureLoader spLoader = new StoredProcedureLoader(connection);
        spLoader.lazyLoad(spCategoryContainer);
        Container sp = spCategoryContainer.getChildByName("film_in_stock");
        spLoader.detailedLoad(sp);


        SchemaLoader schemaLoader1 = new SchemaLoader(connection);
        Container schemaContainer1 = new Container();
        schemaContainer1.setName("sakila");
        schemaLoader1.fullLoad(schemaContainer1);
        Container procCategory = schemaContainer1.getChildByName(NameConstants.STORED_PROCEDURES);

        SchemaLoader schemaLoader2 = new SchemaLoader(connection);
        Container schemaContainer2 = new Container();
        schemaContainer2.setName("sakila");
        schemaLoader1.fullLoad(schemaContainer2);
        Container tableContainer = schemaContainer2.getChildByName(NameConstants.TABLES)
            .getChildByName("rental");

        TablePrinter tablePrinter = new TablePrinter();
        System.out.println(tablePrinter.execute(tableContainer));
        //Container indexContainer = tableContainer
    }
}