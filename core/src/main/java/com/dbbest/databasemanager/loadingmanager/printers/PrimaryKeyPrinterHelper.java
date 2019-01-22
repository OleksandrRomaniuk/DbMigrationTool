package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKeyPrinterHelper implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintPrimaryKeys(tableContainer);
    }

    private String buildPrintPrimaryKeys(Container tableContainer) throws ContainerException {
        List<Container> pkList = tableContainer
            .getChildByName(TableCategoriesTagNameCategories.ConstraintCategory.getElement()).getChildren();
        StringBuilder query = new StringBuilder();
        for (Container pkContainer : pkList) {
            String constraintType = (String) pkContainer.getAttributes().get(TableConstraintAttributes.CONSTRAINT_TYPE.getElement());
            if (constraintType.trim().equals("PRIMARY KEY")) {
                query.append(printPrimaryKey(pkContainer));
            }
        }
        return query.toString();
    }

    private String printPrimaryKey(Container primaryKey) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfPkChildren = primaryKey.getChildren();
        query.append("PRIMARY KEY " + getKeyPart(listOfPkChildren) + "\n");
        return query.toString();
    }

    private String getKeyPart(List<Container> listOfPkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < listOfPkChildren.size(); i++) {
            for (int j = 0; j < listOfPkChildren.size(); j++) {
                Container pkContainer = listOfPkChildren.get(j);
                int seqIndex = Integer.parseInt((String) pkContainer
                    .getAttributes().get(FkAttributes.ORDINAL_POSITION.getElement()));
                if (seqIndex == i + 1) {
                    query.append(pkContainer
                        .getAttributes().get(FkAttributes.COLUMN_NAME.getElement()) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append("),");
        return query.toString();
    }
}
