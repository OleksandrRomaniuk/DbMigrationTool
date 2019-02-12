package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

@PrinterAnnotation(LoaderPrinterName.TABLE_TRIGGERS)
public class TriggerCategoryPrinter implements Printer {
    @Override
    public String execute(Container triggerCategoryPrinter) throws ContainerException {
        StringBuilder query = new StringBuilder();

        if (triggerCategoryPrinter.hasChildren()) {
            for (Container trigger : (List<Container>) triggerCategoryPrinter.getChildren()) {
                query.append(new TriggerPrinter().execute(trigger));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
