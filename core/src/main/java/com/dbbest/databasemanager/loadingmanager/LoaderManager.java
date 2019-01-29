package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TypeSupportConstants;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.DirectorySearcher;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.LoaderClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.util.Map;

public class LoaderManager {

    private static LoaderManager instance;

    private Map<String, Loader> loaders;

    private LoaderManager(){}

    public static LoaderManager getInstance() {
        if (instance == null) {
            instance = new LoaderManager();
        }
        return instance;
    }

    public Container loadLazy(Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.lazyLoad(container);
        return container;
    }

    public Container loadDetails(Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.detailedLoad(container);
        return container;
    }

    public Container loadFull(Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.fullLoad(container);
        return container;
    }

    private Loader getLoader(Container container) throws DatabaseException {
        String catalogWithLoader = new DirectorySearcher()
            .findFolderWithLoaders((String) container.getAttributes().get(TypeSupportConstants.DatabaseType.toString()));

        Loader loader = new LoaderClassLoader().getLoader(catalogWithLoader,
            (String) container.getAttributes().get(TypeSupportConstants.LoaderPrinterType.toString()));

        return loader;
    }
}
