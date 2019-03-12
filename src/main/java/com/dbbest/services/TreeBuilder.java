package com.dbbest.services;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

public interface TreeBuilder {
    Container build(String dbType, String sbName, String login, String password) throws DatabaseException, ContainerException;
}
