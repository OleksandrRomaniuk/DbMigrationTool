package com.dbbest.databasemanager.dbmanager.loaders.mysql;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TriggerCategoryLoaderTest {

    @Test
    public void shouldExecuteLazyLoadOfTriggers() throws DatabaseException, ContainerException, SQLException {
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

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container tableCategory = new Container();
        schema.addChild(tableCategory);

        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        Container triggerCategory = new Container();
        table.addChild(triggerCategory);


        TriggerCategoryLoader loader = new TriggerCategoryLoader(connection);
        loader.lazyLoad(triggerCategory);

        Assert.assertEquals(1, triggerCategory.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) triggerCategory.getChildren().get(0)).getAttributes().get("TRIGGER_NAME"));
    }
}