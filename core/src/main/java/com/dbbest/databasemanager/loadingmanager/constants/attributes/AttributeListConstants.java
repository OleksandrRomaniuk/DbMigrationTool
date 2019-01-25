package com.dbbest.databasemanager.loadingmanager.constants.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeListConstants {

    private Map<String, List<String>> constants;

    private static AttributeListConstants instance;

    private AttributeListConstants() {
        constants = getAttributes();
    }

    public static AttributeListConstants getInstance() {
        if (instance == null) {
            instance = new AttributeListConstants();
        }
        return instance;
    }

    private Map<String, List<String>> getAttributes() {
        Map<String, List<String>> attributeListConstants = new HashMap();
        attributeListConstants.put(LoaderPrinterName.TABLE, getListOfTableAttributes());
        attributeListConstants.put(LoaderPrinterName.FUNCTION, getListOfFunctionAttributes());
        return attributeListConstants;
    }

    private List<String> getListOfTableAttributes() {
        DbList<String> listOfTableAttributes = new ListOfChildren(); // do not include TABLE_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "TABLE_TYPE", "ENGINE", "VERSION", "ROW_FORMAT",
            "TABLE_ROWS", "AVG_ROW_LENGTH", "DATA_LENGTH", "MAX_DATA_LENGTH", "INDEX_LENGTH", "DATA_FREE", "AUTO_INCREMENT", "CREATE_TIME",
            "UPDATE_TIME", "CHECK_TIME", "TABLE_COLLATION", "CHECKSUM", "CREATE_OPTIONS", "TABLE_COMMENT"};
        listOfTableAttributes.addAll(listOfConstants);
        return (List<String>) listOfTableAttributes;
    }

    private List<String> getListOfFunctionAttributes() {
        DbList<String> listOfFunctionAttributes = new ListOfChildren(); // do not include SPECIFIC_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"SPECIFIC_CATALOG", "SPECIFIC_SCHEMA", "ORDINAL_POSITION", "PARAMETER_MODE", "PARAMETER_NAME",
            "DATA_TYPE", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH", "NUMERIC_PRECISION", "NUMERIC_SCALE", "CHARACTER_SET_NAME",
            "COLLATION_NAME", "DTD_IDENTIFIER", "ROUTINE_TYPE"};
        return (List<String>) listOfFunctionAttributes;
    }

    public Map<String, List<String>> getMapOfConstants() {
        return constants;
    }
}
