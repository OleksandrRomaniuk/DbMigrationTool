package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the function category.
 */
@PrinterAnnotation(NameConstants.FUNCTIONS)
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
