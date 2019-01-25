package com.dbbest.databasemanager.loadingmanager.constants.attributes.delete;

public enum FkAttributes {
    CONSTRAINT_CATALOG("CONSTRAINT_CATALOG"), CONSTRAINT_SCHEMA("CONSTRAINT_SCHEMA"), CONSTRAINT_NAME("CONSTRAINT_NAME"), TABLE_CATALOG("TABLE_CATALOG"),
    TABLE_SCHEMA("TABLE_SCHEMA"), TABLE_NAME("TABLE_NAME"), COLUMN_NAME("COLUMN_NAME"), ORDINAL_POSITION("ORDINAL_POSITION"),
    POSITION_IN_UNIQUE_CONSTRAINT("POSITION_IN_UNIQUE_CONSTRAINT"), REFERENCED_TABLE_SCHEMA("REFERENCED_TABLE_SCHEMA"),
    REFERENCED_TABLE_NAME("REFERENCED_TABLE_NAME"), REFERENCED_COLUMN_NAME("REFERENCED_COLUMN_NAME");

    private String element;

    FkAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
