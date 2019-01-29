package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
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
            .getChildByName(LoaderPrinterName.TABLE_INDEXES).getChildren();
        StringBuilder query = new StringBuilder();
        if (lisOfIndexes != null && lisOfIndexes.size() > 0) {
            for (Container index : lisOfIndexes) {
                query.append(printIndex(index));
            }
        }
        return query.toString();
    }

    private String printIndex(Container index) {
        StringBuilder query = new StringBuilder();
        if (!index.getAttributes().get(AttributeSingleConstants.INDEX_NAME).equals("PRIMARY")) {
            query.append("INDEX " + index.getAttributes().get(AttributeSingleConstants.INDEX_NAME)
                + getIndexType((Container) index.getChildren().get(0)) + getKeyPart(index.getChildren()) + ",\n");
        }
        return query.toString();
    }

    private List<String> getListOfUniqueName(List<Container> lisOfIndexes) {
        List<String> listOfUniqueIndexNames = new ArrayList();
        if (lisOfIndexes.size() > 0) {
            for (Container indexContainer : lisOfIndexes) {
                String indexName = (String) indexContainer.getAttributes().get(AttributeSingleConstants.INDEX_NAME);
                if (!ifListContainsName(listOfUniqueIndexNames, indexName) && !indexName.equals("PRIMARY")) {
                    listOfUniqueIndexNames.add(indexName);
                }
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
                    .getAttributes().get(AttributeSingleConstants.SEQ_IN_INDEX));
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
        query.append(indexContainer.getAttributes().get(AttributeSingleConstants.COLUMN_NAME));
        if (indexContainer.getAttributes().get(AttributeSingleConstants.SUB_PART) != null
            && !((String) indexContainer.getAttributes().get(AttributeSingleConstants.SUB_PART)).trim().equals("")
            && !((String) indexContainer.getAttributes().get(AttributeSingleConstants.SUB_PART)).trim().equals("null")) {
            query.append(" (");
            query.append(indexContainer.getAttributes().get(AttributeSingleConstants.SUB_PART));
            query.append(")");
        }
        query.append(", ");
    }

    private String getIndexType(Container index) {
        if (index.getAttributes().get(AttributeSingleConstants.INDEX_TYPE).equals("BTREE")) {
            return " USING BTREE";
        } else if (index.getAttributes().get(AttributeSingleConstants.INDEX_TYPE).equals("HASH")) {
            return " USING HASH";
        } else {
            return "";
        }
    }
}
