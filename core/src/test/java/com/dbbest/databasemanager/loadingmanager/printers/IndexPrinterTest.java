package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container indexContainer = new Container();
        indexContainer.setName("indx_location1");
        indexContainer.addAttribute(IndexAttributes.NON_UNIQUE.getElement(), 1);
        indexContainer.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "indx_location1");
        indexContainer.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");
        indexContainer.addAttribute(IndexAttributes.TABLE_SCHEMA.getElement(), "sakila");
        indexContainer.addAttribute(IndexAttributes.TABLE_NAME.getElement(), "address");
        indexContainer.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "location");

        IndexPrinter indexPrinter = new IndexPrinter();
        assertEquals(indexPrinter.execute(indexContainer), "CREATE INDEX indx_location1 USING BTREE\n" +
            "ON sakila.address (location)");

    }
}