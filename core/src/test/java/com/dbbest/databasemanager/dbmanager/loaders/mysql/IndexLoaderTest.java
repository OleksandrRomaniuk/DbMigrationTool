package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
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

public class IndexLoaderTest {

    @Test
    public void shouldExecuteDetailLoadOfIndexes() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_CATALOG, INDEX_SCHEMA, COLLATION, CARDINALITY, PACKED, NULLABLE, COMMENT, INDEX_COMMENT, NON_UNIQUE, INDEX_TYPE, TABLE_SCHEMA, TABLE_NAME, SEQ_IN_INDEX, COLUMN_NAME, SUB_PART FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = 'sakila' AND TABLE_NAME = 'testTable'  AND INDEX_NAME =  'null' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        /*ResultSet resultSet = mockery.mock(ResultSet.class);
        mockery.checking(new Expectations() {{
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});*/

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container tableCategory = new Container();
        schema.addChild(tableCategory);

        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");

        Container indeCategory = new Container();
        table.addChild(indeCategory);

        Container index = new Container();
        indeCategory.addChild(index);
        index.addAttribute("INDEX_NAME", null);
        IndexLoader loader = new IndexLoader(connection);
        loader.detailedLoad(index);

        Map<String, String> schemaAttributes = index.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}
