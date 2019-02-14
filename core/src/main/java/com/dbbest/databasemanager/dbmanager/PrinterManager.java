package com.dbbest.databasemanager.dbmanager;

import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.databasemanager.reflectionutil.printersreflection.PrinterClassLoader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.util.Map;
import java.util.logging.Level;

/**
 * The class which executes printing of a database element with the respective printer.
 */
public final class PrinterManager {

    private Map<String, Class> printers;

    public PrinterManager(String dbType) throws DatabaseException {
        printers = new PrinterClassLoader(dbType).getPrinters();
    }

    /**
     * @param container the target container to print.
     * @return returns the priunted sql script.
     * @throws DatabaseException throws the exception if a problem encountered at work with the database.
     * @throws ContainerException throws the exception if a problem encountered at work with the container.
     */
    public String print(Container container) throws DatabaseException, ContainerException {
        Class printerClass = printers.get(container.getName());
        try {
            Printer printer = (Printer)printerClass.newInstance();
            return printer.execute(container);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }
}
