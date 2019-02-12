package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.reflectionutil.CustomClassLoader;
import com.dbbest.exceptions.DatabaseException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which searches for the respective printer class and returns its instance.
 */
public class PrinterClassLoader {

    private String connectionType;

    public PrinterClassLoader(String connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @return returns the instance of the respective printer.
     * @throws DatabaseException throws an exception if could not make the instance of the respective printer.
     */
    public Map<String, Class> getPrinters() throws DatabaseException {
        String printersCatalog = new PrintersDirectorySearcher().findFolderWithPrinters(connectionType);
        File root = new File(printersCatalog);
        return checkPrinters(root);
    }

    private Map<String, Class> checkPrinters(File root) throws DatabaseException {
        Map<String, Class> printers = new HashMap();
        File[] listOfFiles = root.listFiles();
        for (File item : listOfFiles) {
            if (!item.isDirectory() && item.getName().toLowerCase().endsWith(".class")) {
                try {
                    Class printerClass = new CustomClassLoader().getClass(item);
                    if (printerClass.isAnnotationPresent(PrinterAnnotation.class)) {
                        PrinterAnnotation annotation = (PrinterAnnotation) printerClass.getAnnotation(PrinterAnnotation.class);
                        printers.put(annotation.value(), printerClass);
                    }
                } catch (ClassNotFoundException e) {
                    throw new DatabaseException(Level.SEVERE, e);
                }
            }
        }
        return printers;
    }
}
