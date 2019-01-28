package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public interface Loader {
    void lazyLoad(Container tree) throws DatabaseException, ContainerException;

    void detailedLoad(Container tree) throws DatabaseException, ContainerException ;

    void fullLoad(Container tree) throws DatabaseException, ContainerException;
}