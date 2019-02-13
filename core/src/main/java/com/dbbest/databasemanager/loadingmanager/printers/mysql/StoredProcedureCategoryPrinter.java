package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the stored procedure category.
 */
@PrinterAnnotation(LoaderPrinterName.STORED_PROCEDURES)
public class StoredProcedureCategoryPrinter implements Printer {
    @Override
    public String execute(Container storedProcedureCategory) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (storedProcedureCategory.hasChildren()) {
            for (Container storedProcedure : (List<Container>) storedProcedureCategory.getChildren()) {
                query.append(new StoredProcedurePrinter().execute(storedProcedure));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
