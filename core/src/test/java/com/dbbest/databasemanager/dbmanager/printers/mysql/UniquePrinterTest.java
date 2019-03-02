package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UniquePrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(NameConstants.TABLE_CONSTRAINTS);
        tableContainer.addChild(constraintCategoryContainer);

        Container uniqueConstrain1 = new Container();
        uniqueConstrain1.addAttribute(ConstraintAttributes.CONSTRAINT_TYPE, "UNIQUE");
        uniqueConstrain1.addAttribute(ConstraintAttributes.CONSTRAINT_NAME, "test1");
        Container uniqueConstrain2 = new Container();
        uniqueConstrain2.addAttribute(ConstraintAttributes.CONSTRAINT_TYPE, "UNIQUE");
        constraintCategoryContainer.addChild(uniqueConstrain1);
        constraintCategoryContainer.addChild(uniqueConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id");
        subConstraint1.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "3");
        uniqueConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id1");
        subConstraint2.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "2");
        uniqueConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id2");
        subConstraint3.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "1");
        uniqueConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id3");
        subConstraint4.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "1");
        uniqueConstrain2.addChild(subConstraint4);

        UniquePrinter uniquePrinter = new UniquePrinter();
        System.out.println(uniquePrinter.execute(tableContainer));
        assertEquals(uniquePrinter.execute(tableContainer), "UNIQUE test1 (store_id2, store_id1, store_id),\n" +
                "UNIQUE (store_id3),\n");
    }
}
