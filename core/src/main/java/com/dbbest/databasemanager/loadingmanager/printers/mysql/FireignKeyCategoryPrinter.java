package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the foreign keys.
 */
@PrinterAnnotation(LoaderPrinterName.TABLE_FOREIGN_KEYS)
public class FireignKeyCategoryPrinter implements Printer {
    @Override
    public String execute(Container foreignKeyContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (foreignKeyContainer.hasChildren()) {
            for (Container foreignKey : (List<Container>) foreignKeyContainer.getChildren()) {
                query.append(new ForeignKeyPrinter().execute(foreignKey));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
