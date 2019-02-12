package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
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
            oneOf (resultSet).getString("TABLE_NAME"); will(returnValue("testTable"));
            oneOf (resultSet).next(); will(returnValue(true));
            oneOf (resultSet).getString("TABLE_NAME"); will(returnValue("testTable"));
            oneOf (resultSet).next(); will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = new Context();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        TableLoader tableLoader = new TableLoader(context);
        tableLoader.lazyLoad(container);

        Assert.assertEquals(1, container.getChildren().size());
        Assert.assertEquals("testTable", ((Container)container.getChildren().get(0)).getAttributes().get("TABLE_NAME"));
        Assert.assertEquals(5, ((Container)container.getChildren().get(0)).getChildren().size());
    }

    @Test
    public void shouldExecuteDetailedLoadingForTables() throws ParsingException, ContainerException, DatabaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_CATALOG, TABLE_SCHEMA, TABLE_TYPE, VERSION, TABLE_ROWS, DATA_LENGTH, MAX_DATA_LENGTH, INDEX_LENGTH, CREATE_TIME, UPDATE_TIME, CHECK_TIME, CREATE_OPTIONS, AVG_ROW_LENGTH, CHECKSUM, TABLE_COMMENT, ENGINE, ROW_FORMAT, DATA_FREE, AUTO_INCREMENT, TABLE_COLLATION FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'sakila' AND table_name = 'testTable' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Context context = new Context();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container container = new Container();
        container.addAttribute("TABLE_NAME", "testTable");
        TableLoader loader = new TableLoader(context);
        loader.detailedLoad(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            if(!entry.getKey().equals("TABLE_NAME")) {
                Assert.assertEquals(null, entry.getValue());
            } else {
                Assert.assertEquals("testTable", entry.getValue());
            }
        }

    }
}