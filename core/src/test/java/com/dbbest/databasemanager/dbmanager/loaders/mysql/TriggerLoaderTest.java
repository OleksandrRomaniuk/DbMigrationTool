package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TriggerLoaderTest {

    @Test
    public void shouldExecuteDetailLoadOfIndexes() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TRIGGER_CATALOG, TRIGGER_SCHEMA, EVENT_OBJECT_CATALOG, ACTION_ORDER, ACTION_CONDITION, ACTION_ORIENTATION, ACTION_REFERENCE_OLD_TABLE, ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW, ACTION_REFERENCE_NEW_ROW, CREATED, SQL_MODE, DEFINER, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, DATABASE_COLLATION, EVENT_OBJECT_SCHEMA, ACTION_TIMING, EVENT_MANIPULATION, EVENT_OBJECT_TABLE, ACTION_STATEMENT FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = 'sakila' AND EVENT_OBJECT_TABLE = 'testTable' AND  TRIGGER_NAME = 'testTrigger' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCategory = new Container();
        schema.addChild(tableCategory);
        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        Container triggerCategory = new Container();
        table.addChild(triggerCategory);
        Container trigger = new Container();
        triggerCategory.addChild(trigger);
        trigger.addAttribute("TRIGGER_NAME", "testTrigger");
        TriggerLoader loader = new TriggerLoader(connection);
        loader.detailedLoad(trigger);

        Map<String, String> attributes = trigger.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if(entry.getKey().equals("TRIGGER_NAME")) {
                Assert.assertEquals("testTrigger", entry.getValue());
            } else {
                Assert.assertEquals(null, entry.getValue());
            }
        }
        Assert.assertEquals(22, trigger.getAttributes().size());
    }

}