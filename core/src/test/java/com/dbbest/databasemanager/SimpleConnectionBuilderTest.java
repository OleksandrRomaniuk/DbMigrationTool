package com.dbbest.databasemanager;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class SimpleConnectionBuilderTest {

    @Test
    public void shouldGetConnectionToDBMySQLNeedToHaveDbSourceItBeforeTesting() throws DatabaseException, ContainerException, ParsingException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection(
            "mysqlDb1",
            "src\\test\\resources\\ConnectionPropertiesConManTest.xml");
        assertEquals(new SimpleConnectionBuilder().getConnectionPools().isEmpty(), false);
    }
}