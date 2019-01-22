package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForeignKeyPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(TableCategoriesTagNameCategories.ConstraintCategory.getElement());
        tableContainer.addChild(constraintCategoryContainer);

        Container pkConstrain1 = new Container();
        pkConstrain1.addAttribute(TableConstraintAttributes.CONSTRAINT_TYPE.getElement(), "FOREIGN KEY");
        Container pkConstrain2 = new Container();
        pkConstrain2.addAttribute(TableConstraintAttributes.CONSTRAINT_TYPE.getElement(), "FOREIGN KEY");
        constraintCategoryContainer.addChild(pkConstrain1);
        constraintCategoryContainer.addChild(pkConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id");
        subConstraint1.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "3");
        subConstraint1.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "table1");
        subConstraint1.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        subConstraint1.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "2");
        subConstraint1.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "Col2");
        pkConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id1");
        subConstraint2.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "1");
        subConstraint2.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "table1");
        subConstraint2.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        subConstraint2.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "3");
        subConstraint2.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "Col3");
        pkConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id2");
        subConstraint3.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "2");
        subConstraint3.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "table1");
        subConstraint3.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        subConstraint3.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "1");
        subConstraint3.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "Col1");
        pkConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id3");
        subConstraint4.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "1");
        subConstraint4.addAttribute(FkAttributes.REFERENCED_TABLE_NAME.getElement(), "table1");
        subConstraint4.addAttribute(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement(), "sakila");
        subConstraint4.addAttribute(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement(), "1");
        subConstraint4.addAttribute(FkAttributes.REFERENCED_COLUMN_NAME.getElement(), "Col11");
        pkConstrain2.addChild(subConstraint4);

        ForeignKeyPrinterHelper fkHelper = new ForeignKeyPrinterHelper();
        System.out.println(fkHelper.execute(tableContainer));
        //assertEquals(primaryKeyPrinterHelper.execute(tableContainer), "");
    }
}