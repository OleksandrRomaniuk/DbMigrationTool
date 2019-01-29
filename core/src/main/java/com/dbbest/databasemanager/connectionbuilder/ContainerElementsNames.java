package com.dbbest.databasemanager.connectionbuilder;

/**
 * The enum with tags for the properties file.
 */
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
