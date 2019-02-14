package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;
import com.mysql.cj.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * The class-printer of the table columns.
 */
@PrinterAnnotation(NameConstants.TABLE_COLUMN)
public class TableColumnPrinter implements Printer {
    @Override
    public String execute(Container tableColumnCategoryContainer) {

        List<Container> columns = tableColumnCategoryContainer.getChildren();
        StringBuilder query = new StringBuilder();
        if (columns != null && columns.size() > 0) {
            for (Container column : columns) {
                Map<String, String> columnAttributes = column.getAttributes();
                if (columnAttributes.get(TableColumnAttributes.EXTRA).equals("VIRTUAL GENERATED")
                    || columnAttributes.get(TableColumnAttributes.EXTRA).equals("VIRTUAL STORED")) {
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

        query.append(columnAttributes.get(TableColumnAttributes.COLUMN_NAME)
            + " " + columnAttributes.get(TableColumnAttributes.COLUMN_TYPE)
            + isNullable(column) + getDefault(column)
            + isAutoIncrement(column)
            + getComment(column) + getCollation(column) + ",\n");
        return query.toString();
    }

    private String getComment(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        if (!StringUtils.isNullOrEmpty((String) columnAttributes.get(TableColumnAttributes.COLUMN_COMMENT))) {
            return " COMMENT " + columnAttributes.get(TableColumnAttributes.COLUMN_COMMENT);
        } else  {
            return "";
        }
    }

    private String getGenerated(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        StringBuilder query = new StringBuilder();
        query.append(columnAttributes.get(TableColumnAttributes.COLUMN_NAME)
            + " " + columnAttributes.get(TableColumnAttributes.COLUMN_TYPE) + "\n"
            + "GENERATED ALWAYS AS (" + columnAttributes.get(TableColumnAttributes.GENERATION_EXPRESSION) + ") \n"
            + getVirtualStored(column) + " " + isNullable(column) + "\n"
            + getColumnKey(column) + "\n"
            + columnAttributes.get(TableColumnAttributes.COLUMN_COMMENT));
        return query.toString();
    }

    private String getVirtualStored(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(TableColumnAttributes.GENERATION_EXPRESSION)) {
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
        return columnAttributes.get(TableColumnAttributes.COLUMN_IS_NULLABLE).equals("NO") ? " NOT NULL" : " NULL";
    }

    private String getDefault(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(TableColumnAttributes.COLUMN_DEFAULT) == null ? ""
            : (" DEFAULT " + columnAttributes.get(TableColumnAttributes.COLUMN_DEFAULT));
    }

    private String isAutoIncrement(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        return columnAttributes.get(TableColumnAttributes.EXTRA).equals("auto_increment") ? " AUTO_INCREMENT" : "";
    }

    private String getColumnKey(Container column) {
        Map<String, String> columnAttributes = column.getAttributes();
        switch (columnAttributes.get(TableColumnAttributes.COLUMN_KEY)) {
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
        return columnAttributes.get(TableColumnAttributes.COLLATION_NAME) != null
            ? (" COLLATE " + columnAttributes.get(TableColumnAttributes.COLLATION_NAME)) : "";
    }
}
