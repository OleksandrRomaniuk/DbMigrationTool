package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class helper for the ForeignKeyPrinter class. Executes some of foreign key printing elements.
 */
public class ForeignKeyPrinterHelper implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintUnique(tableContainer);
    }

    private String buildPrintUnique(Container tableContainer) throws ContainerException {
        List<Container> fkList = tableContainer
            .getChildByName(LoaderPrinterName.TABLE_CONSTRAINTS).getChildren();
        StringBuilder query = new StringBuilder();
        if (fkList != null && fkList.size() > 0) {
            for (Container fkContainer : fkList) {
                String constraintType = (String) fkContainer.getAttributes().get(AttributeSingleConstants.CONSTRAINT_TYPE);
                if (constraintType.trim().equals("FOREIGN KEY")) {
                    query.append(printForeignKey(fkContainer));
                }
            }
        }
        return query.toString();
    }

    private String printForeignKey(Container fkContainer) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfFkChildren = fkContainer.getChildren();
        query.append("FOREIGN KEY" + getName(fkContainer) + getColumns(listOfFkChildren) + getReference(listOfFkChildren) + "\n");
        return query.toString();
    }

    private String getName(Container fkContainer) {
        String constraintName = (String) fkContainer.getAttributes()
            .get(AttributeSingleConstants.CONSTRAINT_NAME);
        if (constraintName != null && !constraintName.trim().equals("")
            && !constraintName.trim().equals("null")) {
            return " " + constraintName;
        } else {
            return "";
        }
    }

    private String getReference(List<Container> listOfFkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" REFERENCES " + listOfFkChildren.get(0).getAttributes().get(AttributeSingleConstants.REFERENCED_TABLE_SCHEMA)
            + "." + listOfFkChildren.get(0).getAttributes().get(AttributeSingleConstants.REFERENCED_TABLE_NAME) + " (");
        for (int i = 0; i < listOfFkChildren.size(); i++) {
            for (int j = 0; j < listOfFkChildren.size(); j++) {
                Container uniqueContainer = listOfFkChildren.get(j);
                int seqIndex = Integer.parseInt((String) uniqueContainer
                    .getAttributes().get(AttributeSingleConstants.POSITION_IN_UNIQUE_CONSTRAINT));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(AttributeSingleConstants.REFERENCED_COLUMN_NAME) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append("),");
        return query.toString();
    }

    private String getColumns(List<Container> listOfFkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < listOfFkChildren.size(); i++) {
            for (int j = 0; j < listOfFkChildren.size(); j++) {
                Container uniqueContainer = listOfFkChildren.get(j);
                int seqIndex = Integer.parseInt((String) uniqueContainer
                    .getAttributes().get(AttributeSingleConstants.ORDINAL_POSITION));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(AttributeSingleConstants.COLUMN_NAME) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }
}
