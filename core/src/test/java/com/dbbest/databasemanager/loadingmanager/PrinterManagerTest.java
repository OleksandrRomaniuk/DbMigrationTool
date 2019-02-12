package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoadersPrinterDatabaseTypes;
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
        container.setName(LoaderPrinterName.SCHEMA);
        container.addAttribute("SCHEMA_NAME", "sakila");
        container.addAttribute("DEFAULT_CHARACTER_SET_NAME", "utf8mb4");
        container.addAttribute("DEFAULT_COLLATION_NAME", "utf8mb4_0900_ai_ci");

        Context context = new Context();

        context.setDbType(LoadersPrinterDatabaseTypes.MYSQL);

        Assert.assertEquals("CREATE SCHEMA IF NOT EXISTS sakila CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';",
            new PrinterManager(context).print(container));
    }
}