package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.databasemanager.reflectionutil.printersreflection.PrinterClassLoader;
import com.dbbest.databasemanager.reflectionutil.printersreflection.PrintersDirectorySearcher;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which executes printing of a database element with the respective printer.
 */
public final class PrinterManager {

    private Map<String, Class> printers;

    public PrinterManager(Context context) throws DatabaseException {
        printers = new PrinterClassLoader(context.getDbType()).getPrinters();
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
