package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class which searches required node in the tree.
 */
public class TreeNavigator {

    private Context context;
    private Container root;

    public TreeNavigator(Context context) {
        this.context = context;
    }
    public TreeNavigator(Container container) {
        this.root = container;
    }

    /**
     * @param fullPath the full path to the target node.
     * @return returns the target container.
     */
    public Container getTargetContainer(String fullPath) {
        Container rootContainer = null;
        if (context != null) {
            rootContainer = context.getDbTreeContainer();
        } else if (root != null) {
            rootContainer = root;
        }

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
