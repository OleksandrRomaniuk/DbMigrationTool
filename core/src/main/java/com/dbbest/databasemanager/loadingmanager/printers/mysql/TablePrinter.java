package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

/**
 * The class-printer of the tables.
 */
@PrinterAnnotation(LoaderPrinterName.TABLE)
public class TablePrinter implements Printer {
    @Override
    public String execute(Container tableContainer) throws ContainerException {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS " + tableContainer.getAttributes().get(AttributeSingleConstants.TABLE_SCHEMA)
            + "." + tableContainer.getAttributes().get(AttributeSingleConstants.TABLE_NAME));
        query.append(" (\n");
        query.append(new TableColumnPrinter().execute(tableContainer
            .getChildByName(LoaderPrinterName.TABLE_COLUMNS)));
        query.append(new IndexPrinterHelper().execute(tableContainer));
        query.append(new PrimaryKeyPrinterHelper().execute(tableContainer));
        query.append(new UniquePrinterHelper().execute(tableContainer));
        query.append(new ForeignKeyPrinterHelper().execute(tableContainer));
        query.deleteCharAt(query.length() - 2);
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
        return tableAttributes.get(AttributeSingleConstants.AUTO_INCREMENT) != null
            ? ("AUTO_INCREMENT = " + tableAttributes.get(AttributeSingleConstants.AUTO_INCREMENT) + "\n") : "";
    }

    private String getAngRowLength(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.AVG_ROW_LENGTH) != null
            ? ("AVG_ROW_LENGTH = " + tableAttributes.get(AttributeSingleConstants.AVG_ROW_LENGTH) + "\n") : "";
    }

    private String getCheckSum(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.CHECKSUM) != null
            ? ("CHECKSUM = " + tableAttributes.get(AttributeSingleConstants.CHECKSUM) + "\n") : "";
    }

    private String getCollation(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.TABLE_COLLATION) != null
            ? ("DEFAULT COLLATE = " + tableAttributes.get(AttributeSingleConstants.TABLE_COLLATION) + "\n") : "";
    }

    private String getComment(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return (tableAttributes.get(AttributeSingleConstants.TABLE_COMMENT) != null
            && !tableAttributes.get(AttributeSingleConstants.TABLE_COMMENT).equals(""))
            ? ("COMMENT = " + tableAttributes.get(AttributeSingleConstants.TABLE_COMMENT) + "\n") : "";
    }

    private String getEngine(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.ENGINE) != null
            ? ("ENGINE = " + tableAttributes.get(AttributeSingleConstants.ENGINE) + "\n") : "";
    }

    private String getRowFormat(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.ROW_FORMAT) != null
            ? ("ROW_FORMAT = " + tableAttributes.get(AttributeSingleConstants.ROW_FORMAT) + "\n") : "";
    }

    private String getTableSpace(Container tableContainer) {
        Map<String, String> tableAttributes = tableContainer.getAttributes();
        return tableAttributes.get(AttributeSingleConstants.DATA_FREE) != null
            ? ("TABLESPACE = " + tableAttributes.get(AttributeSingleConstants.DATA_FREE) + "\n") : "";
    }
}
