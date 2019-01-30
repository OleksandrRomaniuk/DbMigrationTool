package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.DirectorySearcher;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.LoaderClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.util.HashMap;
import java.util.Map;

public class LoaderManager extends AbstractManager {

    private static LoaderManager instance;

    private Map<String, Loader> loaders;

    private LoaderManager() {
    }

    public static LoaderManager getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new LoaderManager();
            instance.initializeLoaders();
        }
        return instance;
    }

    public Container loadLazy(Container container) throws DatabaseException, ContainerException {
        Loader loader = loaders.get(super.getLazyOrDetailedLoaderMatching(container.getName()));
        loader.lazyLoad(container);
        return container;
    }

    public Container loadDetails(Container container) throws DatabaseException, ContainerException {
        Loader loader = loaders.get(container.getName());
        loader.detailedLoad(container);
        return container;
    }

    public Container loadFull(Container container) throws DatabaseException, ContainerException {
        Loader loader = loaders.get(super.getLazyOrDetailedLoaderMatching(container.getName()));
        loader.fullLoad(container);
        return container;
    }

    private void initializeLoaders() throws DatabaseException {
        String connectionType = Context.getInstance().getDbType();
        String loadersCatalog = new DirectorySearcher().findFolderWithLoaders(connectionType);
        loaders = new HashMap();
        for (String loaderType : LoaderPrinterName.getInstance().getListOfLoaders()) {
            loaders.put(loaderType, new LoaderClassLoader().getLoader(loadersCatalog, loaderType));
        }
    }
}
