package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;

import java.sql.Connection;

public interface Loaders {
    void lazyLoad(Connection connection) throws ConnectionException, ContainerException;

    void detailedLoad(Connection connection) throws ConnectionException, ContainerException ;

    void fullLoad(Connection connection);
}