package com.dbbest.databasemanager.loadingmanager.enumtypes;

public enum TagNames {
    ShemaNameTag("ShemaName");

    private String element;

    TagNames(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
