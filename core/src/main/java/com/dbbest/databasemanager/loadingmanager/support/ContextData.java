package com.dbbest.databasemanager.loadingmanager.support;

import com.dbbest.xmlmanager.container.Container;

public class ContextData {

    private static ContextData instance;
    private Container databaseTree = new Container();

    private ContextData() {
    }

    public static ContextData getInstance() {
        if (instance == null) {
            instance = new ContextData();
        }
        return instance;
    }

    public Container getDatabaseTree() {
        return databaseTree;
    }
}
