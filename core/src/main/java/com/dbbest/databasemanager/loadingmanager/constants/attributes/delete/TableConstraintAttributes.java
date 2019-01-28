package com.dbbest.databasemanager.loadingmanager.constants.attributes.delete;

public enum TableConstraintAttributes {
    CONSTRAINT_CATALOG("CONSTRAINT_CATALOG"), CONSTRAINT_SCHEMA("CONSTRAINT_SCHEMA"), TABLE_SCHEMA("TABLE_SCHEMA"),
    TABLE_NAME("TABLE_NAME"), CONSTRAINT_TYPE("CONSTRAINT_TYPE"),CONSTRAINT_NAME("CONSTRAINT_NAME");

    private String element;

    TableConstraintAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
