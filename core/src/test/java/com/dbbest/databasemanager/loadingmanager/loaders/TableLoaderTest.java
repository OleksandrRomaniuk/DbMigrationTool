package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.SchemaLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.TableLoader;
import com.dbbest.databasemanager.loadingmanager.printers.TablePrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TableLoaderTest {


    @Test
    public void shouldExecuteLazyLoadOfTwoTables() throws SQLException, DatabaseException, ContainerException {

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'sakila' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations(){{
            oneOf (resultSet).next(); will(returnValue(true));
            oneOf (resultSet).getString("TABLE_NAME"); will(returnValue("testTable"));
            oneOf (resultSet).next(); will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        TableLoader tableLoader = new TableLoader();
        tableLoader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testTable", ((Container)container.getChildren().get(0)).getAttributes().get("TABLE_NAME"));
        Assert.assertEquals(5, ((Container)container.getChildren().get(0)).getChildren().size());
    }

    @Test
    public void lazyLoad() throws ParsingException, ContainerException, DatabaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CATALOG_NAME, SCHEMA_NAME, DEFAULT_CHARACTER_SET_NAME, DEFAULT_COLLATION_NAME, SQL_PATH FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'sakila' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }

    }
}