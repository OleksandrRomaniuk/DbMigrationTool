package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ForeignKeyLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ForeignKeyLoaderTest {

    @Test
    public void lazyLoad() throws ContainerException, SQLException, DatabaseException {
        ForeignKeyLoader foreignKeyLoader = new ForeignKeyLoader();

        Container schema = new Container();
        schema.addAttribute(SchemaAttributes.SCHEMA_NAME.getElement(), "testAtr");
        Container child1 = new Container();
        schema.addChild(child1);
        Container child2 = new Container();
        child2.setName("test");
        child1.addChild(child2);

        Container fkCategoryContainer = new Container();
        fkCategoryContainer.setName("test");
        child2.addChild(fkCategoryContainer);

        Mockery cnt2 = new Mockery();
        final ResultSet resultSet = cnt2.mock(ResultSet.class);
        cnt2.checking(new Expectations(){{
            oneOf (resultSet).next(); will(returnValue(true));
            oneOf (resultSet).next(); will(returnValue(false));
            oneOf (resultSet).getString("FK_NAME"); will(returnValue("testChild"));
        }});

        Mockery cnt1 = new Mockery();
        final DatabaseMetaData databaseMetaData = cnt1.mock(DatabaseMetaData.class);
        cnt1.checking(new Expectations(){{
            oneOf (databaseMetaData).getImportedKeys("testAtr", "testAtr", "test"); will(returnValue(resultSet));
        }});

        Mockery cnt = new Mockery();
        final Connection connection = cnt.mock(Connection.class);
        cnt.checking(new Expectations(){{
            oneOf (connection).getMetaData(); will(returnValue(databaseMetaData));
        }});

        foreignKeyLoader.lazyLoad(fkCategoryContainer);
        assertEquals(fkCategoryContainer.getName(), "test");
        assertEquals(fkCategoryContainer.hasChildren(), true);
    }

    @Test
    public void detailedLoad() throws SQLException, DatabaseException, ContainerException {
        String query = "SELECT *  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = 'sakila' AND TABLE_NAME = 'table' AND  CONSTRAINT_NAME = 'Sakila'";
        ResultSet rs = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        Mockery cnt = new Mockery();
        final Connection connection = cnt.mock(Connection.class);
        cnt.checking(new Expectations(){{
            oneOf (connection).prepareStatement(query); will(returnValue(preparedStatement));
        }});

        when(preparedStatement.executeQuery()).thenReturn(rs);
        when(rs.getString(SchemaAttributes.SCHEMA_NAME.getElement())).thenReturn("test");
        when(rs.next()).thenReturn(true);

        Container schema = new Container();
        schema.setName("sakila");
        Container tableCat = new Container();
        tableCat.setName("tableCat");
        schema.addChild(tableCat);
        Container table = new Container();
        table.setName("table");
        tableCat.addChild(table);
        Container fks = new Container();
        table.addChild(fks);

        Container schemaContainer = new Container();
        schemaContainer.setName("Sakila");
        fks.addChild(schemaContainer);
        ForeignKeyLoader foreignKeyLoader = new ForeignKeyLoader();
        foreignKeyLoader.detailedLoad(schemaContainer);

        Assert.assertEquals(schemaContainer.hasAttributes(), true);
    }
}