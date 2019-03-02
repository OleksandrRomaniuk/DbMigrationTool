package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the table category.
 */
@PrinterAnnotation(NameConstants.TABLES)
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

