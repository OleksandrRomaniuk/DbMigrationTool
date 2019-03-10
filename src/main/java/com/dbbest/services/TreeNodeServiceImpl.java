package com.dbbest.services;

import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.Context;
import com.dbbest.consolexmlmanager.LoadTypes;
import com.dbbest.consolexmlmanager.TreeNavigator;
import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.utils.TreeNodeBuilder;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeNodeServiceImpl extends AbstractWrapper implements TreeNodeService {

    @Override
    public Container checkTree(String dbType, String dbName, String userName, String password,
                               String fullPath, String loadType, Container schemas)
            throws CommandException, ContainerException, DatabaseException {
        Container root = (Container)schemas.getChildren().get(0);
        this.removeUnnecessaryData(root, loadType, fullPath);
        Context context = new Context();
        context.setDbTreeContainer(root);
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
        commandManager.execute();
        new TreeNodeBuilder().getTreeNode(root);
        return super.getWrapper(root);
    }

    private String getFullPath(String fullPath) {
        return new StringBuilder(fullPath).deleteCharAt(0).toString().replace("/", ".");
    }

    private void updateContainerName(Container container) throws ContainerException {
        container.setName((String)container.getAttributes().get(CustomAttributes.CATEGORY));
        if (container.hasChildren()) {
            for (Container childContainer: (List<Container>)container.getChildren()) {
                this.updateContainerName(childContainer);
            }
        }
    }

    private void removeUnnecessaryData(Container root, String loadType, String fullPath) throws ContainerException {

        Container targetContainer = new TreeNavigator(root)
                .getTargetContainer(this.getFullPath(fullPath));

        if(loadType.equals(LoadTypes.LAZY)) {
            targetContainer.setChildren(new ArrayList<>(0));
        } else if (loadType.equals(LoadTypes.DETAIL)) {
        } else if (loadType.equals(LoadTypes.FULL)) {
            targetContainer.setChildren(new ArrayList<>(0));
        }
    }
}
