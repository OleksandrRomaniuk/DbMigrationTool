package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container indexContainer = new Container();
        indexContainer.addAttribute(IndexAttributes.INDEX_NAME, "indx_location1");
        Container index = new Container();
        indexContainer.addChild(index);
        index.addAttribute(IndexAttributes.INDEX_NAME, "indx_location1");
        index.addAttribute(IndexAttributes.NON_UNIQUE, 1);
        index.addAttribute(IndexAttributes.INDEX_NAME, "indx_location1");
        index.addAttribute(IndexAttributes.INDEX_TYPE, "BTREE");
        index.addAttribute(IndexAttributes.TABLE_SCHEMA, "sakila");
        index.addAttribute(ViewAttributes.TABLE_NAME, "address");
        index.addAttribute(TableColumnAttributes.COLUMN_NAME, "location");
        index.addAttribute(IndexAttributes.SEQ_IN_INDEX, "1");

        IndexPrinter indexPrinter = new IndexPrinter();
        assertEquals(indexPrinter.execute(indexContainer), "CREATE INDEX indx_location1 USING BTREE\n" +
            "ON sakila.address (location)");

    }
}