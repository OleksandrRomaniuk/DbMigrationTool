package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.printers.mysql.TablePrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Assert;
import org.junit.Test;

public class TablePrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container table = new Container();
        table.addAttribute(AttributeSingleConstants.TABLE_NAME, "customer1");
        table.addAttribute(TableAttributes.TABLE_SCHEMA.getElement(), "sakila");
        table.addAttribute(TableAttributes.TABLE_NAME.getElement(), "customer1");
        Container columnCategory = new Container();
        table.addChild(columnCategory);
        columnCategory.setName(LoaderPrinterName.TABLE_COLUMNS);
        Container column1 = new Container();
        column1.setName(LoaderPrinterName.TABLE_COLUMN);
        columnCategory.addChild(column1);
        column1.addAttribute(AttributeSingleConstants.TABLE_NAME, "adressId");
        column1.addAttribute(ColumnAttributes.COLUMN_EXTRA.getElement(), null);
        column1.addAttribute(ColumnAttributes.COLUMN_NAME.getElement(), "address_id");
        column1.addAttribute(ColumnAttributes.COLUMN_TYPE.getElement(), "smallint(5) unsigned");
        column1.addAttribute(ColumnAttributes.COLUMN_COMMENT.getElement(), "Comment test");
        column1.addAttribute(ColumnAttributes.COLUMN_GENERATION_EXPRESSION.getElement(), "");
        column1.addAttribute(ColumnAttributes.COLUMN_IS_NULLABLE.getElement(), "NO");
        column1.addAttribute(ColumnAttributes.COLUMN_DEFAULT.getElement(), null);
        column1.addAttribute(ColumnAttributes.COLUMN_EXTRA.getElement(), "");
        column1.addAttribute(ColumnAttributes.COLUMN_KEY.getElement(), "MUL");
        column1.addAttribute(ColumnAttributes.COLUMN_COLLATION_NAME.getElement(), null);
        Container indexCategory = new Container();
        indexCategory.setName(LoaderPrinterName.TABLE_INDEXES);
        table.addChild(indexCategory);

        Container constrCategory = new Container();
        constrCategory.setName(LoaderPrinterName.TABLE_CONSTRAINTS);
        table.addChild(constrCategory);

        TablePrinter tablePrinter = new TablePrinter();

        Assert.assertEquals(tablePrinter.execute(table), "CREATE TABLE IF NOT EXISTS sakila.customer1 (\n" +
            "address_id smallint(5) unsigned NOT NULL COMMENT Comment test\n" +
            ")\n");
    }
}