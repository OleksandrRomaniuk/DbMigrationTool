package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ViewAttributes;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

public class ViewPrinter implements Printer {
    @Override
    public String execute(Container viewContainer) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE ");

        Map<String, String> viewAttributes = viewContainer.getAttributes();

        if (viewAttributes.get(ViewAttributes.DEFINER.getElement()) != null
            && !viewAttributes.get(ViewAttributes.DEFINER.getElement()).isEmpty()) {
            query.append("DEFINER " + viewAttributes.get(ViewAttributes.DEFINER.getElement()) + " ");
        }
        if (viewAttributes.get(ViewAttributes.SECURITY_TYPE.getElement()) != null
            && !viewAttributes.get(ViewAttributes.SECURITY_TYPE.getElement()).isEmpty()) {
            query.append("SQL SECURITY " + viewAttributes.get(ViewAttributes.SECURITY_TYPE.getElement()) + " ");
        }
        query.append("VIEW " + viewContainer.getAttributes().get(ViewAttributes.TABLE_SCHEMA.getElement()) + "." + viewContainer.getName());

        if (viewContainer.getChildren() != null && !viewContainer.getChildren().isEmpty()) {
            List<Container> columns = viewContainer.getChildren();
            query.append(" (");
            for (Container column : columns) {
                query.append( column.getName() + ", ");
            }
            query.deleteCharAt(query.length() - 1);
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }

        query.append(" AS " + viewAttributes.get(ViewAttributes.VIEW_DEFINITION.getElement()));

        return query.toString();
    }
}
