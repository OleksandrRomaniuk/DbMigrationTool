package com.dbbest.databasemanager.reflectionutil.classloader;

import com.dbbest.databasemanager.dbmanager.constants.DatabaseTypes;
import com.dbbest.databasemanager.reflectionutil.loadersreflection.DirectorySearcher;
import com.dbbest.exceptions.DatabaseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectorySearcherTest {

    @Test
    public void countFile() throws ClassNotFoundException, IOException, DatabaseException {
        DirectorySearcher directorySearcher = new DirectorySearcher();
        assertEquals(directorySearcher.findFolderWithLoaders(DatabaseTypes.MYSQL.toString()),
            "C:\\Users\\admin\\IdeaProjects\\test2\\core\\target\\classes\\com\\dbbest\\databasemanager\\dbmanager\\loaders\\mysql");
    }

}