package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoadersPrinterDatabaseTypes;
import com.dbbest.exceptions.DatabaseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintersDirectorySearcherTest {

    @Test
    public void shouldReturnFolderWithPrinters() throws DatabaseException {
        PrintersDirectorySearcher printersDirectorySearcher = new PrintersDirectorySearcher();
        assertEquals("C:\\Users\\Oleksandr Romaniuk\\eclipse-workspace\\DBbest\\core\\target\\classes\\com\\dbbest\\databasemanager\\loadingmanager\\printers\\mysql",
            printersDirectorySearcher.findFolderWithPrinters(LoadersPrinterDatabaseTypes.MYSQL));
    }
}