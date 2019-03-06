package com.dbbest.databasemanager.reflectionutil.loadersreflection;

import com.dbbest.databasemanager.dbmanager.annotations.LoadersPackageAnnotation;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

/**
 * The class which looks for the catalog with respective loaders.
 */
public class DirectorySearcher {

    private String folderWithLoader;

    /**
     * @param databaseType the type of database and respective loaders.
     * @return returns the path to the catalog with respective loaders
     * @throws DatabaseException throws an exception if the catalog has not been found.
     */
    public String findFolderWithLoaders(String databaseType) throws DatabaseException {

        try {
            File root = new File(new File(getRoot()).getParent());
            checkFile(root, databaseType);
        } catch (IOException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        if (folderWithLoader != null && !folderWithLoader.trim().equals("")) {
            return folderWithLoader;
        } else {
            throw new DatabaseException(Level.SEVERE, "Can not find the package with loaders " + databaseType);
        }
    }

    private void checkFile(File root, String databaseTypesEnum) throws DatabaseException {
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {
            if (item.isDirectory()) {
                checkFile(item, databaseTypesEnum);
            } else if (item.getName().toLowerCase().equals("package-info.class")) {
                try {
                    String path = item.getPath();
                    path = path.replace("\\", ".");
                    String regex = ".target.classes.";
                    String[] pathArr = path.split(regex);
                    Annotation annotation = null;
                    if(pathArr.length == 2) {
                        String classPath = pathArr[1];
                        classPath = classPath.replace(".class", "");
                        Class clazz = Class.forName(classPath);
                        Package pkg = clazz.getPackage();
                        annotation = pkg.getAnnotation(LoadersPackageAnnotation.class);
                    }
                    //Package pkg = new CustomClassLoader().getClass(item).getPackage();

                    if (annotation != null
                        && ((LoadersPackageAnnotation) annotation).value().equals(databaseTypesEnum)) {
                        folderWithLoader = item.getCanonicalPath().replace("\\package-info.class", "");
                    }

                } catch (ClassNotFoundException | IOException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
    }

    private String getRoot() throws IOException {
        //URL resource = new DirectorySearcher().getClass().getResource("/");
        return new File(".").getCanonicalPath();
    }
}
