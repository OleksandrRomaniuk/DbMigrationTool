package com.dbbest.databasemanager;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.propertyfilemanager.ConnectionPropertiesManager;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ConnectionPropertiesManagerTest {

    @Test
    public void shouldGetConnectionUrlDriverUserAndPassFromFile() throws ParsingException, ContainerException, DatabaseException {
        ConnectionPropertiesManager connectionPropertiesManager = new ConnectionPropertiesManager();
        Map<String, String> connectionPropertiesMap = connectionPropertiesManager
            .getConnectionUrlDriverUserAndPass("src\\test\\resources\\ConnectionPropertiesConManTest.xml", "mysqlDb1");
        Assert.assertEquals("jdbc:mysql://localhost:3306/sakila?allowPublicKeyRetrieval=true&useSSL=false",
            connectionPropertiesMap.get("url"));
        Assert.assertEquals("com.mysql.cj.jdbc.Driver",
            connectionPropertiesMap.get("driver"));
        Assert.assertEquals("root",
            connectionPropertiesMap.get("password"));
        Assert.assertEquals("root",
            connectionPropertiesMap.get("username"));
    }
}