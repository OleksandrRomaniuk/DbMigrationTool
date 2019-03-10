package com.dbbest.services;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

public interface PrintService {
    String print(Container root,String dbType, String fullPath) throws DatabaseException, ContainerException;
}
