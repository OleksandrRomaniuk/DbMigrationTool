package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

public class PrinterClassLoader {

    private Loader loaderInstance;

    public Loader getPrinter(String catalog, String printerType) throws DatabaseException {
        File root = new File(catalog);
        checkPrinters(root, printerType);
        return loaderInstance;
    }

    private void checkPrinters(File root, String printerType) throws DatabaseException {
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {
            if (!item.isDirectory() && item.getName().toLowerCase().endsWith(".class")) {
                try {
                    Class printerClass = new CustomClassLoader().createClass(item);
                    if (printerClass.isAnnotationPresent(PrinterAnnotation.class)) {

                        Annotation annotation = printerClass.getAnnotation(PrinterAnnotation.class);
                        if (annotation != null
                            && ((PrinterAnnotation) annotation).value().toString().equals(printerType)) {
                            loaderInstance = (Loader) printerClass.newInstance();
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
    }
}
