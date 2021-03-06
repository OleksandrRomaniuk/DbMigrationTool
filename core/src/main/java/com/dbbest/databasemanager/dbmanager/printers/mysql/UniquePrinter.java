package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class which executes printing of a part of the mysql unique keys.
 */
public class UniquePrinter implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintUnique(tableContainer);
    }

    private String buildPrintUnique(Container tableContainer) throws ContainerException {
        List<Container> uniqueList = tableContainer
            .getChildByName(NameConstants.TABLE_CONSTRAINTS).getChildren();
        StringBuilder query = new StringBuilder();
        if (uniqueList != null && uniqueList.size() > 0) {
            for (Container uniqueContainer : uniqueList) {
                String constraintType = (String) uniqueContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_TYPE);
                if (constraintType.trim().equals("UNIQUE")) {
                    query.append(printUnique(uniqueContainer));
                }
            }
        }
        return query.toString();
    }

    private String printUnique(Container uniqueContainer) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfUniqueChildren = uniqueContainer.getChildren();
        query.append("UNIQUE" + getName(uniqueContainer) + getKeyPart(listOfUniqueChildren) + "\n");
        return query.toString();
    }

    private String getName(Container uniqueContainer) {
        String constraintName = (String) uniqueContainer.getAttributes()
            .get(ConstraintAttributes.CONSTRAINT_NAME);
        if (constraintName != null && !constraintName.trim().equals("")
            && !constraintName.trim().equals("null")) {
            return " " + constraintName;
        } else {
            return "";
        }
    }

    private String getKeyPart(List<Container> listOfUniqueChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < listOfUniqueChildren.size(); i++) {
            for (int j = 0; j < listOfUniqueChildren.size(); j++) {
                Container uniqueContainer = listOfUniqueChildren.get(j);
                int seqIndex = Integer.parseInt((String) uniqueContainer
                    .getAttributes().get(ForeignKeyAttributes.ORDINAL_POSITION));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(TableColumnAttributes.COLUMN_NAME) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append("),");
        return query.toString();
    }
}
