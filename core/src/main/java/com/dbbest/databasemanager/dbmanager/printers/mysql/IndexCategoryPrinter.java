package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the index category.
 */
@PrinterAnnotation(NameConstants.TABLE_INDEXES)
public class IndexCategoryPrinter implements Printer {
    @Override
    public String execute(Container indexCategoryContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (indexCategoryContainer.hasChildren()) {
            for (Container index : (List<Container>) indexCategoryContainer.getChildren()) {
                query.append(new IndexPrinter().execute(index));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
