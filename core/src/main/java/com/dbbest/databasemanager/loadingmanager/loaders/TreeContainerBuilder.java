package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

/**
 * The class which occupies by building the tree in the container.
 */
public class TreeContainerBuilder {

    private Container<String> treeContainer = new Container();

    public void setSchemaName(String schemaName) throws ContainerException {
        treeContainer.setName(schemaName);
    }
}
