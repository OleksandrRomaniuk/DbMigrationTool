package com.dbbest.services;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.models.TreeNode;
import com.dbbest.xmlmanager.container.Container;

public interface TreeNodeService {
    Container checkTree(com.dbbest.xmlmanager.container.Container root) throws CommandException, ContainerException;
}