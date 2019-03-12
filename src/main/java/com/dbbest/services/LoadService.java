package com.dbbest.services;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.dbmanager.LoaderManager;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.utils.LoadTypes;
import com.dbbest.utils.TreeNavigator;
import com.dbbest.utils.TreeNodeBuilder;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;

@Service
public class LoadService extends AbstractWrapper implements TreeNodeService {

    @Override
    public Container checkTree(String dbType, String dbName, String userName, String password,
                               String fullPath, String loadType, Container schemas)
            throws ContainerException, DatabaseException {

        Container root = (Container)schemas.getChildren().get(0);
        Container targetContainer = this.removeUnnecessaryDataAndGetTarget(root, loadType, fullPath);

        //Context context = new Context();
        //context.setDbTreeContainer(root);
        /*
        CommandManager commandManager = new CommandManager(context);
        String[] commandLine = new String[7];
        commandLine[0] = "-load";
        commandLine[1] = dbType;
        commandLine[2] = dbName;
        commandLine[3] = userName;
        commandLine[4] = password;
        commandLine[5] = this.getFullPath(fullPath);
        commandLine[6] = loadType;
        commandManager.addCommands(commandLine);
        commandManager.execute();*/
        System.out.println(root.getLabel());
        this.executeLoad(dbType, dbName, userName, password,
                fullPath, loadType, targetContainer);

        new TreeNodeBuilder().getTreeNode(root);
        return super.getWrapper(root);
    }

    private String getFullPath(String fullPath) {
        return new StringBuilder(fullPath).deleteCharAt(0).toString().replace("/", ".");
    }

    private void executeLoad(String dbType, String dbName, String userName, String password,
                             String fullPath, String loadType, Container container) throws DatabaseException, ContainerException {

        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();

        Connection connection = simpleConnectionBuilder.getConnection(dbType, dbName, userName, password);

        Container targetContainer = new TreeNavigator(container).getTargetContainer(fullPath);
        if (targetContainer == null) {
            throw new DatabaseException(Level.SEVERE, "Can not find the node with the path: " + fullPath);
        }

        LoaderManager loaderManager = new LoaderManager(connection, dbType);

        switch (loadType.toLowerCase()) {
            case LoadTypes.LAZY:
                loaderManager.loadLazy(targetContainer);
                break;
            case LoadTypes.DETAIL:
                loaderManager.loadDetails(targetContainer);
                break;
            case LoadTypes.FULL:
                loaderManager.loadFull(targetContainer);
                break;
            default:
                throw new DatabaseException(Level.SEVERE, "The type of loading " + loadType + " has not been recognized.");
        }
    }

    private Container removeUnnecessaryDataAndGetTarget(Container root, String loadType, String fullPath) throws ContainerException, DatabaseException {


        Container targetContainer = new TreeNavigator(root)
                .getTargetContainer(this.getFullPath(fullPath));

        switch (loadType.toLowerCase()) {
            case LoadTypes.LAZY:
                targetContainer.setChildren(new ArrayList<>(0));
                break;
            case LoadTypes.DETAIL:
                break;
            case LoadTypes.FULL:
                targetContainer.setChildren(new ArrayList<>(0));
                break;
            default:
                throw new DatabaseException(Level.SEVERE, "The type of loading " + loadType + " has not been recognized.");
        }
        return targetContainer;
    }
}
