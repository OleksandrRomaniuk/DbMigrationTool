package com.dbbest.databasemanager.loadingmanager.constants;

public enum TagNamesConstants {
    ShemaNameTag("Schema"), Tables("Tables"), Views("Views"), Stored_Procedures("Stored Procedures"),
    Functions("Functions");

    private String element;

    TagNamesConstants(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
