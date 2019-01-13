package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public interface Loaders {
    void lazyLoad(Connection connection, Container container) throws ConnectionException, ContainerException;

    void detailedLoad(Connection connection, Container container) throws ConnectionException, ContainerException ;

    void fullLoad(Connection connection, Container container);
}