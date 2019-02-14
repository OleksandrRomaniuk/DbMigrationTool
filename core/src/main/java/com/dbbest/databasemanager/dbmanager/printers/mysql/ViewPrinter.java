package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;
import com.mysql.cj.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * The class-printer of the mysql views.
 */
@PrinterAnnotation(NameConstants.VIEW)
public class ViewPrinter implements Printer {
    @Override
    public String execute(Container viewContainer) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE\n");

        Map<String, String> viewAttributes = viewContainer.getAttributes();

        if (!StringUtils.isNullOrEmpty(viewAttributes.get(FunctionAttributes.DEFINER))) {
            query.append("DEFINER = " + viewAttributes.get(FunctionAttributes.DEFINER) + "\n");
        }
        if (!StringUtils.isNullOrEmpty(viewAttributes.get(FunctionAttributes.SECURITY_TYPE))) {
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
