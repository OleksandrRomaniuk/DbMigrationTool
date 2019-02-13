package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the function category.
 */
@PrinterAnnotation(LoaderPrinterName.FUNCTIONS)
public class FunctionCategoryPrinter implements Printer {
    @Override
    public String execute(Container functionCategoryContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (functionCategoryContainer.hasChildren()) {
            for (Container function : (List<Container>) functionCategoryContainer.getChildren()) {
                query.append(new FunctionPrinter().execute(function));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
