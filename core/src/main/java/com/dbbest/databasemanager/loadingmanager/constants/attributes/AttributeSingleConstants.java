package com.dbbest.databasemanager.loadingmanager.constants.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

public class AttributeSingleConstants {
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String FUNCTION_PROCEDURE_NAME = "SPECIFIC_NAME";
    public static final Map<String, String> NAME_ATTRIBUTE_CONSTANTS;

    static {
        NAME_ATTRIBUTE_CONSTANTS = new HashMap();
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.TABLE, TABLE_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.FUNCTION, FUNCTION_PROCEDURE_NAME);
    }
}
