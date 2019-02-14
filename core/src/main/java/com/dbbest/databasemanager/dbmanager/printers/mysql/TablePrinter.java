package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

/**
 * The class-printer of the tables.
 */
@PrinterAnnotation(NameConstants.TABLE)
public class TablePrinter implements Printer {
    @Override
    public String execute(Container tableContainer) throws ContainerException {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS " + tableContainer.getAttributes().get(IndexAttributes.TABLE_SCHEMA)
            + "." + tableContainer.getAttributes().get(TableAttributes.TABLE_NAME));
        query.append(" (\n");
        query.append(new TableColumnPrinter().execute(tableContainer
            .getChildByName(NameConstants.TABLE_COLUMNS)));
        query.append(new PrimaryKeyPrinter().execute(tableContainer));
        query.append(new UniquePrinter().execute(tableContainer));
        query.deleteCharAt(query.length() - 2);
        query.append(")\n");
        query.append(getTableOptions(tableContainer));


        return query.toString();
    }

    private String getTableOptions(Container tableContainer) {
        StringBuilder query = new StringBuilder();
        query.append(getAutoIncrement(tableContainer) + getAngRowLength(tableContainer) + getCheckSum(tableContainer)
            + getCollation(tableContainer) + getComment(tableContainer) + getEngine(tableContainer)
            + getRowFormat(tableContainer));

        return query.toString();
    }

    private String getAutoIncrement(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.AUTO_INCREMENT) != null
            ? ("AUTO_INCREMENT = " + tableAttributes.get(TableAttributes.AUTO_INCREMENT) + "\n") : "";
    }

    private String getAngRowLength(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.AVG_ROW_LENGTH) != null
            ? ("AVG_ROW_LENGTH = " + tableAttributes.get(TableAttributes.AVG_ROW_LENGTH) + "\n") : "";
    }

    private String getCheckSum(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.CHECKSUM) != null
            ? ("CHECKSUM = " + tableAttributes.get(TableAttributes.CHECKSUM) + "\n") : "";
    }

    private String getCollation(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.TABLE_COLLATION) != null
            ? ("DEFAULT COLLATE = " + tableAttributes.get(TableAttributes.TABLE_COLLATION) + "\n") : "";
    }

    private String getComment(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return (tableAttributes.get(TableAttributes.TABLE_COMMENT) != null
            && !tableAttributes.get(TableAttributes.TABLE_COMMENT).equals(""))
            ? ("COMMENT = " + tableAttributes.get(TableAttributes.TABLE_COMMENT) + "\n") : "";
    }

    private String getEngine(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.ENGINE) != null
            ? ("ENGINE = " + tableAttributes.get(TableAttributes.ENGINE) + "\n") : "";
    }

    private String getRowFormat(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.ROW_FORMAT) != null
            ? ("ROW_FORMAT = " + tableAttributes.get(TableAttributes.ROW_FORMAT) + "\n") : "";
    }

    private String getTableSpace(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(TableAttributes.DATA_FREE) != null
            ? ("TABLESPACE = " + tableAttributes.get(TableAttributes.DATA_FREE) + "\n") : "";
    }
}
