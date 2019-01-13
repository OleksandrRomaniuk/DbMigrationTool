package com.dbbest.databasemanager.loadingmanager.support;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

public class DbTreeBuilder {

    private Container dbTreeContainer = ContextData.getInstance().getDatabaseTree();

    public void setSchemaName(String schemaName) throws ContainerException {
        dbTreeContainer.setName(schemaName);
    }

    public void setSchemaAttributes(String key, String value) {

    }
}
