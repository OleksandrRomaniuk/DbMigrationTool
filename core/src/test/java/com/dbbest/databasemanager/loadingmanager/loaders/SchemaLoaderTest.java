package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SchemaLoaderTest {

    @Test
    public void lazyLoad() throws ContainerException, DatabaseException, SQLException {
        SchemaLoader schemaLoader = new SchemaLoader();
        Container schemaContainer = new Container();
        schemaContainer.setName("Test");
        Mockery cnt = new Mockery();
        final Connection connection = cnt.mock(Connection.class);
        schemaLoader.lazyLoad(connection, schemaContainer);
        Assert.assertEquals(schemaContainer.getChildren().size(), 4);
    }

    @Test
    public void detailedLoad() throws SQLException, ContainerException, DatabaseException {
        String query = "SELECT * FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'Sakila'";
        ResultSet rs = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        Mockery cnt = new Mockery();
        final Connection connection = cnt.mock(Connection.class);
        cnt.checking(new Expectations(){{
            oneOf (connection).getCatalog(); will(returnValue("Sakila"));
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.getString(SchemaAttributes.SCHEMA_NAME.getElement())).thenReturn("test");
        when(rs.next()).thenReturn(true);

        SchemaLoader schemaLoader = new SchemaLoader();
        Container schemaContainer = new Container();
        schemaContainer.setName("Sakila");
        schemaLoader.detailedLoad(connection, schemaContainer);

        Assert.assertEquals(schemaContainer.getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()), "test");
    }
}