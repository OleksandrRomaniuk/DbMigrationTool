package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

public class ForeignKeyPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(TableCategoriesTagNameCategories.ConstraintCategory.getElement());
        tableContainer.addChild(constraintCategoryContainer);

        Container pkConstrain1 = new Container();
        pkConstrain1.addAttribute(AttributeSingleConstants.CONSTRAINT_TYPE, "FOREIGN KEY");
        Container pkConstrain2 = new Container();
        pkConstrain2.addAttribute(AttributeSingleConstants.CONSTRAINT_TYPE, "FOREIGN KEY");
        constraintCategoryContainer.addChild(pkConstrain1);
        constraintCategoryContainer.addChild(pkConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id");
        subConstraint1.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "3");
        subConstraint1.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "table1");
        subConstraint1.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        subConstraint1.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "2");
        subConstraint1.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "Col2");
        pkConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id1");
        subConstraint2.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "1");
        subConstraint2.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "table1");
        subConstraint2.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        subConstraint2.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "3");
        subConstraint2.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "Col3");
        pkConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id2");
        subConstraint3.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "2");
        subConstraint3.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "table1");
        subConstraint3.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        subConstraint3.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "1");
        subConstraint3.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "Col1");
        pkConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id3");
        subConstraint4.addAttribute(AttributeSingleConstants.ORDINAL_POSITION, "1");
        subConstraint4.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_NAME, "table1");
        subConstraint4.addAttribute(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA, "sakila");
        subConstraint4.addAttribute(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT, "1");
        subConstraint4.addAttribute(AttributeSingleConstants.REFERENCED_COLUMN_NAME, "Col11");
        pkConstrain2.addChild(subConstraint4);

        ForeignKeyPrinterHelper fkHelper = new ForeignKeyPrinterHelper();
        System.out.println(fkHelper.execute(tableContainer));
        //assertEquals(primaryKeyPrinterHelper.execute(tableContainer), "");
    }
}