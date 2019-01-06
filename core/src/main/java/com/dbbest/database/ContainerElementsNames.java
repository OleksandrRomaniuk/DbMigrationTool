package com.dbbest.database;

public enum ContainerElementsNames {
    DRIVER("driver"), URL("url"), LOGIN("login"), PASSWORD("password");

    private String element;

    ContainerElementsNames(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
