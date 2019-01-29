package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimaryKeyPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(TableCategoriesTagNameCategories.ConstraintCategory.getElement());
        tableContainer.addChild(constraintCategoryContainer);

        Container pkConstrain1 = new Container();
        pkConstrain1.addAttribute(AttributeSingleConstants.CONSTRAINT_TYPE, "PRIMARY KEY");
        Container pkConstrain2 = new Container();
        pkConstrain2.addAttribute(AttributeSingleConstants.CONSTRAINT_TYPE, "PRIMARY KEY");
        constraintCategoryContainer.addChild(pkConstrain1);
        constraintCategoryContainer.addChild(pkConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id");
        subConstraint1.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "3");
        pkConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id1");
        subConstraint2.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "1");
        pkConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id2");
        subConstraint3.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "2");
        pkConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id3");
        subConstraint4.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "1");
        pkConstrain2.addChild(subConstraint4);


        PrimaryKeyPrinterHelper primaryKeyPrinterHelper = new PrimaryKeyPrinterHelper();
        assertEquals(primaryKeyPrinterHelper.execute(tableContainer), "PRIMARY KEY (store_id1, store_id2, store_id),\n" +
            "PRIMARY KEY (store_id3),\n");
    }
}