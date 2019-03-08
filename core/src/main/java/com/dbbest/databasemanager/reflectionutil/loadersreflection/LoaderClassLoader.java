package com.dbbest.databasemanager.reflectionutil.loadersreflection;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.annotations.LoadersPackageAnnotation;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which looks for the required loader in the defined catalog and returns its instance.
 */
public class LoaderClassLoader {

    private String connectionType;

    public LoaderClassLoader(String connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @return returns the instance of the target loader.
     * @throws DatabaseException throw an exception if the instance was not retrived.
     */
    public Map<String, Class> getLoaders() throws DatabaseException {

        String loadersCatalog = new DirectorySearcher().findFolderWithLoaders(connectionType);
        File root = new File(loadersCatalog);
        return this.checkLoaders(root);
    }

    private Map<String, Class> checkLoaders(File root) throws DatabaseException {
        Map<String, Class> loaders = new HashMap();
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {
            if (!item.isDirectory() && item.getName().toLowerCase().endsWith(".class")) {
                try {
                    String path = item.getPath();
                    path = path.replace("\\", ".");
                    String regex = ".target.classes.";
                    String[] pathArr = path.split(regex);
                    Class loaderClass = null;
                    if(pathArr.length == 2) {
                        String classPath = pathArr[1];
                        classPath = classPath.replace(".class", "");
                        loaderClass = Class.forName(classPath);
                    }

                    //Class loaderClass = new CustomClassLoader().getClass(item);
                    if (loaderClass.isAnnotationPresent(LoaderAnnotation.class)) {
                        LoaderAnnotation annotation = (LoaderAnnotation)loaderClass.getAnnotation(LoaderAnnotation.class);
                        loaders.put(annotation.value(), loaderClass);
                    }
                } catch (ClassNotFoundException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
        return loaders;
    }
}
