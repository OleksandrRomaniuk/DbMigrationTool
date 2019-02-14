package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ForeignKeyAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import java.util.List;

/**
 * The class-printer of foreign keys (assists TablePrinter class).
 */
@PrinterAnnotation(NameConstants.FOREIGN_KEY)
public class ForeignKeyPrinter implements Printer {

    @Override
    public String execute(Container fkContainer) throws ContainerException {
        return printForeignKey(fkContainer);
    }

    private String printForeignKey(Container fkContainer) {
        StringBuilder query = new StringBuilder();
        List<Container> listOfFkChildren = fkContainer.getChildren();
        System.out.println(listOfFkChildren.size());
        query.append("ALTER TABLE " + ((Container)fkContainer.getChildren().get(0))
            .getAttributes().get(ConstraintAttributes.TABLE_SCHEMA)
            + "." + ((Container)fkContainer.getChildren().get(0)).getAttributes().get(ConstraintAttributes.TABLE_NAME) + "\n");
        query.append("ADD FOREIGN KEY" + getName(fkContainer) + getColumns(listOfFkChildren)
            + getReference(listOfFkChildren));
        return query.toString();
    }

    private String getName(Container fkContainer) {
        String constraintName = (String) fkContainer.getAttributes()
            .get(ConstraintAttributes.CONSTRAINT_NAME);
        if (constraintName != null && !constraintName.trim().equals("")
            && !constraintName.trim().equals("null")) {
            return " " + constraintName;
        } else {
            return "";
        }
    }

    private String getReference(List<Container> listOfFkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" REFERENCES " + listOfFkChildren.get(0).getAttributes().get(ForeignKeyAttributes.REFERENCED_TABLE_SCHEMA)
            + "." + listOfFkChildren.get(0).getAttributes().get(ForeignKeyAttributes.REFERENCED_TABLE_NAME) + " (");
        for (int i = 0; i < listOfFkChildren.size(); i++) {
            for (int j = 0; j < listOfFkChildren.size(); j++) {
                Container uniqueContainer = listOfFkChildren.get(j);
                int seqIndex = Integer.parseInt((String) uniqueContainer
                    .getAttributes().get(ForeignKeyAttributes.POSITION_IN_UNIQUE_CONSTRAINT));
                if (seqIndex == i + 1) {
                    query.append(uniqueContainer
                        .getAttributes().get(ForeignKeyAttributes.REFERENCED_COLUMN_NAME) + ", ");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }

    private String getColumns(List<Container> listOfFkChildren) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < listOfFkChildren.size(); i++) {
            for (int j = 0; j < listOfFkChildren.size(); j++) {
                Container uniqueContainer = listOfFkChildren.get(j);
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
        query.append(")");
        return query.toString();
    }
}
