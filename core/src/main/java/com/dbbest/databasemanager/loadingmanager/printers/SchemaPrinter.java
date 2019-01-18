package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;

public class SchemaPrinter implements Printer {
    @Override
    public String execute(Container schemaContainer) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE SCHEMA IF NOT EXISTS " + schemaContainer.getName());

        Map<String, String> schemaAttributes = schemaContainer.getAttributes();
        if (schemaAttributes != null
            && !schemaAttributes.isEmpty()
            && schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_CHARACTER_SET_NAME.getElement()) != null
            && !schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_CHARACTER_SET_NAME.getElement()).trim().isEmpty()) {
            query.append(" CHARACTER SET " + "'" + schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_CHARACTER_SET_NAME.getElement()) + "'");
        }
        if (schemaAttributes != null
            && !schemaAttributes.isEmpty()
            && schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_COLLATION_NAME.getElement()) != null
            && !schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_COLLATION_NAME.getElement()).trim().isEmpty()) {
            query.append(" COLLATE " + "'" + schemaAttributes.get(SchemaAttributes.SCHEMA_DEFAULT_COLLATION_NAME.getElement()) + "'");
        }
        query.append(";");
        return query.toString();
    }
}
