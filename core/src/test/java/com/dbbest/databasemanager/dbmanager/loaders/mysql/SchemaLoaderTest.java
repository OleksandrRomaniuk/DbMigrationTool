package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
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

public class SchemaLoaderTest {

    @Test
    public void shouldExecuteDetailedLoadOfSchema() throws SQLException, ContainerException, DatabaseException, ParsingException {

        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CATALOG_NAME, SQL_PATH, DEFAULT_CHARACTER_SET_NAME, DEFAULT_COLLATION_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'sakila' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Context context = new Context();

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        SchemaLoader schemaLoader = new SchemaLoader(connection);
        schemaLoader.detailedLoad(schemaContainer);

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            if (entry.getKey().equals(SchemaAttributes.SCHEMA_NAME)) {
                Assert.assertEquals("sakila", entry.getValue());
            } else {
            Assert.assertEquals(null, entry.getValue());
            }

        }
    }
}