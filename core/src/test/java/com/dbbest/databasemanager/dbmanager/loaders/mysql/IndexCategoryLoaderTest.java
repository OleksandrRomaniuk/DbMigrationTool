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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IndexCategoryLoaderTest {

    @Test
    public void executeLazyLoaderQuery() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT DISTINCT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = 'sakila' AND TABLE_NAME = 'testTable' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("INDEX_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("INDEX_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = new Context();


        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCategory = new Container();
        schema.addChild(tableCategory);
        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        Container indexCategory = new Container();
        table.addChild(indexCategory);


        IndexCategoryLoader loader = new IndexCategoryLoader(connection);
        loader.lazyLoad(indexCategory);

        Assert.assertEquals(1, indexCategory.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) indexCategory.getChildren().get(0)).getAttributes().get("INDEX_NAME"));
    }
}