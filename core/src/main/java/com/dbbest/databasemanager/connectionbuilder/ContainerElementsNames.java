package com.dbbest.databasemanager.connectionbuilder;

public enum ContainerElementsNames {
    DRIVER("driver"), URL("url"), LOGIN("username"), PASSWORD("password");

    private String element;

    ContainerElementsNames(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
