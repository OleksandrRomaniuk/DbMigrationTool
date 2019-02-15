package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
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

public class StoredProcedureCategoryLoaderTest {

    @Test
    public void shouldExecuteLazyLoadOfStroredProcedures() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        //String query = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'PROCEDURE' ;";
        String query = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = 'sakila' AND SPECIFIC_NAME = 'testSP' ;";

        Mockery mockery = new Mockery();
        Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        Mockery mockery1 = new Mockery();
        ResultSet resultSet = mockery1.mock(ResultSet.class);
        mockery1.checking(new Expectations(){{
            oneOf (resultSet).getString("PARAMETER_NAME"); will(returnValue("testSP"));
            oneOf (resultSet).next(); will(returnValue(true));
            oneOf (resultSet).getString("PARAMETER_NAME"); will(returnValue("testSP"));
            oneOf (resultSet).next(); will(returnValue(false));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container spCategory = new Container();
        schema.addChild(spCategory);

        Container spContainer = new Container();
        spCategory.addChild(spContainer);

        spContainer.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "testSP");

        StoredProcedureLoader loader = new StoredProcedureLoader(connection);
        loader.lazyLoad(spContainer);

        Assert.assertEquals(1, spContainer.getChildren().size());
        Assert.assertEquals("testSP", ((Container) spContainer.getChildren().get(0)).getAttributes().get("PARAMETER_NAME"));
    }
}