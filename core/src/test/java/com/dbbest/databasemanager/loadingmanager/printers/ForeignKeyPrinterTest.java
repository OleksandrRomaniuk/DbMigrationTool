package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForeignKeyPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container fkContainer = new Container();
        fkContainer.setName("testFK");

        Container constraint1 = new Container();
        constraint1.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        constraint1.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "refTable1");
        constraint1.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "2");
        constraint1.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "refCol1");
        constraint1.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "1");
        constraint1.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "col1");

        Container constraint2 = new Container();
        constraint2.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        constraint2.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "refTable2");
        constraint2.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "1");
        constraint2.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "refCol2");
        constraint2.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "2");
        constraint2.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "col2");

        fkContainer.addChild(constraint1);
        fkContainer.addChild(constraint2);

        ForeignKeyPrinter foreignKeyPrinter = new ForeignKeyPrinter();
        assertEquals("FOREIGN KEY testFK (col1, col2) REFERENCES sakila.refTable1 (refCol2, refCol1)",
            foreignKeyPrinter.execute(fkContainer));
    }
}