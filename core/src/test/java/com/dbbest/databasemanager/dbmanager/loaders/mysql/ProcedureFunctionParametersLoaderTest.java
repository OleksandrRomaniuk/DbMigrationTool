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

public class ProcedureFunctionParametersLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfProcedureFunctionParameteres() throws SQLException, DatabaseException, ContainerException {
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        final String query = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'procParam' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        final ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("PARAMETER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("PARAMETER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(false));

            oneOf(resultSet).getString("PARAMETER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("PARAMETER_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container functionCategory = new Container();
        schemaContainer.addChild(functionCategory);
        Container function = new Container();
        functionCategory.addChild(function);
        function.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "procParam");

        FunctionLoader loader = new FunctionLoader(connection);
        loader.lazyLoad(function);

        Container schemaContainer2 = new Container();
        schemaContainer2.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container functionCategory2 = new Container();
        schemaContainer2.addChild(functionCategory2);
        Container function2 = new Container();
        functionCategory.addChild(function2);
        function2.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "procParam");

        StoredProcedureLoader loader2 = new StoredProcedureLoader(connection);
        loader2.lazyLoad(function2);

        Assert.assertEquals(1, function.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) function.getChildren().get(0)).getAttributes().get("PARAMETER_NAME"));

        Assert.assertEquals(1, function2.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) function2.getChildren().get(0)).getAttributes().get("PARAMETER_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfIndexes() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        final String query = "SELECT ORDINAL_POSITION, SPECIFIC_CATALOG, SPECIFIC_SCHEMA, SPECIFIC_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_TYPE, PARAMETER_MODE FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'testTable' AND PARAMETER_NAME = 'test' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);


        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container functionCategory = new Container();
        schemaContainer.addChild(functionCategory);
        Container function = new Container();
        functionCategory.addChild(function);
        function.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "testTable");

        Container parameter = new Container();
        function.addChild(parameter);
        parameter.addAttribute("PARAMETER_NAME", "test");
        ProcedureFunctionParametersLoader loader = new ProcedureFunctionParametersLoader(connection);
        loader.detailedLoad(parameter);

        Map<String, String> attributes = parameter.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getKey().equals("PARAMETER_NAME")) {
            Assert.assertEquals("test", entry.getValue());
            } else {
                Assert.assertEquals(null, entry.getValue());
            }
        }
        Assert.assertEquals(15, parameter.getAttributes().size());
    }
}