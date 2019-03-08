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
public class TestTreeBuilderImpl implements TestTreeBuilder {



    @Override
    public Container build() throws DatabaseException, ContainerException {

        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection(DatabaseTypes.MYSQL, "sakila", "root", "root");
        SchemaLoader schemaLoader = new SchemaLoader();
        schemaLoader.setConnection(connection);
        com.dbbest.xmlmanager.container.Container root = new com.dbbest.xmlmanager.container.Container();
        schemaLoader.lazyLoad(root);
        schemaLoader.detailedLoad(root);
        return new TreeNodeBuilder().getTreeNode(root);
    }
}
