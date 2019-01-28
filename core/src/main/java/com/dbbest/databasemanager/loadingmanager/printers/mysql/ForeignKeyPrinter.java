package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

public class ForeignKeyPrinter implements Printer {

    @Override
    public String execute(Container fkContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("FOREIGN KEY " + fkContainer.getName() + " (" + getColumns(fkContainer)
            + ") REFERENCES " + getReferencedTable(fkContainer) + " (" + getReferencedColumns(fkContainer) + ")");
        return query.toString();
    }

    private String getReferencedTable(Container fkContainer) {
        List<Container> constraints = fkContainer.getChildren();
        return (String) constraints.get(0).getAttributes().get(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement()) + "."
            + constraints.get(0).getAttributes().get(FkAttributes.REFERENCED_TABLE_NAME.getElement());
    }

    private String getReferencedColumns(Container fkContainer) {
        List<Container> constraints = fkContainer.getChildren();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < constraints.size(); i++) {

            for (int j = 0; j < constraints.size(); j++) {
                int position = Integer.parseInt((String) constraints.get(j).getAttributes().get(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement()));
                if (position == (i + 1)) {
                    query.append(constraints.get(j).getAttributes().get(FkAttributes.REFERENCED_COLUMN_NAME.getElement()) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }

    private String getColumns(Container fkContainer) {
        List<Container> constraints = fkContainer.getChildren();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < constraints.size(); i++) {
            for (int j = 0; j < constraints.size(); j++) {
                int ordPosition = Integer.parseInt((String) constraints.get(j).getAttributes().get(FkAttributes.ORDINAL_POSITION.getElement()));
                if (ordPosition == (i + 1)) {
                    query.append(constraints.get(j).getAttributes().get(FkAttributes.COLUMN_NAME.getElement()) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }
}
