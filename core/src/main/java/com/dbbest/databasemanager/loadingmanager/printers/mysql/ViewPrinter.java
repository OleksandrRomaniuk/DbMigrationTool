package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
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

        if (viewAttributes.get(FunctionAttributes.DEFINER) != null
            && !viewAttributes.get(FunctionAttributes.DEFINER).isEmpty()) {
            query.append("DEFINER = " + viewAttributes.get(FunctionAttributes.DEFINER) + "\n");
        }
        if (viewAttributes.get(FunctionAttributes.SECURITY_TYPE) != null
            && !viewAttributes.get(FunctionAttributes.SECURITY_TYPE).isEmpty()) {
            query.append("SQL SECURITY " + viewAttributes.get(FunctionAttributes.SECURITY_TYPE) + "\n");
        }
        query.append("VIEW " + viewContainer.getAttributes().get(IndexAttributes.TABLE_SCHEMA)
            + "." + viewAttributes.get(ViewAttributes.TABLE_NAME));

        if (viewContainer.getChildren() != null && !viewContainer.getChildren().isEmpty()) {
            List<Container> columns = viewContainer.getChildren();
            query.append(" (\n");
            for (Container column : columns) {
                query.append("`" + column.getAttributes().get(TableColumnAttributes.COLUMN_NAME) + "`" + ",\n");
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            query.append(")\n");
        }

        query.append("AS " + viewAttributes.get(ViewAttributes.VIEW_DEFINITION));

        return query.toString();
    }
}
