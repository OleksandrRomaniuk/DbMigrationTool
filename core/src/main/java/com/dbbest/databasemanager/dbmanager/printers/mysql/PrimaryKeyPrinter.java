package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class which executes printing of some elements of the primary key. Assists the PrimaryKeyPrinter class.
 */
public class PrimaryKeyPrinter implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintPrimaryKeys(tableContainer);
    }

    private String buildPrintPrimaryKeys(Container tableContainer) throws ContainerException {
        List<Container> pkList = tableContainer
            .getChildByName(NameConstants.TABLE_CONSTRAINTS).getChildren();
        StringBuilder query = new StringBuilder();
        if (pkList != null && pkList.size() > 0) {
            for (Container pkContainer : pkList) {
                String constraintType = (String) pkContainer.getAttributes().get(ConstraintAttributes.CONSTRAINT_TYPE);
                if (constraintType.trim().equals("PRIMARY KEY")) {
                    query.append(printPrimaryKey(pkContainer));
                }
            }
        }
        return query.toString();
    }

    private String printPrimaryKey(Container primaryKey) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfPkChildren = primaryKey.getChildren();
        query.append("PRIMARY KEY" + getKeyPart(listOfPkChildren) + "\n");
        return query.toString();
    }

    private String getKeyPart(List<Container> listOfPkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < listOfPkChildren.size(); i++) {
            for (int j = 0; j < listOfPkChildren.size(); j++) {
                Container pkContainer = listOfPkChildren.get(j);
                int seqIndex = Integer.parseInt((String) pkContainer
                    .getAttributes().get(ForeignKeyAttributes.ORDINAL_POSITION));
                if (seqIndex == i + 1) {
                    query.append(pkContainer
                        .getAttributes().get(ForeignKeyAttributes.FK_COLUMN_NAME) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append("),");
        return query.toString();
    }
}
