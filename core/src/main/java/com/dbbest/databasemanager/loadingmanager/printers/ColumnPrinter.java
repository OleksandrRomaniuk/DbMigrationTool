package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ColumnAttributes;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

public class ColumnPrinter implements Printer {
    @Override
    public String execute(Container columnTree) {

        StringBuilder query = new StringBuilder();
        Map<String, String> columnAttributes = columnTree.getAttributes();
        query.append(columnTree.getName() + " ");
        query.append(columnAttributes.get(ColumnAttributes.COLUMN_TYPE.getElement()));

        if (columnAttributes.get(ColumnAttributes.COLUMN_TYPE.getElement()) != null
            && !columnAttributes.get(ColumnAttributes.COLUMN_TYPE.getElement()).isEmpty()
            && columnAttributes.get(ColumnAttributes.COLUMN_IS_NULLABLE.getElement()).equals("NO")) {
            query.append(" " + "NOT NULL");
        }

        return query.toString();
    }
}
