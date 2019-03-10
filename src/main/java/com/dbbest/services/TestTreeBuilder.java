package com.dbbest.services;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.TreeNode;
import com.dbbest.xmlmanager.container.Container;

public interface TestTreeBuilder {
    Container build(String dbType, String sbName, String login, String password) throws CommandException, DatabaseException, ContainerException;
}
