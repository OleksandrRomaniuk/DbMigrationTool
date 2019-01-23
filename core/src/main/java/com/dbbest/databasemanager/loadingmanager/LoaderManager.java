package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.databasemanager.loadingmanager.constants.tags.TypeSupportConstants;
import com.dbbest.databasemanager.loadingmanager.loaders.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.classloader.DirectorySearcher;
import com.dbbest.databasemanager.reflectionutil.classloader.LoaderClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public class LoaderManager {

    public Container loadLazy(Connection connection, Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.lazyLoad(connection, container);
        return container;
    }

    public Container loadDetails(Connection connection, Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.detailedLoad(connection, container);
        return container;
    }

    public Container loadFull(Connection connection, Container container) throws DatabaseException, ContainerException {
        Loader loader = getLoader(container);
        loader.fullLoad(connection, container);
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
