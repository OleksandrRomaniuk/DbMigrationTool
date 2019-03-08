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

public class ConstraintCategoryLoaderTest {

    @Test
    public void shouldExecuteLazyLoadOfConstraints() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CONSTRAINT_CATALOG, CONSTRAINT_SCHEMA, TABLE_SCHEMA, TABLE_NAME, CONSTRAINT_TYPE, CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = 'sakila' AND  TABLE_NAME = 'testTable' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations() {{
            oneOf(resultSet).getString("CONSTRAINT_NAME");
            will(returnValue("cName"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("CONSTRAINT_CATALOG");
            will(returnValue("testColumn"));
            oneOf(resultSet).getString("CONSTRAINT_SCHEMA");
            will(returnValue("testColumn"));
            oneOf(resultSet).getString("TABLE_SCHEMA");
            will(returnValue("testColumn"));
            oneOf(resultSet).getString("TABLE_NAME");
            will(returnValue("testColumn"));
            oneOf(resultSet).getString("CONSTRAINT_TYPE");
            will(returnValue("testColumn"));
            oneOf(resultSet).getString("CONSTRAINT_NAME");
            will(returnValue("cName"));
            oneOf(resultSet).next();
            will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Context context = new Context();

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCategory = new Container();
        schemaContainer.addChild(tableCategory);
        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        Container constraintCategory = new Container();
        table.addChild(constraintCategory);

        ConstraintCategoryLoader loader = new ConstraintCategoryLoader();
        loader.setConnection(connection);
        loader.lazyLoad(constraintCategory);

        Assert.assertEquals(1, constraintCategory.getChildren().size());
        Assert.assertEquals("cName", ((Container) constraintCategory.getChildren().get(0)).getAttributes().get("CONSTRAINT_NAME"));
    }
}