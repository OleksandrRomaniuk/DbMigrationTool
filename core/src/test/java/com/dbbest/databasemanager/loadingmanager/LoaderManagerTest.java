package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoadersPrinterDatabaseTypes;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TypeSupportConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoaderManagerTest {

    @Test
    public void loadLazy() {
    }

    @Test
    public void loadDetails() {
    }

    @Test
    public void loadFull() throws ParsingException, ContainerException, DatabaseException, SQLException {
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection("mysql");

        Context.getInstance().setSchemaName("sakila");
        Context.getInstance().setConnection(connection);


        Container container = new Container();
        container.setName(LoaderPrinterName.SCHEMA);
        Context.getInstance().setDbType(LoadersPrinterDatabaseTypes.MYSQL);

        LoaderManager loaderManager = LoaderManager.getInstance();
        loaderManager.loadFull(container);

        for(Container container1: (List<Container>)container.getChildByName(LoaderPrinterName.STORED_PROCEDURES).getChildren())
        System.out.println(container1.getAttributes().get(AttributeSingleConstants.FUNCTION_PROCEDURE_NAME));
    }
}