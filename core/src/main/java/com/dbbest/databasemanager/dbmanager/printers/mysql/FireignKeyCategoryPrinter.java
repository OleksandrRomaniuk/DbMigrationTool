package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the foreign keys.
 */
@PrinterAnnotation(NameConstants.TABLE_FOREIGN_KEYS)
public class FireignKeyCategoryPrinter implements Printer {
    @Override
    public String execute(Container foreignKeyContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        if (foreignKeyContainer.hasChildren()) {
            for (Container foreignKey : (List<Container>) foreignKeyContainer.getChildren()) {
                query.append(new ForeignKeyPrinter().execute(foreignKey));
                query.append(";\n");
            }
        }
        return query.toString();
    }
}
