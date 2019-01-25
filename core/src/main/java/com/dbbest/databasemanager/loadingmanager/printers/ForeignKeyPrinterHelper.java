package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

public class ForeignKeyPrinterHelper implements Printer {

    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintUnique(tableContainer);
    }

    private String buildPrintUnique(Container tableContainer) throws ContainerException {
        List<Container> fkList = tableContainer
            .getChildByName(TableCategoriesTagNameCategories.ConstraintCategory.getElement()).getChildren();
        StringBuilder query = new StringBuilder();
        if (fkList != null && fkList.size() > 0) {
            for (Container fkContainer : fkList) {
                String constraintType = (String) fkContainer.getAttributes().get(TableConstraintAttributes.CONSTRAINT_TYPE.getElement());
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
            .get(TableConstraintAttributes.CONSTRAINT_NAME.getElement());
        if (constraintName != null && !constraintName.trim().equals("")
            && !constraintName.trim().equals("null")) {
            return " " + constraintName;
        } else {
            return "";
        }
    }

    private String getReference(List<Container> listOfFkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" REFERENCES " + listOfFkChildren.get(0).getAttributes().get(FkAttributes.REFERENCED_TABLE_SCHEMA.getElement())
            + "." + listOfFkChildren.get(0).getAttributes().get(FkAttributes.REFERENCED_TABLE_NAME.getElement()) + " (");
        for (int i = 0; i < listOfFkChildren.size(); i++) {
            for (int j = 0; j < listOfFkChildren.size(); j++) {
                Container uniqueContainer = listOfFkChildren.get(j);
                int seqIndex = Integer.parseInt((String) uniqueContainer
                    .getAttributes().get(FkAttributes.POSITION_IN_UNIQUE_CONSTRAINT.getElement()));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(FkAttributes.REFERENCED_COLUMN_NAME.getElement()) + ", ");
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
                    .getAttributes().get(FkAttributes.ORDINAL_POSITION.getElement()));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(FkAttributes.COLUMN_NAME.getElement()) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }
}
