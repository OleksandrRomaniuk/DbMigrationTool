package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForeignKeyPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container fkContainer = new Container();
        fkContainer.addAttribute(AttributeSingleConstants.CONSTRAINT_NAME, "testFK");

        Container constraint1 = new Container();
        constraint1.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        constraint1.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "refTable1");
        constraint1.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "2");
        constraint1.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "refCol1");
        constraint1.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "1");
        constraint1.addAttribute(AttributeSingleConstants.COLUMN_NAME, "col1");

        Container constraint2 = new Container();
        constraint2.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        constraint2.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "refTable2");
        constraint2.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "1");
        constraint2.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "refCol2");
        constraint2.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "2");
        constraint2.addAttribute(AttributeSingleConstants.COLUMN_NAME, "col2");

        fkContainer.addChild(constraint1);
        fkContainer.addChild(constraint2);

        ForeignKeyPrinter foreignKeyPrinter = new ForeignKeyPrinter();
        assertEquals("FOREIGN KEY testFK (col1, col2) REFERENCES sakila.refTable1 (refCol2, refCol1)",
            foreignKeyPrinter.execute(fkContainer));
    }
}