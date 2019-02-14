package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;
import com.mysql.cj.util.StringUtils;

import java.util.Map;

/**
 * The class-printer of the schema.
 */
@PrinterAnnotation(NameConstants.SCHEMA)
public class SchemaPrinter implements Printer {
    @Override
    public String execute(Container schemaContainer) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE SCHEMA IF NOT EXISTS " + schemaContainer.getAttributes().get(SchemaAttributes.SCHEMA_NAME));

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
        if (schemaAttributes != null) {
            if (!StringUtils.isNullOrEmpty(schemaAttributes.get(SchemaAttributes.DEFAULT_CHARACTER_SET_NAME))) {
                query.append(" CHARACTER SET " + "'" + schemaAttributes.get(SchemaAttributes.DEFAULT_CHARACTER_SET_NAME) + "'");
            }
            if (!StringUtils.isNullOrEmpty(schemaAttributes.get(SchemaAttributes.DEFAULT_COLLATION_NAME))) {
                query.append(" COLLATE " + "'" + schemaAttributes.get(SchemaAttributes.DEFAULT_COLLATION_NAME) + "'");
            }
        }
        query.append(";");
        return query.toString();
    }
}
