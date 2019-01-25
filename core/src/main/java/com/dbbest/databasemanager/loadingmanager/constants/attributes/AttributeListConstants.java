package com.dbbest.databasemanager.loadingmanager.constants.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeListConstants {
    public static final Map<LoaderPrinterName, List<String>> CONSTANTS = getAttributes();

    private static Map<LoaderPrinterName, List<String>> getAttributes() {
        Map<LoaderPrinterName, List<String>> attributeListConstants = new HashMap();
        attributeListConstants.put(LoaderPrinterName.Table, getListOfTableAttributes());
        return attributeListConstants;
    }

    private static List<String> getListOfTableAttributes() {
        DbList<String> listOfTableAttributes = new ListOfChildren(); // do not include TABLE_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "TABLE_TYPE", "ENGINE", "VERSION", "ROW_FORMAT",
            "TABLE_ROWS", "AVG_ROW_LENGTH", "DATA_LENGTH", "MAX_DATA_LENGTH", "INDEX_LENGTH", "DATA_FREE", "AUTO_INCREMENT", "CREATE_TIME",
            "UPDATE_TIME", "CHECK_TIME", "TABLE_COLLATION", "CHECKSUM", "CREATE_OPTIONS", "TABLE_COMMENT"};
        listOfTableAttributes.addAll(listOfConstants);
        return (List<String>) listOfTableAttributes;
    }
}
