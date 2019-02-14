package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
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

public class FunctionLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfFunctions() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'FUNCTION' ;";

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

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container functionCategory = new Container();
        schemaContainer.addChild(functionCategory);

        FunctionCategoryLoader loader = new FunctionCategoryLoader(connection);
        loader.lazyLoad(functionCategory);

        Assert.assertEquals(1, functionCategory.getChildren().size());
        Assert.assertEquals("testTable", ((Container) functionCategory.getChildren().get(0)).getAttributes().get("SPECIFIC_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfFunctions() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        String query = "SELECT ROUTINE_CATALOG, ROUTINE_NAME, ROUTINE_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_BODY, EXTERNAL_NAME, PARAMETER_STYLE, SQL_PATH, CREATED, LAST_ALTERED, SQL_MODE, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, DATABASE_COLLATION, ROUTINE_SCHEMA, DATA_TYPE, ROUTINE_DEFINITION, DEFINER, ROUTINE_COMMENT, EXTERNAL_LANGUAGE, IS_DETERMINISTIC, SQL_DATA_ACCESS, SECURITY_TYPE  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'FUNCTION' AND SPECIFIC_NAME = 'null' ;";
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


        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container functionCategory = new Container();
        schemaContainer.addChild(functionCategory);
        Container functionContainer = new Container();
        functionCategory.addChild(functionContainer);
        functionContainer.addAttribute("SPECIFIC_NAME", null);
        FunctionLoader loader = new FunctionLoader(connection);
        loader.detailedLoad(functionContainer);

        Map<String, String> schemaAttributes = functionContainer.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}