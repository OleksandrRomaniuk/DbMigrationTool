package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;

import java.util.List;

public class TreeNavigator {

    private Context context;

    public TreeNavigator(Context context) {
        this.context = context;
    }

    public Container getTargetContainer(String fullPath) {
        Container rootContainer = context.getDbTreeContainer();
        String[] fullPathSplit = fullPath.split("\\.");
        if (fullPathSplit.length == 1) {
            return rootContainer;
        } else {
            List<Container> containersOfLevel = rootContainer.getChildren();
            Container targetContainer = null;
            for (int i = 0; i < fullPathSplit.length; i++) {
                for (Container container : containersOfLevel) {
                    if (checkNode(i, fullPathSplit, container)) {
                        targetContainer = container;
                        containersOfLevel = container.getChildren();
                    }
                }
            }
            return targetContainer;
        }
    }

    private boolean checkNode(int index, String[] fullPathSplit, Container targetContainer) {
        if (targetContainer.getName().equals(fullPathSplit[index])) {
            return true;
        } else {
            return targetContainer.getAttributes().containsValue(fullPathSplit[index]);
        }
    }
}
