package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Assert;
import org.junit.Test;

public class SchemaPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container schemaContainer = new Container();
        schemaContainer.setName("testDB");
        schemaContainer.addAttribute(AttributeSingleConstants.DEFAULT_CHARACTER_SET_NAME, "utf8mb4");
        schemaContainer.addAttribute(AttributeSingleConstants.DEFAULT_COLLATION_NAME, "utf8mb4_0900_ai_ci");

        SchemaPrinter schemaPrinter = new SchemaPrinter();
        String query = schemaPrinter.execute(schemaContainer);

        Assert.assertEquals("CREATE SCHEMA IF NOT EXISTS testDB CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';", query);
    }
}