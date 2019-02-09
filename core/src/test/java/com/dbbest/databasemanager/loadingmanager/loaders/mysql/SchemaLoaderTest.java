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

public class SchemaLoaderTest {

    @Test
    public void shouldSetSchemaNameAndCreateFourChildren() throws ContainerException, DatabaseException, SQLException {
        Container schemaContainer = new Container();
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.lazyLoad(schemaContainer);

        Assert.assertEquals("Schema", schemaContainer.getName());
        Assert.assertEquals(4, schemaContainer.getChildren().size());
    }

    @Test
    public void detailedLoad() throws SQLException, ContainerException, DatabaseException, ParsingException {

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

        Context context = Context.getInstance();
        context.setConnection(connection);
        context.setSchemaName("sakila");

        Container schemaContainer = new Container();
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.detailedLoad(schemaContainer);

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}