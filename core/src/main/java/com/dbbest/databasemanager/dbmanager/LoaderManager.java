package com.dbbest.databasemanager.dbmanager;

import com.dbbest.databasemanager.dbmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.LoaderClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which executes a respective loader defined by the criteria.
 */
public final class LoaderManager {

    private Map<String, Class> loaders;
    private Connection connection;

    public LoaderManager(Connection connection, String dbType) throws DatabaseException {
        this.connection = connection;
        loaders = new LoaderClassLoader(dbType).getLoaders();
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
            Loader loader = (Loader)loaderClass.getDeclaredConstructor(new Class[] {Connection.class}).newInstance(connection);
            loader.lazyLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
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
            Loader loader = (Loader)loaderClass.getDeclaredConstructor(new Class[] {Connection.class}).newInstance(connection);
            loader.detailedLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
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
            Loader loader = (Loader)loaderClass.getDeclaredConstructor(new Class[] {Connection.class}).newInstance(connection);
            loader.fullLoad(container);
            return container;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }
}
