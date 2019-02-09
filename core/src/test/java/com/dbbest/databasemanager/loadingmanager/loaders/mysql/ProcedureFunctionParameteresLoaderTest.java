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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcedureFunctionParameteresLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfProcedureFunctionParameteres() throws SQLException, DatabaseException, ContainerException {
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        final String query = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'procParam' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
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
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container parent = new Container();
        parent.addAttribute("TABLE_NAME", "testTable");
        Container container = new Container();
        container.addAttribute("SPECIFIC_NAME", "procParam");
        parent.addChild(container);


        ProcedureFunctionParameteresLoader loader = new ProcedureFunctionParameteresLoader();
        loader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) container.getChildren().get(0)).getAttributes().get("PARAMETER_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfIndexes() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        final PreparedStatement preparedStatement = mock(PreparedStatement.class);
        final String query = "SELECT ORDINAL_POSITION, SPECIFIC_CATALOG, SPECIFIC_SCHEMA, SPECIFIC_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_TYPE, PARAMETER_MODE FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'testTable' AND PARAMETER_NAME = 'null' ;";

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
        parent2.addAttribute("SPECIFIC_NAME", "testTable");
        parent1.addChild(parent2);

        Container container = new Container();
        parent2.addChild(container);
        container.addAttribute("PARAMETER_NAME", null);
        ProcedureFunctionParameteresLoader loader = new ProcedureFunctionParameteresLoader();
        loader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}