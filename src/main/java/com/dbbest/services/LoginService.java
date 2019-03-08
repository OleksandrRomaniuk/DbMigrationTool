package com.dbbest.services;

public interface LoginService {
    boolean checkLoginAndPassword(String login, String password, String dbName, String dbType);
}
