package com.dbbest.services;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.TreeNode;
import com.dbbest.xmlmanager.container.Container;

public interface TreeNodeService {
    Container checkTree(String dbType, String dbName, String userName, String password,
                        String fullPath, String loadType, Container root) throws CommandException, ContainerException, DatabaseException;
}