package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the table category.
 */
@PrinterAnnotation(LoaderPrinterName.TABLES)
public class TableCategoryPrinter implements Printer {
    @Override
    public String execute(Container tableCategoryContainer) throws ContainerException {

        StringBuilder query = new StringBuilder();

        if (tableCategoryContainer.hasChildren()) {
            for (Container table : (List<Container>) tableCategoryContainer.getChildren()) {
                query.append(new TablePrinter().execute(table));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
