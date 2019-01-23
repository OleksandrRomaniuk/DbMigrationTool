package com.dbbest.databasemanager.reflectionutil.classloader;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.loaders.loaders.Loader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

public class LoaderClassLoader {

    private Loader loaderInstance;

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
