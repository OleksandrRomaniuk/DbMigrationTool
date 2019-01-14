package com.dbbest.databasemanager.loadingmanager.constants.attributes;

public enum SchemaAttributes {
    SCHEMA_CATALOG_NAME("CATALOG_NAME"), SCHEMA_NAME("SCHEMA_NAME"), SCHEMA_DEFAULT_CHARACTER_SET_NAME("DEFAULT_CHARACTER_SET_NAME"),
    SCHEMA_DEFAULT_COLLATION_NAME("DEFAULT_COLLATION_NAME"), SCHEMA_SQL_PATH("SQL_PATH");

    private String element;

    SchemaAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}