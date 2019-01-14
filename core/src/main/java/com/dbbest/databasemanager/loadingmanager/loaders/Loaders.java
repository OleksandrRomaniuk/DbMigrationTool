package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public interface Loaders {
    void lazyLoad(Connection connection, Container container) throws DatabaseException, ContainerException;

    void detailedLoad(Connection connection, Container container) throws DatabaseException, ContainerException ;

    void fullLoad(Connection connection, Container container);
}