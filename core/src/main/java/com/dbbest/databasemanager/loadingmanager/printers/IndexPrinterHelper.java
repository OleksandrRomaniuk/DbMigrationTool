package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.IndexAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.ArrayList;
import java.util.List;

public class IndexPrinterHelper implements Printer {
    @Override
    public String execute(Container tableContainer) throws ContainerException {

        return buildPrintIndexes(tableContainer);
    }

    private String buildPrintIndexes(Container tableContainer) throws ContainerException {
        List<Container> lisOfIndexes = tableContainer
            .getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()).getChildren();
        List<String> listOfUniqueIndexNames = getListOfUniqueName(lisOfIndexes);
        StringBuilder query = new StringBuilder();
        for (String indexName : listOfUniqueIndexNames) {
            query.append(printIndex(lisOfIndexes, indexName));
        }
        return query.toString();
    }

    private String printIndex(List<Container> lisOfIndexes, String indexName) {
        StringBuilder query = new StringBuilder();
        List<Container> indexesWithSameName = getIndexesWithSameName(lisOfIndexes, indexName);

        query.append("INDEX " + indexName
            + getIndexType(indexesWithSameName.get(0)) + getKeyPart(indexesWithSameName) + "\n");

        return query.toString();
    }

    private List<Container> getIndexesWithSameName(List<Container> lisOfIndexes, String indexName) {
        List<Container> indexesWithSameName = new ArrayList();
        for (Container indexContainer : lisOfIndexes) {
            if (((String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME.getElement())).trim().equals(indexName)) {
                indexesWithSameName.add(indexContainer);
            }
        }
        return indexesWithSameName;
    }

    private List<String> getListOfUniqueName(List<Container> lisOfIndexes) {
        List<String> listOfUniqueIndexNames = new ArrayList();
        for (Container indexContainer : lisOfIndexes) {
            String indexName = (String) indexContainer.getAttributes().get(IndexAttributes.INDEX_NAME.getElement());
            if (!ifListContainsName(listOfUniqueIndexNames, indexName)) {
                listOfUniqueIndexNames.add(indexName);
            }
        }
        return listOfUniqueIndexNames;
    }

    private boolean ifListContainsName(List<String> listOfUniqueIndexNames, String indexName) {
        for (String nameOfIndex : listOfUniqueIndexNames) {
            if (nameOfIndex.equals(indexName)) {
                return true;
            }
        }
        return false;
    }

    private String getKeyPart(List<Container> indexesWithSameName) {
        StringBuilder query = new StringBuilder();
        query.append(" (");
        for (int i = 0; i < indexesWithSameName.size(); i++) {
            for (int j = 0; j < indexesWithSameName.size(); j++) {
                Container indexContainer = indexesWithSameName.get(j);
                int seqIndex = Integer.parseInt((String) indexContainer
                    .getAttributes().get(IndexAttributes.SEQ_IN_INDEX.getElement()));
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
        query.append(indexContainer.getAttributes().get(IndexAttributes.COLUMN_NAME.getElement()));
        if (indexContainer.getAttributes().get(IndexAttributes.SUB_PART.getElement()) != null
            && !((String) indexContainer.getAttributes().get(IndexAttributes.SUB_PART.getElement())).trim().equals("")
            && !((String) indexContainer.getAttributes().get(IndexAttributes.SUB_PART.getElement())).trim().equals("null")) {
            query.append(" (");
            query.append(indexContainer.getAttributes().get(IndexAttributes.SUB_PART.getElement()));
            query.append(")");
        }
        query.append(", ");
    }


    private String getIndexType(Container indexContainer) {
        if (indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE.getElement()).equals("BTREE")) {
            return " USING BTREE";
        } else if (indexContainer.getAttributes().get(IndexAttributes.INDEX_TYPE.getElement()).equals("HASH")) {
            return " USING HASH";
        } else {
            return "";
        }
    }
}
