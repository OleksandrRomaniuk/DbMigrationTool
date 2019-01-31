package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.*;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class SchemaLoaderTestDelete {

    private Connection connection;
    @Before
    public void setUp() throws Exception {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        connection = simpleConnectionBuilder.getConnection("mysql");
        Context.getInstance().setConnection(connection);
        Context.getInstance().setSchemaName("sakila");
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null)
        connection.close();
    }

    @Test
    public void lazyLoad() throws DatabaseException, ContainerException, SQLException {

        Container schemaContainer1 = new Container();
        SchemaLoader schemaLoader1 = new SchemaLoader();
        schemaLoader1.fullLoad(schemaContainer1);
        System.out.println(schemaContainer1.getName());
        System.out.println("-----------------------------------------------");
        List<Container> schemaCategories = schemaContainer1.getChildren();
        for (Container schemaCategory: schemaCategories) {
            System.out.println("-----------------------------------------------");
            System.out.println(schemaCategory.getName());
            System.out.println("-----------------------------------------------");
            List<Container> subCategories = schemaCategory.getChildren();
            for (Container subCategory: subCategories) {
                System.out.println("-----------------------------------------------");
                System.out.println(subCategory.getName());
                System.out.println("-----------------------------------------------");
                List<Container> tableCategories = subCategory.getChildren();
                if (tableCategories != null && !tableCategories.isEmpty()) {
                    for (Container tableCategory: tableCategories) {
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println(tableCategory.getName());
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        List<Container> subTableCategories = tableCategory.getChildren();
                        if (subTableCategories != null && !subCategories.isEmpty()) {
                            for (Container child: subTableCategories) {
                                System.out.println("*********************************************");
                                System.out.println(child.getParent().getParent().getParent().getParent().getName() + " - " + child.getParent().getParent().getParent().getName() + " - " + child.getParent().getParent().getName() + " + " + child.getParent().getName() + " - " + child.getName());
                                System.out.println("*********************************************");

                                Map<String, String> attrs = child.getAttributes();
                                for (Map.Entry<String, String> enty: attrs.entrySet()) {
                                    System.out.println(enty.getKey() + " - " + enty.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}