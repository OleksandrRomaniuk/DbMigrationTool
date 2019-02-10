package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

/**
 * The class with the mysql name attributes for each node.
 */
public final class NameAttributes {
    private Map<String, String> nameAttributeConstants;
    private static NameAttributes instance;

    private NameAttributes() {
        nameAttributeConstants = initializeAttributes();
    }

    /**
     * @return returns the instance of the class.
     */
    public static NameAttributes getInstance() {
        if (instance == null) {
            instance = new NameAttributes();
        }
        return instance;
    }

    private Map<String, String> initializeAttributes() {
        Map<String, String> nameAttributeConstants = new HashMap();
        nameAttributeConstants.put(LoaderPrinterName.TABLE, TableAttributes.TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.FUNCTION, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.STORED_PROCEDURE, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEW, ViewAttributes.TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_COLUMN, TableColumnAttributes.COLUMN_NAME);
        nameAttributeConstants.put(LoaderPrinterName.INDEX, IndexAttributes.INDEX_NAME);
        nameAttributeConstants.put(LoaderPrinterName.FOREIGN_KEY, ConstraintAttributes.CONSTRAINT_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TRIGGER, TriggerAttributes.TRIGGER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER,
            FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEW_COLUMN, TableColumnAttributes.COLUMN_NAME);
        nameAttributeConstants.put(LoaderPrinterName.CONSTRAINT, ConstraintAttributes.CONSTRAINT_NAME);
        return nameAttributeConstants;
    }

    public Map<String, String> getNameAttributes() {
        return nameAttributeConstants;
    }
}
