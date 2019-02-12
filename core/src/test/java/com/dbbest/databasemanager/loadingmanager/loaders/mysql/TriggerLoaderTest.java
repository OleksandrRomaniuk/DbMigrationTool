package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TriggerLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfTriggers() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TRIGGER_NAME  FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = 'sakila' AND EVENT_OBJECT_TABLE = 'testTable' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("TRIGGER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("TRIGGER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = new Context();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container parent = new Container();
        parent.addAttribute("TABLE_NAME", "testTable");
        Container container = new Container();
        parent.addChild(container);


        TriggerLoader loader = new TriggerLoader(context);
        loader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) container.getChildren().get(0)).getAttributes().get("TRIGGER_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfIndexes() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TRIGGER_CATALOG, TRIGGER_SCHEMA, EVENT_OBJECT_CATALOG, ACTION_ORDER, ACTION_CONDITION, ACTION_ORIENTATION, ACTION_REFERENCE_OLD_TABLE, ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW, ACTION_REFERENCE_NEW_ROW, CREATED, SQL_MODE, DEFINER, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, DATABASE_COLLATION, EVENT_OBJECT_SCHEMA, ACTION_TIMING, EVENT_MANIPULATION, EVENT_OBJECT_TABLE, ACTION_STATEMENT FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = 'sakila' AND EVENT_OBJECT_TABLE = 'testTable' AND  TRIGGER_NAME = 'null' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Context context = new Context();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container parent1 = new Container();
        parent1.addAttribute("TABLE_NAME", "testTable");
        Container parent2 = new Container();
        parent1.addChild(parent2);

        Container container = new Container();
        parent2.addChild(container);
        container.addAttribute("TRIGGER_NAME", null);
        TriggerLoader loader = new TriggerLoader(context);
        loader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }

}