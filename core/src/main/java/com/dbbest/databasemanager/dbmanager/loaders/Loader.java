package com.dbbest.databasemanager.dbmanager.loaders;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

/**
 * The interface for the databases-loaders.
 */
public interface Loader {
    void setConnection(Connection connection);

    void lazyLoad(Container tree) throws DatabaseException, ContainerException;

    void detailedLoad(Container tree) throws DatabaseException, ContainerException ;

    void fullLoad(Container tree) throws DatabaseException, ContainerException;
}
