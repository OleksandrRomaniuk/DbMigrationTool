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
    public void shouldSetSchemaNameAndCreateFourChildren() throws ContainerException, DatabaseException, SQLException {
        Container schemaContainer = new Container();
        Mockery mockerySchema = new Mockery();
        Connection connection = mockerySchema.mock(Connection.class);

        PreparedStatement preparedStatementTable = mock(PreparedStatement.class);
        String queryTable = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'sakila' ;";

        Mockery mockeryTable = new Mockery();
        ResultSet resultSetTable = mockeryTable.mock(ResultSet.class);
        mockeryTable.checking(new Expectations(){{
            oneOf (resultSetTable).getString("TABLE_NAME"); will(returnValue("testTable"));
            oneOf (resultSetTable).next(); will(returnValue(true));
            oneOf (resultSetTable).getString("TABLE_NAME"); will(returnValue("testTable"));
            oneOf (resultSetTable).next(); will(returnValue(false));
        }});
        when(preparedStatementTable.executeQuery()).thenReturn(resultSetTable);


        PreparedStatement preparedStatementView = mock(PreparedStatement.class);
        String queryView = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = 'sakila' ;";

        Mockery mockeryView1 = new Mockery();
        ResultSet resultSetView = mockeryView1.mock(ResultSet.class);
        mockeryView1.checking(new Expectations() {{
            oneOf(resultSetView).getString("TABLE_NAME");
            will(returnValue("testTable"));
            oneOf(resultSetView).next();
            will(returnValue(true));
            oneOf(resultSetView).getString("TABLE_NAME");
            will(returnValue("testTable"));
            oneOf(resultSetView).next();
            will(returnValue(false));
        }});
        when(preparedStatementView.executeQuery()).thenReturn(resultSetView);


        PreparedStatement preparedStatementSP = mock(PreparedStatement.class);
        String querySP = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'PROCEDURE' ;";

        Mockery mockerySP1 = new Mockery();
        ResultSet resultSetSP = mockerySP1.mock(ResultSet.class);
        mockerySP1.checking(new Expectations(){{
            oneOf (resultSetSP).getString("SPECIFIC_NAME"); will(returnValue("testSP"));
            oneOf (resultSetSP).next(); will(returnValue(true));
            oneOf (resultSetSP).getString("SPECIFIC_NAME"); will(returnValue("testSP"));
            oneOf (resultSetSP).next(); will(returnValue(false));
        }});

        when(preparedStatementSP.executeQuery()).thenReturn(resultSetSP);


        PreparedStatement preparedStatementFunction = mock(PreparedStatement.class);
        String queryFunction = "SELECT SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = 'sakila' AND ROUTINE_TYPE = 'FUNCTION' ;";

        Mockery mockeryFunction1 = new Mockery();
        ResultSet resultSetFunction = mockeryFunction1.mock(ResultSet.class);
        mockeryFunction1.checking(new Expectations() {{
            oneOf(resultSetFunction).getString("SPECIFIC_NAME");
            will(returnValue("testTable"));
            oneOf(resultSetFunction).next();
            will(returnValue(true));
            oneOf(resultSetFunction).getString("SPECIFIC_NAME");
            will(returnValue("testTable"));
            oneOf(resultSetFunction).next();
            will(returnValue(false));
        }});

        when(preparedStatementFunction.executeQuery()).thenReturn(resultSetFunction);


        mockerySchema.checking(new Expectations(){{
            oneOf (connection).getSchema(); will(returnValue("sakila"));
            oneOf (connection).prepareStatement(queryTable); will(returnValue(resultSetTable));
            oneOf (connection).prepareStatement(queryView); will(returnValue(resultSetView));
            oneOf (connection).prepareStatement(querySP); will(returnValue(resultSetSP));
            oneOf (connection).prepareStatement(queryFunction); will(returnValue(resultSetFunction));
        }});

        SchemaLoader schemaLoader = new SchemaLoader(connection);
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