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
        indexContainer.addAttribute(IndexAttributes.NON_UNIQUE.getElement(), 0);
        indexContainer.addAttribute(IndexAttributes.INDEX_NAME.getElement(), "indx_location1");
        indexContainer.addAttribute(IndexAttributes.INDEX_TYPE.getElement(), "SPATIAL");
        indexContainer.addAttribute(IndexAttributes.TABLE_SCHEMA.getElement(), "sakila");
        indexContainer.addAttribute(IndexAttributes.TABLE_NAME.getElement(), "address");
        indexContainer.addAttribute(IndexAttributes.COLUMN_NAME.getElement(), "location");

        IndexPrinter indexPrinter = new IndexPrinter();
        System.out.println(indexPrinter.execute(indexContainer));
    }
}