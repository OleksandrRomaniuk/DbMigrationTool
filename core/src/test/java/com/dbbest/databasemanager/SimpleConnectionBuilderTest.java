package com.dbbest.databasemanager;

import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class SimpleConnectionBuilderTest {

    @Test
    public void shouldGetConnectionToDBMySQLNeedToHaveDbSourceItBeforeTesting() throws ConnectionException, ContainerException, ParsingException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection(
            "mysqlDb1",
            "src\\test\\resources\\ConnectionPropertiesConManTest.xml");
        assertEquals(new SimpleConnectionBuilder().getConnectionPools().size(), 1);
    }
}