package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the view category.
 */
@PrinterAnnotation(LoaderPrinterName.VIEWS)
public class ViewCategoryPrinter implements Printer {
    @Override
    public String execute(Container viewCategoryContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (viewCategoryContainer.hasChildren()) {
            for (Container view : (List<Container>) viewCategoryContainer.getChildren()) {
                query.append(new ViewPrinter().execute(view));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
