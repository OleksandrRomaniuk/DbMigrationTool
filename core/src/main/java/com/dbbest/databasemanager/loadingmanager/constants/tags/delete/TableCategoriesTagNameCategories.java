package com.dbbest.databasemanager.loadingmanager.constants.tags.delete;

public enum  TableCategoriesTagNameCategories {
    Columns("Columns"), Indexes("Indexes"), Foreign_Keys("Foreign Keys"),
    Triggers("Triggers"), ConstraintCategory("Constraints");

    private String element;

    TableCategoriesTagNameCategories(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
