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

public class StoredProcedureLoaderTest {

    @Test
    public void shouldExecuteLazyLoadOfStroredProcedures() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        //String query = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'PROCEDURE' ;";
        String query = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'testSP' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations(){{
            oneOf (resultSet).getString("PARAMETER_NAME"); will(returnValue("testSP"));
            oneOf (resultSet).next(); will(returnValue(true));
            oneOf (resultSet).getString("PARAMETER_NAME"); will(returnValue("testSP"));
            oneOf (resultSet).next(); will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container spCategory = new Container();
        schema.addChild(spCategory);

        Container spContainer = new Container();
        spCategory.addChild(spContainer);

        spContainer.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "testSP");

        StoredProcedureLoader loader = new StoredProcedureLoader(connection);
        loader.lazyLoad(spContainer);

        Assert.assertEquals(1, spContainer.getChildren().size());
        Assert.assertEquals("testSP", ((Container) spContainer.getChildren().get(0)).getAttributes().get("PARAMETER_NAME"));
    }

    @Test
    public void shouldExecuteDetailedLoadOfStroredProcedures() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        ResultSet resultSet2 = mock(ResultSet.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);

        String query = "SELECT ROUTINE_CATALOG, ROUTINE_NAME, ROUTINE_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_BODY, EXTERNAL_NAME, PARAMETER_STYLE, SQL_PATH, CREATED, LAST_ALTERED, SQL_MODE, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, DATABASE_COLLATION, ROUTINE_SCHEMA, DATA_TYPE, ROUTINE_DEFINITION, DEFINER, ROUTINE_COMMENT, EXTERNAL_LANGUAGE, IS_DETERMINISTIC, SQL_DATA_ACCESS, SECURITY_TYPE  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'PROCEDURE' AND SPECIFIC_NAME = 'null' ;";

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


        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container spCategory = new Container();
        schema.addChild(spCategory);

        Container spContainer = new Container();
        spCategory.addChild(spContainer);

        spContainer.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "testSP");

        spContainer.addAttribute("SPECIFIC_NAME", null);
        StoredProcedureLoader loader = new StoredProcedureLoader(connection);
        loader.detailedLoad(spContainer);

        Map<String, String> schemaAttributes = spContainer.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }

}