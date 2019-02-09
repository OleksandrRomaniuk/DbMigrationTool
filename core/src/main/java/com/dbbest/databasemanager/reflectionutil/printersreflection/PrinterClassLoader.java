package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.logging.Level;

/**
 * The class which searches for the respective printer class and returns its instance.
 */
public class PrinterClassLoader {

    private Printer printerInstance;

    /**
     * @param catalog retrieves the catalog where the class should search for the respective printer.
     * @param printerType the type of the printer which should found.
     * @return returns the instance of the respective printer.
     * @throws DatabaseException throws an exception if could not make the instance of the respective printer.
     */
    public Printer getPrinter(String catalog, String printerType) throws DatabaseException {
        File root = new File(catalog);
        checkPrinters(root, printerType);
        return printerInstance;
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
                            printerInstance = (Printer) printerClass.newInstance();
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
    }
}
