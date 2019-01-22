package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniquePrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        Container constraintCategoryContainer = new Container();
        constraintCategoryContainer.setName(TableCategoriesTagNameCategories.ConstraintCategory.getElement());
        tableContainer.addChild(constraintCategoryContainer);

        Container uniqueConstrain1 = new Container();
        uniqueConstrain1.addAttribute(TableConstraintAttributes.CONSTRAINT_TYPE.getElement(), "UNIQUE");
        uniqueConstrain1.addAttribute(TableConstraintAttributes.CONSTRAINT_NAME.getElement(), "test1");
        Container uniqueConstrain2 = new Container();
        uniqueConstrain2.addAttribute(TableConstraintAttributes.CONSTRAINT_TYPE.getElement(), "UNIQUE");
        constraintCategoryContainer.addChild(uniqueConstrain1);
        constraintCategoryContainer.addChild(uniqueConstrain2);

        Container subConstraint1 = new Container();
        subConstraint1.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id");
        subConstraint1.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "3");
        uniqueConstrain1.addChild(subConstraint1);

        Container subConstraint2 = new Container();
        subConstraint2.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id1");
        subConstraint2.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "2");
        uniqueConstrain1.addChild(subConstraint2);

        Container subConstraint3 = new Container();
        subConstraint3.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id2");
        subConstraint3.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "1");
        uniqueConstrain1.addChild(subConstraint3);

        Container subConstraint4 = new Container();
        subConstraint4.addAttribute(FkAttributes.COLUMN_NAME.getElement(), "store_id3");
        subConstraint4.addAttribute(FkAttributes.ORDINAL_POSITION.getElement(), "1");
        uniqueConstrain2.addChild(subConstraint4);

        UniquePrinterHelper uniquePrinterHelper = new UniquePrinterHelper();
        assertEquals(uniquePrinterHelper.execute(tableContainer), "UNIQUE test1  (store_id2, store_id1, store_id),\n" +
            "UNIQUE  (store_id3),\n");
    }
}