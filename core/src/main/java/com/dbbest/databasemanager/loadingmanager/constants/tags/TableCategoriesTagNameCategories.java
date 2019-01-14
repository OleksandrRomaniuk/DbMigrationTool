package com.dbbest.databasemanager.loadingmanager.constants.tags;

public enum  TableCategoriesTagNameCategories {
    Columns("Columns"), Indexes("Indexes"), Foreign_Keys("Foreign Keys"),
    Triggers("Triggers");

    private String element;

    TableCategoriesTagNameCategories(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
