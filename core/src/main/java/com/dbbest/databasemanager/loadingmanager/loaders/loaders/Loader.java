package com.dbbest.databasemanager.loadingmanager.loaders.loaders;

import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public interface Loader {
    void lazyLoad(Connection connection, Container tree) throws DatabaseException, ContainerException;

    void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException ;

    void fullLoad(Connection connection, Container tree) throws DatabaseException, ContainerException;
}