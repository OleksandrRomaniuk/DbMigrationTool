package com.dbbest.databasemanager.dbmanager.loaders.mysql;

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

public class ViewLoaderTest {

    @Test
    public void shouldExecuteDetailLoadOfViews() throws SQLException, DatabaseException, ContainerException {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        String query = "SELECT TABLE_CATALOG, CHECK_OPTION, IS_UPDATABLE, DEFINER, SECURITY_TYPE, CHARACTER_SET_CLIENT, COLLATION_CONNECTION, VIEW_DEFINITION, TABLE_SCHEMA FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = 'sakila' AND TABLE_NAME = 'tableTest' ;";

        Mockery mockery = new Mockery();
        final Connection connection = mockery.mock(Connection.class);
        mockery.checking(new Expectations() {{
            oneOf(connection).prepareStatement(query);
            will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME, "sakila");
        Container viewCategory = new Container();
        schema.addChild(viewCategory);
        Container view = new Container();
        viewCategory.addChild(view);
        view.addAttribute("TABLE_NAME", "tableTest");
        ViewLoader loader = new ViewLoader(connection);
        loader.detailedLoad(view);

        Map<String, String> attributes = view.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getKey().equals("TABLE_NAME")) {
                Assert.assertEquals("tableTest", entry.getValue());
            } else {
                Assert.assertEquals(null, entry.getValue());
            }
        }
        Assert.assertEquals(10, view.getAttributes().size());
    }
}