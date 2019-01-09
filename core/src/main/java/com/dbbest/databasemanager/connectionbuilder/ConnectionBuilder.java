package com.dbbest.databasemanager.connectionbuilder;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;

import java.sql.Connection;

/**
 * A connection builder which gets the connection name and return the respective connection.
 */
public interface ConnectionBuilder {
    Connection getConnection(String connectionName) throws ConnectionException, ContainerException, ParsingException;
}
