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

/**
 * The class which executes printing of a database element with the respective printer.
 */
public final class PrinterManager {

    private Map<String, Printer> printers;
    private Context context;

    public PrinterManager(Context context) {
        this.context = context;
    }

    public String print(Container container) throws ContainerException {
        Printer printer = printers.get(container.getName());
        return printer.execute(container);
    }

    private void initializePrinters() throws DatabaseException {
        String connectionType = context.getDbType();
        String printersCatalog = new PrintersDirectorySearcher().findFolderWithPrinters(connectionType);
        printers = new HashMap();
        for (String printerType : LoaderPrinterName.getInstance().getListOfPrinters()) {
            printers.put(printerType, new PrinterClassLoader().getPrinter(printersCatalog, printerType));
        }
    }
}
