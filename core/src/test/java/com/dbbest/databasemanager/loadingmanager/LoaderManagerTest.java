package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.databasemanager.connectionbuilder.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoadersPrinterDatabaseTypesEnum;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.SchemaCategoriesTagNameConstants;
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

        Container container = new Container();
        container.addAttribute(TypeSupportConstants.DatabaseType.toString(), LoadersPrinterDatabaseTypesEnum.MYSQL.toString());
        container.addAttribute(TypeSupportConstants.LoaderPrinterType.toString(), LoaderPrinterName.SCHEMA);

        LoaderManager loaderManager = new LoaderManager();
        loaderManager.loadFull(connection, container);

        for(Container container1: (List<Container>)container.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren())
        System.out.println(container1.getName());
    }
}