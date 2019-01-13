package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.ConstantListsBuilder;
import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class SchemaLoaderTest {

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
    public void lazyLoad() throws ConnectionException, ContainerException {

        Container container = new Container();
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.lazyLoad(connection, container);
        schemaLoader.detailedLoad(connection, new Container());

        //ConstantListsBuilder.getInstance().get();

    }
}