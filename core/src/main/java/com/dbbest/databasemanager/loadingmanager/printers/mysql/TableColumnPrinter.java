package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

public class TableColumnPrinter implements Printer {
    @Override
    public String execute(Container tableColumnCategoryContainer) {

        List<Container> columns = tableColumnCategoryContainer.getChildren();
        StringBuilder query = new StringBuilder();
        if (columns != null && columns.size() > 0) {
            for (Container column : columns) {
                Map<String, String> columnAttributes = column.getAttributes();
                if (columnAttributes.get(AttributeSingleConstants.EXTRA).equals("VIRTUAL GENERATED")
                    || columnAttributes.get(AttributeSingleConstants.EXTRA).equals("VIRTUAL STORED")) {
                    query.append(getGenerated(column) + ",\n");
                } else {
                    query.append(getNoGenerated(column));
                }
            }
        }
        return query.toString();
    }

    private String getNoGenerated(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        StringBuilder query = new StringBuilder();

        query.append(columnAttributes.get(AttributeSingleConstants.COLUMN_NAME)
            + " " + columnAttributes.get(AttributeSingleConstants.COLUMN_TYPE)
            + isNullable(column) + getDefault(column)
            + isAutoIncrement(column) /*+ getColumnKey(column)*/
            + getComment(column) + getCollation(column) + ",\n");
        return query.toString();
    }

    private String getComment(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        if (columnAttributes.get(AttributeSingleConstants.COLUMN_COMMENT) != null
            && !columnAttributes.get(AttributeSingleConstants.COLUMN_COMMENT).trim().equals("")) {
            return " COMMENT " + columnAttributes.get(AttributeSingleConstants.COLUMN_COMMENT);
        } else  {
            return "";
        }
    }

    private String getGenerated(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        StringBuilder query = new StringBuilder();
        query.append(columnAttributes.get(AttributeSingleConstants.COLUMN_NAME)
            + " " + columnAttributes.get(AttributeSingleConstants.COLUMN_TYPE) + "\n"
            + "GENERATED ALWAYS AS (" + columnAttributes.get(AttributeSingleConstants.GENERATION_EXPRESSION) + ") \n"
            + getVirtualStored(column) + " " + isNullable(column) + "\n"
            + getColumnKey(column) + "\n"
            + columnAttributes.get(AttributeSingleConstants.COLUMN_COMMENT));
        return query.toString();
    }

    private String getVirtualStored(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(AttributeSingleConstants.GENERATION_EXPRESSION)) {
            case "VIRTUAL GENERATED":
                return "GENERATED";
            case "VIRTUAL STORED":
                return "STORED";
            default:
                return "";
        }
    }

    private String isNullable(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(AttributeSingleConstants.COLUMN_IS_NULLABLE).equals("NO") ? " NOT NULL" : " NULL";
    }

    private String getDefault(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(AttributeSingleConstants.COLUMN_DEFAULT) == null ? ""
            : (" DEFAULT " + columnAttributes.get(AttributeSingleConstants.COLUMN_DEFAULT));
    }

    private String isAutoIncrement(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(AttributeSingleConstants.EXTRA).equals("auto_increment") ? " AUTO_INCREMENT" : "";
    }

    private String getColumnKey(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(AttributeSingleConstants.COLUMN_KEY)) {
            case "PRI":
                return "PRIMARY KEY";
            case "UNI":
                return "UNIQUE KEY";
            default:
                return "";
        }
    }

    private String getCollation(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(AttributeSingleConstants.COLLATION_NAME) != null
            ? (" COLLATE " + columnAttributes.get(AttributeSingleConstants.COLLATION_NAME)) : "";
    }
}
