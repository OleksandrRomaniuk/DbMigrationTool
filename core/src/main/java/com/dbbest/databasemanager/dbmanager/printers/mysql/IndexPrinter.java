package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.IndexAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;

/**
 * The class-printer of the mysql indexes.
 */
@PrinterAnnotation(NameConstants.INDEX)
public class IndexPrinter implements Printer {
    @Override
    public String execute(Container indexContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE" + isUnique((Container) indexContainer.getChildren().get(0)) + " INDEX "
            + indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME)
            + getType((Container) indexContainer.getChildren().get(0))
            + "\nON " + getTableName((Container) indexContainer.getChildren().get(0))
            + getColumnName(indexContainer));

        return query.toString();
    }

    private String isUnique(Container indexContainer) {
        return indexContainer.getAttributes().get(IndexAttributes.NON_UNIQUE).equals(0) ? " UNIQUE" : "";
    }

    private String getName(Container indexContainer) {
        return (String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME);
    }

    private String getType(Container indexContainer) {
        if (indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE).equals("BTREE")
            || indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE).equals("HASH")) {
            return (String) " USING " + indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE);
        } else {
            return "";
        }
    }

    private String getTableName(Container indexContainer) {
        return (String) indexContainer.getAttributes().get(IndexAttributes.TABLE_SCHEMA) + "."
            + indexContainer.getAttributes().get(TableAttributes.TABLE_NAME);
    }

    private String getColumnName(Container indexContainer) {


        return getKeyPart(indexContainer.getChildren());
    }

    private String getKeyPart(List<Container> indexesWithSameName) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < indexesWithSameName.size(); i++) {
            for (int j = 0; j < indexesWithSameName.size(); j++) {
                Container indexContainer = indexesWithSameName.get(j);
                int seqIndex = Integer.parseInt((String) indexContainer
                    .getAttributes().get(IndexAttributes.SEQ_IN_INDEX));
                if (seqIndex == i + 1) {
                    getOneColumnOfKeyParts(indexContainer, query);
                }
            }
        }

        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }

    private void getOneColumnOfKeyParts(Container indexContainer, StringBuilder query) {
        query.append(indexContainer.getAttributes().get(IndexAttributes.INDEX_COLUMN_NAME));
        if (indexContainer.getAttributes().get(IndexAttributes.SUB_PART) != null
            && !((String) indexContainer.getAttributes().get(IndexAttributes.SUB_PART)).trim().equals("")
            && !((String) indexContainer.getAttributes().get(IndexAttributes.SUB_PART)).trim().equals("null")) {
            query.append(" (");
            query.append(indexContainer.getAttributes().get(IndexAttributes.SUB_PART));
            query.append(")");
        }
        query.append(", ");
    }
}
