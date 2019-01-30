package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

@PrinterAnnotation(LoaderPrinterName.SCHEMA)
public class SchemaPrinter implements Printer {
    @Override
    public String execute(Container schemaContainer) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE SCHEMA IF NOT EXISTS " + schemaContainer.getName());

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
        if (schemaAttributes != null
            && !schemaAttributes.isEmpty()
            && schemaAttributes.get(AttributeSingleConstants.DEFAULT_CHARACTER_SET_NAME) != null
            && !schemaAttributes.get(AttributeSingleConstants.DEFAULT_CHARACTER_SET_NAME).trim().isEmpty()) {
            query.append(" CHARACTER SET " + "'" + schemaAttributes.get(AttributeSingleConstants.DEFAULT_CHARACTER_SET_NAME) + "'");
        }
        if (schemaAttributes != null
            && !schemaAttributes.isEmpty()
            && schemaAttributes.get(AttributeSingleConstants.DEFAULT_COLLATION_NAME) != null
            && !schemaAttributes.get(AttributeSingleConstants.DEFAULT_COLLATION_NAME).trim().isEmpty()) {
            query.append(" COLLATE " + "'" + schemaAttributes.get(AttributeSingleConstants.DEFAULT_COLLATION_NAME) + "'");
        }
        query.append(";");
        return query.toString();
    }
}
