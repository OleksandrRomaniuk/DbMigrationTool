package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

public class IndexPrinter implements Printer {
    @Override
    public String execute(Container indexContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE" + isUnique(indexContainer) + " INDEX " + getName(indexContainer)
            + getType(indexContainer) + "\nON " + getTableName(indexContainer) + " ("
            + getColumnName(indexContainer) + ")");

        return query.toString();
    }

    private String isUnique(Container indexContainer) {
        return indexContainer.getAttributes().get(IndexAttributes.NON_UNIQUE.getElement()).equals(0) ? " UNIQUE" : "";
    }

    private String getName(Container indexContainer) {
        return (String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME.getElement());
    }

    private String getType(Container indexContainer) {
        if (indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE.getElement()).equals("BTREE")
            || indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE.getElement()).equals("HASH")) {
            return (String) " USING " + indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE.getElement());
        } else {
            return "";
        }
    }

    private String getTableName(Container indexContainer) {
        return (String) indexContainer.getAttributes().get(IndexAttributes.TABLE_SCHEMA.getElement()) + "."
            + indexContainer.getAttributes().get(IndexAttributes.TABLE_NAME.getElement());
    }

    private String getColumnName(Container indexContainer) {
        return (String) indexContainer.getAttributes().get(IndexAttributes.COLUMN_NAME.getElement());
    }
}
