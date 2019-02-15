package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
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
    public void shouldExecuteDetailedLoadOfTables() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'sakila' AND TABLE_TYPE = 'BASE TABLE' ;";

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

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCategory = new Container();
        schema.addChild(tableCategory);

        TableCategoryLoader tableLoader = new TableCategoryLoader(connection);
        tableLoader.lazyLoad(tableCategory);

        Assert.assertEquals(1, tableCategory.getChildren().size());
        Assert.assertEquals("testTable", ((Container)tableCategory.getChildren().get(0)).getAttributes().get("TABLE_NAME"));
    }

    @Test
    public void shouldExecuteDetailedLoadingForTables() throws ParsingException, ContainerException, DatabaseException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_CATALOG, TABLE_TYPE, VERSION, TABLE_ROWS, DATA_LENGTH, MAX_DATA_LENGTH, INDEX_LENGTH, CREATE_TIME, UPDATE_TIME, CHECK_TIME, CREATE_OPTIONS, AVG_ROW_LENGTH, CHECKSUM, TABLE_COMMENT, ENGINE, ROW_FORMAT, DATA_FREE, AUTO_INCREMENT, TABLE_COLLATION, TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'sakila' AND table_name = 'testTable' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Container schemaTable = new Container();
        schemaTable.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCotegory = new Container();
        schemaTable.addChild(tableCotegory);

        Container table = new Container();
        tableCotegory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        TableLoader loader = new TableLoader(connection);
        loader.detailedLoad(table);

        Map<String, String> attributes = table.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if(!entry.getKey().equals("TABLE_NAME")) {
                Assert.assertEquals(null, entry.getValue());
            } else {
                Assert.assertEquals("testTable", entry.getValue());
            }
        }
        Assert.assertEquals(21, table.getAttributes().size());

    }
}