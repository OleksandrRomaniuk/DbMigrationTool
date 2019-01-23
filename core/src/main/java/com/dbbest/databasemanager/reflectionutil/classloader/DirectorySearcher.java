package com.dbbest.databasemanager.reflectionutil.classloader;

import com.dbbest.databasemanager.loadingmanager.annotations.DatabaseTypesAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.DatabaseTypesEnum;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.logging.Level;

public class DirectorySearcher {

    private String packageWithLoaders;

    public String findPackageWithLoaders(DatabaseTypesEnum databaseTypesEnum) throws DatabaseException {

        try {
            File root = new File(getRoot());
            checkFile(root, databaseTypesEnum);
        } catch (IOException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        if (packageWithLoaders != null && !packageWithLoaders.trim().equals("")) {
            return packageWithLoaders;
        } else {
            throw new DatabaseException(Level.SEVERE, "Can not find the package with loaders " + databaseTypesEnum);
        }
    }

    private void checkFile(File root, DatabaseTypesEnum databaseTypesEnum) throws DatabaseException {
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {

            if (item.isDirectory()) {
                checkFile(item, databaseTypesEnum);
            } else if (item.getName().toLowerCase().equals("package-info.class")) {
                try {
                    Package pkg = new CustomClassLoader().createClass(item).getPackage();
                    Annotation annotation = pkg.getAnnotation(DatabaseTypesAnnotation.class);
                    if (((DatabaseTypesAnnotation) annotation).value().equals(databaseTypesEnum)) {
                        packageWithLoaders = pkg.getName();
                        System.out.println(item.getCanonicalPath());
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
