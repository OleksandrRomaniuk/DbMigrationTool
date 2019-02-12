package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoadersPrinterDatabaseTypes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.DatabaseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PrinterClassLoaderTest {

    @Test
    public void shouldGetSchaemaPrinterObjectAndReturnClassName() throws DatabaseException {
        PrintersDirectorySearcher printersDirectorySearcher = new PrintersDirectorySearcher();
        Context context = new Context();
        PrinterClassLoader printerClassLoader = new PrinterClassLoader(LoadersPrinterDatabaseTypes.MYSQL);
        Map printers = printerClassLoader.getPrinters();

        Assert.assertEquals(18,
            printers.size());
    }
}