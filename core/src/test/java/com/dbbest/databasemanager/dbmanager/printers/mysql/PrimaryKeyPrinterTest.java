package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimaryKeyPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(NameConstants.TABLE_CONSTRAINTS);
        tableContainer.addChild(constraintCategoryContainer);

        Container pkConstrain1 = new Container();
        pkConstrain1.addAttribute(ConstraintAttributes.CONSTRAINT_TYPE, "PRIMARY KEY");
        Container pkConstrain2 = new Container();
        pkConstrain2.addAttribute(ConstraintAttributes.CONSTRAINT_TYPE, "PRIMARY KEY");
        constraintCategoryContainer.addChild(pkConstrain1);
        constraintCategoryContainer.addChild(pkConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id");
        subConstraint1.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "3");
        pkConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id1");
        subConstraint2.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "1");
        pkConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id2");
        subConstraint3.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "2");
        pkConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(TableColumnAttributes.COLUMN_NAME, "store_id3");
        subConstraint4.addAttribute(ForeignKeyAttributes.ORDINAL_POSITION, "1");
        pkConstrain2.addChild(subConstraint4);


        PrimaryKeyPrinter primaryKeyPrinter = new PrimaryKeyPrinter();
        assertEquals(primaryKeyPrinter.execute(tableContainer), "PRIMARY KEY (store_id1, store_id2, store_id),\n" +
                "PRIMARY KEY (store_id3),\n");
    }
}
