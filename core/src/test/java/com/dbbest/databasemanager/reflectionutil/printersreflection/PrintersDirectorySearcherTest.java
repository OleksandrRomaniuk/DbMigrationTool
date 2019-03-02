package com.dbbest.databasemanager.reflectionutil.printersreflection;

import com.dbbest.databasemanager.dbmanager.constants.DatabaseTypes;
import com.dbbest.exceptions.DatabaseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintersDirectorySearcherTest {

    @Test
    public void shouldReturnFolderWithPrinters() throws DatabaseException {
        PrintersDirectorySearcher printersDirectorySearcher = new PrintersDirectorySearcher();
        assertEquals("C:\\Users\\admin\\IdeaProjects\\romaniukProject\\core\\target\\classes\\com\\dbbest\\databasemanager\\dbmanager\\printers\\mysql",
            printersDirectorySearcher.findFolderWithPrinters(DatabaseTypes.MYSQL));
    }
}