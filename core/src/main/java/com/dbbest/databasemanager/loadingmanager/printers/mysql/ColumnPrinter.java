package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

public class ColumnPrinter implements Printer {
    @Override
    public String execute(Container columnTree) {

        StringBuilder query = new StringBuilder();
        Map<String, String> columnAttributes = columnTree.getAttributes();
        query.append(columnAttributes.get(AttributeSingleConstants.COLUMN_NAME) + " ");
        query.append(columnAttributes.get(AttributeSingleConstants.COLUMN_TYPE));

        if (columnAttributes.get(AttributeSingleConstants.COLUMN_TYPE) != null
            && !columnAttributes.get(AttributeSingleConstants.COLUMN_TYPE).isEmpty()
            && columnAttributes.get(AttributeSingleConstants.COLUMN_IS_NULLABLE).equals("NO")) {
            query.append(" " + "NOT NULL");
        }

        return query.toString();
    }
}
