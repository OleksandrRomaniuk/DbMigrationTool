package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.DirectorySearcher;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.LoaderClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which executes a respective loader defined by the criteria.
 */
public final class LoaderManager {

    private Map<String, Class> loaders;

    public LoaderManager(Context context) throws DatabaseException {
        loaders = new LoaderClassLoader(context.getDbType()).getLoaders();
    }

    /**
     * @param container retrieves the container to be loaded with a respective lazy loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadLazy(Container container) throws DatabaseException, ContainerException {
        Class loaderClass = loaders.get(container.getName());
        try {
            Loader loader = (Loader)loaderClass.newInstance();
            loader.lazyLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    /**
     * @param container retrieves the container to be loaded with a respective detailed loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadDetails(Container container) throws DatabaseException, ContainerException {
        Class loaderClass = loaders.get(container.getName());
        try {
            Loader loader = (Loader)loaderClass.newInstance();
            loader.detailedLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    /**
     * @param container retrieves the container to be loaded with a respective full loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadFull(Container container) throws DatabaseException, ContainerException {
        Class loaderClass = loaders.get(container.getName());
        try {
            Loader loader = (Loader)loaderClass.newInstance();
            loader.fullLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }
}
