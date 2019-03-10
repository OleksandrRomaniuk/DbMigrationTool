package com.dbbest.models;

import com.dbbest.xmlmanager.container.Container;

public class LoadQueryWrapper {
    private String schemaName;
    private String login;
    private String password;
    private String dbType;
    private String loadType;
    private String fullPath;
    private Container container;

    public LoadQueryWrapper(String dbType, String schemaName, String login, String password, String fullPath, String loadType, Container container) {
        this.schemaName = schemaName;
        this.login = login;
        this.password = password;
        this.dbType = dbType;
        this.loadType = loadType;
        this.container = container;
    }

    public LoadQueryWrapper() {}

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
