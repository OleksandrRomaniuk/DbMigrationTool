package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.TableLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class TableLoaderTest {

    @Test
    public void lazyLoad() throws ParsingException, ContainerException, DatabaseException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection("mysql");
        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        TableLoader tableLoader = new TableLoader();
        Container tableCategoryContainer = new Container();
        tableLoader.lazyLoad(tableCategoryContainer);

        List<Container> containers = tableCategoryContainer.getChildren();
        for (Container container: containers) {
           // System.out.println(container.getAttributes().get(AttributeSingleConstants.TABLE_NAME));
        }

        Container table = containers.get(0);
        tableLoader.detailedLoad(table);
        StringBuilder result = new StringBuilder();
        Map<String, String> map = table.getAttributes();
        for (Map.Entry<String, String> entry: map.entrySet()) {
            result.append(entry.getKey() + " - " + entry.getValue() + " ");
        }
        Assert.assertEquals(result.toString(), "TABLE_CATALOG - def TABLE_COMMENT -  TABLE_NAME - actor CHECKSUM - null TABLE_SCHEMA - sakila CHECK_TIME - null ENGINE - InnoDB TABLE_TYPE - BASE TABLE TABLE_ROWS - 200 AVG_ROW_LENGTH - 81 UPDATE_TIME - null DATA_LENGTH - 16384 DATA_FREE - 0 INDEX_LENGTH - 16384 ROW_FORMAT - Dynamic AUTO_INCREMENT - 201 VERSION - 10 CREATE_OPTIONS -  CREATE_TIME - 2018-08-09 20:08:05 MAX_DATA_LENGTH - 0 TABLE_COLLATION - utf8_general_ci ");
    }
}