package com.dbbest.databasemanager.reflectionutil.loadersreflection;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

/**
 * The class which looks for the required loader in the defined catalog and returns its instance.
 */
public class LoaderClassLoader {

    private Loader loaderInstance;

    /**
     * @param catalog the catalog in which the class loader should search for the respective loader.
     * @param loaderType the type of the loader that should be found.
     * @return returns the instance of the target loader.
     * @throws DatabaseException throw an exception if the instance was not retrived.
     */
    public Loader getLoader(String catalog, String loaderType) throws DatabaseException {
        File root = new File(catalog);
        checkLoaders(root, loaderType);
        return loaderInstance;
    }

    private void checkLoaders(File root, String loaderType) throws DatabaseException {
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {
            if (!item.isDirectory() && item.getName().toLowerCase().endsWith(".class")) {
                try {
                    Class loaderClass = new CustomClassLoader().createClass(item);
                    if (loaderClass.isAnnotationPresent(LoaderAnnotation.class)) {

                        Annotation annotation = loaderClass.getAnnotation(LoaderAnnotation.class);
                        if (((LoaderAnnotation) annotation).value().toString().equals(loaderType)) {
                            loaderInstance = (Loader) loaderClass.newInstance();
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
    }
}
