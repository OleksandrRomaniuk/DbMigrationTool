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

public class ForeignKeyLoaderTest {
    @Test
    public void shouldExecuteLazyLoadOfForeignKeys() throws SQLException, DatabaseException, ContainerException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT DISTINCT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS WHERE information_schema.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'FOREIGN KEY' AND information_schema.TABLE_CONSTRAINTS.TABLE_SCHEMA = 'sakila' AND information_schema.TABLE_CONSTRAINTS.TABLE_NAME = 'testTable' ;";

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
            will(returnValue("testColumn"));
            oneOf(resultSet).next();
            will(returnValue(true));
            oneOf(resultSet).getString("CONSTRAINT_NAME");
            will(returnValue("testColumn"));
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

        Container fkCategory = new Container();
        table.addChild(fkCategory);

        //Container fkContainer = new Container();
        //fkCategory.addChild(fkContainer);

        //fkContainer.addAttribute("CONSTRAINT_NAME", null);

        ForeignKeyCategoryLoader loader = new ForeignKeyCategoryLoader(connection);
        loader.lazyLoad(fkCategory);

        Assert.assertEquals(1, fkCategory.getChildren().size());
        Assert.assertEquals("testColumn", ((Container) fkCategory.getChildren().get(0)).getAttributes().get("CONSTRAINT_NAME"));
    }

    @Test
    public void shouldExecuteDetailLoadOfForeignKeys() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CONSTRAINT_CATALOG, CONSTRAINT_SCHEMA, TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME, ORDINAL_POSITION, POSITION_IN_UNIQUE_CONSTRAINT, REFERENCED_TABLE_SCHEMA, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = 'sakila' AND TABLE_NAME = 'testTable' AND  CONSTRAINT_NAME = 'null' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Context context = new Context();

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");

        Container tableCategory = new Container();
        schemaContainer.addChild(tableCategory);

        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");

        Container fkCategory = new Container();
        table.addChild(fkCategory);

        Container fkContainer = new Container();
        fkCategory.addChild(fkContainer);

        fkContainer.addAttribute("CONSTRAINT_NAME", null);
        ForeignKeyLoader loader = new ForeignKeyLoader(connection);
        loader.detailedLoad(fkContainer);

        Map<String, String> schemaAttributes = fkContainer.getAttributes();
        for (Map.Entry<String, String> entry : schemaAttributes.entrySet()) {
            Assert.assertEquals(null, entry.getValue());
        }
    }

}