package com.dbbest.databasemanager.dbmanager.loaders;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.SchemaLoader;
import com.dbbest.databasemanager.dbmanager.printers.mysql.TablePrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class GeneralManualTest {


    @Test
    public void detailedLoad() throws ContainerException, DatabaseException, ParsingException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection("mysql");


        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.setConnection(connection);
        Container schemaContainer = new Container();

        schemaLoader.fullLoad(schemaContainer);

        TablePrinter tablePrinter = new TablePrinter();
        for (Container table : (List<Container>) schemaContainer.getChildByName(NameConstants.TABLES).getChildren()) {
            System.out.println(tablePrinter.execute(table));
        }
    }
}