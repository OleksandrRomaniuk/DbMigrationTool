package com.dbbest.databasemanager.loadingmanager.constants.tags.delete;

public enum SchemaCategoriesTagNameConstants {
    Tables("Tables"), Views("Views"), Stored_Procedures("Stored Procedures"),
    Functions("Functions");

    private String element;

    SchemaCategoriesTagNameConstants(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
