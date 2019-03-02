package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForeignKeyPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container fkContainer = new Container();
        fkContainer.addAttribute(ConstraintAttributes.CONSTRAINT_NAME, "testFK");

        Container constraint1 = new Container();
        constraint1.addAttribute(ForeignKeyAttributes.REFERENCED_TABLE_SCHEMA, "sakila");
        constraint1.addAttribute(ForeignKeyAttributes.REFERENCED_TABLE_NAME, "refTable1");
        constraint1.addAttribute(ForeignKeyAttributes.POSITION_IN_UNIQUE_CONSTRAINT, "2");
        constraint1.addAttribute(ForeignKeyAttributes.REFERENCED_COLUMN_NAME, "refCol1");
        constraint1.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "1");
        constraint1.addAttribute(TableColumnAttributes.COLUMN_NAME, "col1");

        Container constraint2 = new Container();
        constraint2.addAttribute(ForeignKeyAttributes.REFERENCED_TABLE_SCHEMA, "sakila");
        constraint2.addAttribute(ForeignKeyAttributes.REFERENCED_TABLE_NAME, "refTable2");
        constraint2.addAttribute(ForeignKeyAttributes.POSITION_IN_UNIQUE_CONSTRAINT, "1");
        constraint2.addAttribute(ForeignKeyAttributes.REFERENCED_COLUMN_NAME, "refCol2");
        constraint2.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "2");
        constraint2.addAttribute(TableColumnAttributes.COLUMN_NAME, "col2");

        fkContainer.addChild(constraint1);
        fkContainer.addChild(constraint2);

        ForeignKeyPrinter foreignKeyPrinter = new ForeignKeyPrinter();
        assertEquals("ALTER TABLE null.null\n" +
                        "ADD FOREIGN KEY testFK (col1, col2) REFERENCES sakila.refTable1 (refCol2, refCol1)",
                foreignKeyPrinter.execute(fkContainer));
    }
}
