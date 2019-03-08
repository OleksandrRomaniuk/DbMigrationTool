package com.dbbest.models;


public class LoginCredentials {
    private String login;
    private String password;
    private String dbName;
    private String dbType;

    public LoginCredentials(){}

    public LoginCredentials(String login, String password, String dbName, String dbType) {
        this.login = login;
        this.password = password;
        this.dbName = dbName;
        this.dbType = dbType;
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

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

}
