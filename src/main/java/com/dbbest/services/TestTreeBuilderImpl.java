package com.dbbest.services;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.dbmanager.constants.DatabaseTypes;
import com.dbbest.databasemanager.dbmanager.loaders.mysql.SchemaLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.models.TreeNode;
import com.dbbest.utils.TreeNodeBuilder;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class TestTreeBuilderImpl extends AbstractWrapper implements TestTreeBuilder {



    @Override
    public Container build(String dbType, String dbName, String login, String password) throws DatabaseException, ContainerException {

        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection(dbType, dbName, login, password);
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.setConnection(connection);
        Container root = new Container();
        schemaLoader.lazyLoad(root);
        new TreeNodeBuilder().getTreeNode(root);
        return super.getWrapper(root);
    }
}
