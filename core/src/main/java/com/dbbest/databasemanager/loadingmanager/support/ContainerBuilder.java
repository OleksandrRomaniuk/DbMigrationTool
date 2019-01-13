package com.dbbest.databasemanager.loadingmanager.support;

import com.dbbest.xmlmanager.container.Container;

public class ContainerBuilder {
    /**
     * The class which contains all shared data related to loaders.
     */
    public static final class ContextData {

        private static ContextData instance;

        private Container<String> treeContainer = new Container();

        private ContextData() {
        }

        /**
         * @return returns the instance of the class.
         */
        public static ContextData getInstance() {
            if (instance == null) {
                instance = new ContextData();
            }
            return instance;
        }

        public Container<String> getTreeContainer() {
            return treeContainer;
        }
    }
}
