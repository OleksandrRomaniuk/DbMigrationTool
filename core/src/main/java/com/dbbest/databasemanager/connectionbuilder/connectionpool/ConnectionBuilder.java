package com.dbbest.databasemanager.connectionbuilder.connectionpool;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;

import java.sql.Connection;

/**
 * A connection builder which gets the connection name and return the respective connection.
 */
public interface ConnectionBuilder {
    Connection getConnection(String connectionName) throws DatabaseException, ContainerException, ParsingException;
}
