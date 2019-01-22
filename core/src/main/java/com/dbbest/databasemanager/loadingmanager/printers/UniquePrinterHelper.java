package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

public class UniquePrinterHelper implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintUnique(tableContainer);
    }

    private String buildPrintUnique(Container tableContainer) throws ContainerException {
        List<Container> uniqueList = tableContainer
            .getChildByName(TableCategoriesTagNameCategories.ConstraintCategory.getElement()).getChildren();
        StringBuilder query = new StringBuilder();
        for (Container uniqueContainer : uniqueList) {
            String constraintType = (String) uniqueContainer.getAttributes().get(TableConstraintAttributes.CONSTRAINT_TYPE.getElement());
            if (constraintType.trim().equals("UNIQUE")) {
                query.append(printUnique(uniqueContainer));
            }
        }
        return query.toString();
    }

    private String printUnique(Container uniqueContainer) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfUniqueChildren = uniqueContainer.getChildren();
        query.append("UNIQUE " + getName(uniqueContainer) + getKeyPart(listOfUniqueChildren) + "\n");
        return query.toString();
    }

    private String getName(Container uniqueContainer) {
        String constraintName = (String) uniqueContainer.getAttributes()
            .get(TableConstraintAttributes.CONSTRAINT_NAME.getElement());
        if (constraintName != null && !constraintName.trim().equals("")
            && !constraintName.trim().equals("null")) {
            return constraintName + " ";
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
                    .getAttributes().get(FkAttributes.ORDINAL_POSITION.getElement()));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
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
