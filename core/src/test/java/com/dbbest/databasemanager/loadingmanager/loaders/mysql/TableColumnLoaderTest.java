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

public class TableColumnLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfTableColumns() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA= 'sakila' AND TABLE_NAME = 'testTable' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("COLUMN_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("COLUMN_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container parent = new Container();
        parent.addAttribute("TABLE_NAME", "testTable");
        Container container = new Container();
        parent.addChild(container);


        TableColumnLoader loader = new TableColumnLoader();
        loader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) container.getChildren().get(0)).getAttributes().get("COLUMN_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfTableColumns() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, COLUMN_TYPE, COLUMN_KEY, EXTRA, PRIVILEGES, COLUMN_COMMENT, GENERATION_EXPRESSION FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA= 'sakila' AND TABLE_NAME = 'testTable' AND  COLUMN_NAME = 'null' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container parent1 = new Container();
        parent1.addAttribute("TABLE_NAME", "testTable");
        Container parent2 = new Container();
        parent1.addChild(parent2);

        Container container = new Container();
        parent2.addChild(container);
        container.addAttribute("COLUMN_NAME", null);
        TableColumnLoader loader = new TableColumnLoader();
        loader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }

}