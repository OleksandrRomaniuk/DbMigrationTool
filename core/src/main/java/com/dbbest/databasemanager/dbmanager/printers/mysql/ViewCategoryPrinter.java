package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the view category.
 */
@PrinterAnnotation(NameConstants.VIEWS)
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
