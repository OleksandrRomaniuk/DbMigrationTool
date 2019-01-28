package com.dbbest.databasemanager.loadingmanager.constants.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

public class AttributeSingleConstants {
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String FUNCTION_PROCEDURE_NAME = "SPECIFIC_NAME";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String INDEX_NAME = "INDEX_NAME";
    public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    public static final String TRIGGER_NAME = "TRIGGER_NAME";
    public static final String PROC_FUNC_PARAMETER_NAME = "PARAMETER_NAME";
    public static final Map<String, String> NAME_ATTRIBUTE_CONSTANTS;

    static {
        NAME_ATTRIBUTE_CONSTANTS = new HashMap();
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.TABLE, TABLE_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.FUNCTION, FUNCTION_PROCEDURE_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.STORED_PROCEDURE, FUNCTION_PROCEDURE_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.VIEW, TABLE_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.TABLE_COLUMN, COLUMN_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.INDEX, INDEX_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.FOREIGN_KEY, CONSTRAINT_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.TRIGGER, TRIGGER_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, PROC_FUNC_PARAMETER_NAME);
        NAME_ATTRIBUTE_CONSTANTS.put(LoaderPrinterName.VIEW_COLUMN, COLUMN_NAME);
    }
}
