package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.ColumnAttributes;
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
                if (columnAttributes.get(ColumnAttributes.COLUMN_EXTRA.getElement()).equals("VIRTUAL GENERATED")
                    || columnAttributes.get(ColumnAttributes.COLUMN_EXTRA.getElement()).equals("VIRTUAL STORED")) {
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

        query.append(column.getName() + " " + columnAttributes.get(ColumnAttributes.COLUMN_TYPE.getElement())
            + isNullable(column) + getDefault(column)
            + isAutoIncrement(column) /*+ getColumnKey(column)*/
            + getComment(column) + getCollation(column) + ",\n");
        return query.toString();
    }

    private String getComment(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        if (columnAttributes.get(ColumnAttributes.COLUMN_COMMENT.getElement()) != null
            && !columnAttributes.get(ColumnAttributes.COLUMN_COMMENT.getElement()).trim().equals("")) {
            return " COMMENT " + columnAttributes.get(ColumnAttributes.COLUMN_COMMENT.getElement());
        } else  {
            return "";
        }
    }

    private String getGenerated(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        StringBuilder query = new StringBuilder();
        query.append(column.getName() + " " + columnAttributes.get(ColumnAttributes.COLUMN_TYPE.getElement()) + "\n"
            + "GENERATED ALWAYS AS (" + columnAttributes.get(ColumnAttributes.COLUMN_GENERATION_EXPRESSION.getElement()) + ") \n"
            + getVirtualStored(column) + " " + isNullable(column) + "\n"
            + getColumnKey(column) + "\n"
            + columnAttributes.get(ColumnAttributes.COLUMN_COMMENT.getElement()));
        return query.toString();
    }

    private String getVirtualStored(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(ColumnAttributes.COLUMN_GENERATION_EXPRESSION.getElement())) {
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
        return columnAttributes.get(ColumnAttributes.COLUMN_IS_NULLABLE.getElement()).equals("NO") ? " NOT NULL" : " NULL";
    }

    private String getDefault(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(ColumnAttributes.COLUMN_DEFAULT.getElement()) == null ? ""
            : (" DEFAULT " + columnAttributes.get(ColumnAttributes.COLUMN_DEFAULT.getElement()));
    }

    private String isAutoIncrement(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(ColumnAttributes.COLUMN_EXTRA.getElement()).equals("auto_increment") ? " AUTO_INCREMENT" : "";
    }

    private String getColumnKey(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(ColumnAttributes.COLUMN_KEY.getElement())) {
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
        return columnAttributes.get(ColumnAttributes.COLUMN_COLLATION_NAME.getElement()) != null
            ? (" COLLATE " + columnAttributes.get(ColumnAttributes.COLUMN_COLLATION_NAME.getElement())) : "";
    }
}
