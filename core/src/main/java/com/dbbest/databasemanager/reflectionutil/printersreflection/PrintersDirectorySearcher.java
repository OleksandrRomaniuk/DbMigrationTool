package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.annotations.PrintersPackageAnnotation;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

public class PrintersDirectorySearcher {
    private String folderWithLoader;

    public String findFolderWithPrinters(String loadersPrinterDatabaseTypes) throws DatabaseException {

        try {
            File root = new File(getRoot());
            checkFile(root, loadersPrinterDatabaseTypes);
        } catch (IOException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        if (folderWithLoader != null && !folderWithLoader.trim().equals("")) {
            return folderWithLoader;
        } else {
            throw new DatabaseException(Level.SEVERE, "Can not find the package with printers " + loadersPrinterDatabaseTypes);
        }
    }

    private void checkFile(File root, String loadersPrinterDatabaseTypesEnum) throws DatabaseException {
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {

            if (item.isDirectory()) {
                checkFile(item, loadersPrinterDatabaseTypesEnum);
            } else if (item.getName().toLowerCase().equals("package-info.class")) {
                try {
                    Package pkg = new CustomClassLoader().createClass(item).getPackage();
                    Annotation annotation = pkg.getAnnotation(PrintersPackageAnnotation.class);
                    if (annotation != null
                        && ((PrintersPackageAnnotation) annotation).value().toString().equals(loadersPrinterDatabaseTypesEnum)) {
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
