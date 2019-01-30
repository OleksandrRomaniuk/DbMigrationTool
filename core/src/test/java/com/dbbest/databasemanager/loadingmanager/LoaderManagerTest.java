package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoadersPrinterDatabaseTypes;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TypeSupportConstants;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.SchemaLoader;
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

public class LoaderManagerTest {

    @Test
    public void loadLazy() {
    }

    @Test
    public void loadDetails() {
    }

    @Test
    public void loadFull() throws ParsingException, ContainerException, DatabaseException, SQLException {

        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CATALOG_NAME, SCHEMA_NAME, DEFAULT_CHARACTER_SET_NAME, DEFAULT_COLLATION_NAME, SQL_PATH FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'sakila' ;";

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

        context.setDbType(LoadersPrinterDatabaseTypes.MYSQL);

        Container container = new Container();
        container.setName(LoaderPrinterName.SCHEMA);

        LoaderManager loaderManager = LoaderManager.getInstance();
        loaderManager.loadDetails(container);

        Map<String, String> schemaAttributes = container.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }
}