package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
import com.dbbest.xmlmanager.container.Container;

public class TablePrinter implements Printer {
    @Override
    public String execute(Container tableContainer) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS " + tableContainer.getAttributes().get(TableAttributes.TABLE_SCHEMA.getElement())
            + "." + tableContainer.getAttributes().get(TableAttributes.TABLE_NAME.getElement()));

        query.append(" (\n");

        


        query.append(")\n");

        return null;
    }
}
