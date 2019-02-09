package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

/**
 * The class-printer of the mysql views.
 */
@PrinterAnnotation(LoaderPrinterName.VIEW)
public class ViewPrinter implements Printer {
    @Override
    public String execute(Container viewContainer) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE\n");

        Map<String, String> viewAttributes = viewContainer.getAttributes();

        if (viewAttributes.get(AttributeSingleConstants.DEFINER) != null
            && !viewAttributes.get(AttributeSingleConstants.DEFINER).isEmpty()) {
            query.append("DEFINER = " + viewAttributes.get(AttributeSingleConstants.DEFINER) + "\n");
        }
        if (viewAttributes.get(AttributeSingleConstants.SECURITY_TYPE) != null
            && !viewAttributes.get(AttributeSingleConstants.SECURITY_TYPE).isEmpty()) {
            query.append("SQL SECURITY " + viewAttributes.get(AttributeSingleConstants.SECURITY_TYPE) + "\n");
        }
        query.append("VIEW " + viewContainer.getAttributes().get(AttributeSingleConstants.TABLE_SCHEMA)
            + "." + viewAttributes.get(AttributeSingleConstants.TABLE_NAME));

        if (viewContainer.getChildren() != null && !viewContainer.getChildren().isEmpty()) {
            List<Container> columns = viewContainer.getChildren();
            query.append(" (\n");
            for (Container column : columns) {
                query.append("`" + column.getAttributes().get(AttributeSingleConstants.COLUMN_NAME) + "`" + ",\n");
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            query.append(")\n");
        }

        query.append("AS " + viewAttributes.get(AttributeSingleConstants.VIEW_DEFINITION));

        return query.toString();
    }
}
