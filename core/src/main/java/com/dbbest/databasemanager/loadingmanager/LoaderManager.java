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

/**
 * The class which executes a respective loader defined by the criteria.
 */
public final class LoaderManager {

    private static LoaderManager instance;

    private Map<String, Loader> loaders;

    private LoaderManager() {
    }

    /**
     * @return returns the instance of the class.
     * @throws DatabaseException throws an exception if loaders have not been initialized.
     */
    public static LoaderManager getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new LoaderManager();
            instance.initializeLoaders();
        }
        return instance;
    }

    /**
     * @param container retrieves the container to be loaded with a respective lazy loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadLazy(Container container) throws DatabaseException, ContainerException {
        String loaderName = LoaderPrinterName.getInstance().getLazyLoaderName().get(container.getName());
        if (loaderName != null && !loaderName.isEmpty()) {
            Loader loader = loaders.get(loaderName);
            loader.lazyLoad(container);
        }
        return container;
    }

    /**
     * @param container retrieves the container to be loaded with a respective detailed loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadDetails(Container container) throws DatabaseException, ContainerException {
        Loader loader = loaders.get(container.getName());
        if (loader != null) {
            loader.detailedLoad(container);
        }
        return container;
    }

    /**
     * @param container retrieves the container to be loaded with a respective full loader.
     * @return returns the loaded container.
     * @throws DatabaseException  throws an exception if the loading failed.
     * @throws ContainerException throws an exception if a problem encountered with the container.
     */
    public Container loadFull(Container container) throws DatabaseException, ContainerException {
        String loaderName = LoaderPrinterName.getInstance().getLazyLoaderName().get(container.getName());
        if (loaderName != null && !loaderName.isEmpty()) {
            Loader loader = loaders.get(loaderName);
            loader.fullLoad(container);
        }
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
/*
    private String getLazyOrDetailedLoaderMatching(String containerName) throws DatabaseException {
        switch (containerName) {
            case LoaderPrinterName.SCHEMA:
                return LoaderPrinterName.SCHEMA;
            case LoaderPrinterName.TABLES:
                return LoaderPrinterName.TABLE;
            case LoaderPrinterName.VIEWS:
                return LoaderPrinterName.VIEW;
            case LoaderPrinterName.STORED_PROCEDURES:
                return LoaderPrinterName.STORED_PROCEDURE;
            case LoaderPrinterName.FUNCTIONS:
                return LoaderPrinterName.FUNCTION;
            case LoaderPrinterName.TABLE_COLUMNS:
                return LoaderPrinterName.TABLE_COLUMN;
            case LoaderPrinterName.TABLE_INDEXES:
                return LoaderPrinterName.INDEX;
            case LoaderPrinterName.TABLE_FOREIGN_KEYS:
                return LoaderPrinterName.FOREIGN_KEY;
            case LoaderPrinterName.TABLE_TRIGGERS:
                return LoaderPrinterName.TRIGGER;
            case LoaderPrinterName.TABLE_CONSTRAINTS:
                return LoaderPrinterName.CONSTRAINT;
            case LoaderPrinterName.VIEW:
                return LoaderPrinterName.VIEW_COLUMN;
            default:
                throw new DatabaseException(Level.SEVERE,
                    "The manager can not find a respective loader or printer matching for the container " + containerName);
        }
    }*/
}
