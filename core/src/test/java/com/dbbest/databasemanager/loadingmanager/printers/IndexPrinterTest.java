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
        Container index = new Container();
        indexContainer.addChild(index);
        index.setName("indx_location1");
        index.addAttribute(IndexAttributes.NON_UNIQUE.getElement(), 1);
        index.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "indx_location1");
        index.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "BTREE");
        index.addAttribute(IndexAttributes.TABLE_SCHEMA.getElement(), "sakila");
        index.addAttribute(IndexAttributes.TABLE_NAME.getElement(), "address");
        index.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "location");
        index.addAttribute(IndexAttributes.SEQ_IN_INDEX.getElement(), "1");

        IndexPrinter indexPrinter = new IndexPrinter();
        assertEquals(indexPrinter.execute(indexContainer), "CREATE INDEX indx_location1 USING BTREE\n" +
            "ON sakila.address (location)");

    }
}