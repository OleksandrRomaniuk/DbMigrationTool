package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

/**
 * The class with the mysql name attributes for each node.
 */
public final class NameAttributes {
    private NameAttributes() {
    }

    public static Map<String, String> getNameAttributesMap() {
        Map<String, String> nameAttributeConstants = new HashMap();
        nameAttributeConstants.put(LoaderPrinterName.TABLES, TableAttributes.TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.FUNCTIONS, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.STORED_PROCEDURES, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEWS, ViewAttributes.TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_COLUMNS, TableColumnAttributes.COLUMN_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_INDEXES, IndexAttributes.INDEX_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_FOREIGN_KEYS, ConstraintAttributes.CONSTRAINT_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_TRIGGERS, TriggerAttributes.TRIGGER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER,
            FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEW_COLUMN, TableColumnAttributes.COLUMN_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_CONSTRAINTS, ConstraintAttributes.CONSTRAINT_NAME);
        return nameAttributeConstants;
    }
}
