package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the trigger category.
 */
@PrinterAnnotation(NameConstants.TABLE_TRIGGERS)
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
