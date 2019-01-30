package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoadersPrinterDatabaseTypes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.DatabaseException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrinterClassLoaderTest {

    @Test
    public void shouldGetSchaemaPrinterObjectAndReturnClassName() throws DatabaseException {
        PrintersDirectorySearcher printersDirectorySearcher = new PrintersDirectorySearcher();

        PrinterClassLoader printerClassLoader = new PrinterClassLoader();
        Printer printer = printerClassLoader.getPrinter(printersDirectorySearcher.findFolderWithPrinters(LoadersPrinterDatabaseTypes.MYSQL),
            LoaderPrinterName.SCHEMA);

        Assert.assertEquals("com.dbbest.databasemanager.loadingmanager.printers.mysql.SchemaPrinter",
            printer.getClass().getName());
    }
}