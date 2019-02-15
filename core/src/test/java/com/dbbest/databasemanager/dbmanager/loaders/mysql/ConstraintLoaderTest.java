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

public class ConstraintLoaderTest {

    @Test
    public void shouldExecuteDetailLoadOfConstraints() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT CONSTRAINT_CATALOG, CONSTRAINT_SCHEMA, TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, POSITION_IN_UNIQUE_CONSTRAINT, REFERENCED_TABLE_SCHEMA, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME, CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = 'sakila' AND TABLE_NAME = 'testTable' AND  CONSTRAINT_NAME = 'test' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        Container schemaContainer = new Container();
        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container tableCategory = new Container();
        schemaContainer.addChild(tableCategory);
        Container table = new Container();
        tableCategory.addChild(table);
        table.addAttribute("TABLE_NAME", "testTable");
        Container constraintCategory = new Container();
        table.addChild(constraintCategory);
        Container constraint = new Container();
        constraintCategory.addChild(constraint);
        constraint.addAttribute("CONSTRAINT_NAME", "test");
        ConstraintLoader loader = new ConstraintLoader(connection);
        loader.detailedLoad(constraint);

        Map<String, String> attributes = constraint.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
                Assert.assertEquals("test", entry.getValue());
            }
        Assert.assertEquals(1, constraint.getChildren().size());
        Assert.assertEquals(13, ((Container)constraint.getChildren().get(0)).getAttributes().size());
    }

}