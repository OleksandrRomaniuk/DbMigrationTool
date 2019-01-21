package com.dbbest.databasemanager.loadingmanager.constants.tags;

public enum ColumnTagNameConstants {
    ConstraintCategory("ConstraintCategory");

    private String element;

    ColumnTagNameConstants(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
