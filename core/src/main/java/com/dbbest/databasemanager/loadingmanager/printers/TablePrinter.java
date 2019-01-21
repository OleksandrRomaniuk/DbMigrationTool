package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

public class TablePrinter implements Printer {
    @Override
    public String execute(Container tableContainer) throws ContainerException {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS " + tableContainer.getAttributes().get(TableAttributes.TABLE_SCHEMA.getElement())
            + "." + tableContainer.getAttributes().get(TableAttributes.TABLE_NAME.getElement()));

        query.append(" (\n");
        TableColumnPrinter tableColumnPrinter = new TableColumnPrinter();
        query.append(tableColumnPrinter.execute(tableContainer
            .getChildByName(TableCategoriesTagNameCategories.Columns.getElement())));
        query.append(")\n");

        query.append(getTableOptions(tableContainer));


        return query.toString();
    }

    private String getTableOptions(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        StringBuilder query = new StringBuilder();
        query.append(getAutoIncrement(tableContainer) + getAngRowLength(tableContainer) + getCheckSum(tableContainer)
            + getCollation(tableContainer) + getComment(tableContainer) + getEngine(tableContainer)
            + getRowFormat(tableContainer));

        return query.toString();
    }

    private String getAutoIncrement(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_AUTO_INCREMENT.getElement()) != null
            ? ("AUTO_INCREMENT = " + tableAttributes.get(TableAttributes.TABLE_AUTO_INCREMENT.getElement()) + "\n") : "";
    }

    private String getAngRowLength(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_AVG_ROW_LENGTH.getElement()) != null
            ? ("AVG_ROW_LENGTH = " + tableAttributes.get(TableAttributes.TABLE_AVG_ROW_LENGTH.getElement()) + "\n") : "";
    }

    private String getCheckSum(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_CHECKSUM.getElement()) != null
            ? ("CHECKSUM = " + tableAttributes.get(TableAttributes.TABLE_CHECKSUM.getElement()) + "\n") : "";
    }

    private String getCollation(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_TABLE_COLLATION.getElement()) != null
            ? ("DEFAULT COLLATE = " + tableAttributes.get(TableAttributes.TABLE_TABLE_COLLATION.getElement()) + "\n") : "";
    }

    private String getComment(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_TABLE_COMMENT.getElement()) != null
            ? ("COMMENT = " + tableAttributes.get(TableAttributes.TABLE_TABLE_COMMENT.getElement()) + "\n") : "";
    }

    private String getEngine(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_ENGINE.getElement()) != null
            ? ("ENGINE = " + tableAttributes.get(TableAttributes.TABLE_ENGINE.getElement()) + "\n") : "";
    }

    private String getRowFormat(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_ROW_FORMAT.getElement()) != null
            ? ("ROW_FORMAT = " + tableAttributes.get(TableAttributes.TABLE_ROW_FORMAT.getElement()) + "\n") : "";
    }

    private String getTableSpace(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_DATA_FREE.getElement()) != null
            ? ("TABLESPACE = " + tableAttributes.get(TableAttributes.TABLE_DATA_FREE.getElement()) + "\n") : "";
    }
}
