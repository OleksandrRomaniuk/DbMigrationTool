package com.dbbest.databasemanager.loadingmanager.constants.attributes;

public enum KeyColumnUsageAttributes {
    CONSTRAINT_CATALOG("CONSTRAINT_CATALOG"), CONSTRAINT_SCHEMA("CONSTRAINT_SCHEMA"), TABLE_SCHEMA("TABLE_SCHEMA"),
    TABLE_NAME("TABLE_NAME"), CONSTRAINT_TYPE("CONSTRAINT_TYPE");

    private String element;

    KeyColumnUsageAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
