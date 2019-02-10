package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Assert;
import org.junit.Test;

public class TablePrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container table = new Container();
        table.addAttribute(ViewAttributes.TABLE_NAME, "customer1");
        table.addAttribute(IndexAttributes.TABLE_SCHEMA, "sakila");
        table.addAttribute(ViewAttributes.TABLE_NAME, "customer1");
        Container columnCategory = new Container();
        table.addChild(columnCategory);
        columnCategory.setName(LoaderPrinterName.TABLE_COLUMNS);
        Container column1 = new Container();
        column1.setName(LoaderPrinterName.TABLE_COLUMN);
        columnCategory.addChild(column1);
        column1.addAttribute(ViewAttributes.TABLE_NAME, "adressId");
        column1.addAttribute(TableColumnAttributes.EXTRA, null);
        column1.addAttribute(TableColumnAttributes.COLUMN_NAME, "address_id");
        column1.addAttribute(TableColumnAttributes.COLUMN_TYPE, "smallint(5) unsigned");
        column1.addAttribute(TableColumnAttributes.COLUMN_COMMENT, "Comment test");
        column1.addAttribute(TableColumnAttributes.GENERATION_EXPRESSION, "");
        column1.addAttribute(TableColumnAttributes.COLUMN_IS_NULLABLE, "NO");
        column1.addAttribute(TableColumnAttributes.COLUMN_DEFAULT, null);
        column1.addAttribute(TableColumnAttributes.EXTRA, "");
        column1.addAttribute(TableColumnAttributes.COLUMN_KEY, "MUL");
        column1.addAttribute(TableColumnAttributes.COLLATION_NAME, null);
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