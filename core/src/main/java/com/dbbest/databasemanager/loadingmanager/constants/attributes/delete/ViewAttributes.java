package com.dbbest.databasemanager.loadingmanager.constants.attributes.delete;

public enum ViewAttributes {
    TABLE_CATALOG("TABLE_CATALOG"), TABLE_SCHEMA("TABLE_SCHEMA"), TABLE_NAME("TABLE_NAME"), VIEW_DEFINITION("VIEW_DEFINITION"),
    CHECK_OPTION("CHECK_OPTION"), IS_UPDATABLE("IS_UPDATABLE"), DEFINER("DEFINER"), SECURITY_TYPE("SECURITY_TYPE"), CHARACTER_SET_CLIENT("CHARACTER_SET_CLIENT"),
    COLLATION_CONNECTION("COLLATION_CONNECTION");


    private String element;

    ViewAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
