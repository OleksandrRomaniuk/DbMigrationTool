package com.dbbest.databasemanager.dbmanager;

import com.dbbest.databasemanager.dbmanager.constants.DatabaseTypes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Assert;
import org.junit.Test;

public class PrinterManagerTest {

    @Test
    public void loadFull() throws ParsingException, ContainerException, DatabaseException {
        Container container = new Container();
        container.setName(NameConstants.SCHEMA);
        container.addAttribute("SCHEMA_NAME", "sakila");
        container.addAttribute("DEFAULT_CHARACTER_SET_NAME", "utf8mb4");
        container.addAttribute("DEFAULT_COLLATION_NAME", "utf8mb4_0900_ai_ci");

        Assert.assertEquals("CREATE SCHEMA IF NOT EXISTS sakila CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';",
                new PrinterManager(DatabaseTypes.MYSQL).print(container));
    }
}
