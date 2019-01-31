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

public class FunctionLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfFunctions() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'FUNCTION' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("SPECIFIC_NAME");
            will(returnValue("testTable"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("SPECIFIC_NAME");
            will(returnValue("testTable"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        FunctionLoader loader = new FunctionLoader();
        loader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testTable", ((Container) container.getChildren().get(0)).getAttributes().get("SPECIFIC_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfFunctions() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        String query = "SELECT ROUTINE_CATALOG, ROUTINE_SCHEMA, ROUTINE_NAME, ROUTINE_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_BODY, ROUTINE_DEFINITION, EXTERNAL_NAME, EXTERNAL_LANGUAGE, PARAMETER_STYLE, IS_DETERMINISTIC, SQL_DATA_ACCESS, SQL_PATH, SECURITY_TYPE, CREATED, LAST_ALTERED, SQL_MODE, ROUTINE_COMMENT, DEFINER, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, DATABASE_COLLATION  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'FUNCTION' AND  ROUTINE_NAME = 'null' ;";
        String query2 = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'null' ;";
        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query2);
            will(returnValue(preparedStatement2));
        }});

        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        when(resultSet2.next()).thenReturn(false);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        container.addAttribute("SPECIFIC_NAME", null);
        FunctionLoader loader = new FunctionLoader();
        loader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}