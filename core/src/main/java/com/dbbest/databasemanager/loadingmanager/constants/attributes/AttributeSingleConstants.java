package com.dbbest.databasemanager.loadingmanager.constants.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

/**
 * The class containing mysql database attributes.
 */
public final class AttributeSingleConstants {
    //Name attributes
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String FUNCTION_PROCEDURE_NAME = "SPECIFIC_NAME";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String INDEX_NAME = "INDEX_NAME";
    public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    public static final String TRIGGER_NAME = "TRIGGER_NAME";
    public static final String PROC_FUNC_PARAMETER_NAME = "PARAMETER_NAME";
    //Column attributes
    public static final String COLUMN_TYPE = "COLUMN_TYPE";
    public static final String COLUMN_IS_NULLABLE = "IS_NULLABLE";
    // Foreign keys attributes
    public static final String REFERENCED_TABLE_SCHEMA = "REFERENCED_TABLE_SCHEMA";
    public static final String REFERENCED_TABLE_NAME = "REFERENCED_TABLE_NAME";
    public static final String POSITION_IN_UNIQUE_CONSTRAINT = "POSITION_IN_UNIQUE_CONSTRAINT";
    public static final String REFERENCED_COLUMN_NAME = "REFERENCED_COLUMN_NAME";
    public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
    public static final String FK_COLUMN_NAME = "COLUMN_NAME";
    //Table constraint attributes
    public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
    //Function attributes
    public static final String ROUTINE_SCHEMA = "ROUTINE_SCHEMA";
    public static final String DATA_TYPE = "DATA_TYPE";
    public static final String ROUTINE_DEFINITION = "ROUTINE_DEFINITION";
    public static final String DEFINER = "DEFINER";
    public static final String ROUTINE_COMMENT = "ROUTINE_COMMENT";
    public static final String EXTERNAL_LANGUAGE = "EXTERNAL_LANGUAGE";
    public static final String IS_DETERMINISTIC = "IS_DETERMINISTIC";
    public static final String SQL_DATA_ACCESS = "SQL_DATA_ACCESS";
    public static final String SECURITY_TYPE = "SECURITY_TYPE";
    //Index attributes
    public static final String NON_UNIQUE = "NON_UNIQUE";
    public static final String INDEX_TYPE = "INDEX_TYPE";
    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
    public static final String INDEX_TABLE_NAME = "TABLE_NAME";
    public static final String SEQ_IN_INDEX = "SEQ_IN_INDEX";
    public static final String INDEX_COLUMN_NAME = "COLUMN_NAME";
    public static final String SUB_PART = "SUB_PART";
    //Schema attributes
    public static final String DEFAULT_CHARACTER_SET_NAME = "DEFAULT_CHARACTER_SET_NAME";
    public static final String DEFAULT_COLLATION_NAME = "DEFAULT_COLLATION_NAME";
    //Function or Procedure parameter attributes
    public static final String PARAMETER_MODE = "PARAMETER_MODE";
    //Table column attributes
    public static final String EXTRA = "EXTRA";
    public static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    public static final String GENERATION_EXPRESSION = "GENERATION_EXPRESSION";
    public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
    public static final String COLUMN_KEY = "COLUMN_KEY";
    public static final String COLLATION_NAME = "COLLATION_NAME";
    //Table attributes
    public static final String AVG_ROW_LENGTH = "AVG_ROW_LENGTH";
    public static final String CHECKSUM = "CHECKSUM";
    public static final String TABLE_COMMENT = "TABLE_COMMENT";
    public static final String ENGINE = "ENGINE";
    public static final String ROW_FORMAT = "ROW_FORMAT";
    public static final String DATA_FREE = "DATA_FREE";
    public static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    public static final String TABLE_COLLATION = "TABLE_COLLATION";
    //Trigger attributes
    public static final String EVENT_OBJECT_SCHEMA = "EVENT_OBJECT_SCHEMA";
    public static final String ACTION_TIMING = "ACTION_TIMING";
    public static final String EVENT_MANIPULATION = "EVENT_MANIPULATION";
    public static final String EVENT_OBJECT_TABLE = "EVENT_OBJECT_TABLE";
    public static final String ACTION_STATEMENT = "ACTION_STATEMENT";
    //View attributes
    public static final String VIEW_DEFINITION = "VIEW_DEFINITION";


    private Map<String, String> nameAttributes = initializeAttributes();
    private static AttributeSingleConstants instance;

    private AttributeSingleConstants() {
    }

    /**
     * @return returns the instance of the class.
     */
    public static AttributeSingleConstants getInstance() {
        if (instance == null) {
            instance = new AttributeSingleConstants();
        }
        return instance;
    }

    private Map<String, String> initializeAttributes() {
        Map<String, String> nameAttributeConstants = new HashMap();
        nameAttributeConstants.put(LoaderPrinterName.TABLE, TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.FUNCTION, FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.STORED_PROCEDURE, FUNCTION_PROCEDURE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEW, TABLE_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TABLE_COLUMN, COLUMN_NAME);
        nameAttributeConstants.put(LoaderPrinterName.INDEX, INDEX_NAME);
        nameAttributeConstants.put(LoaderPrinterName.FOREIGN_KEY, CONSTRAINT_NAME);
        nameAttributeConstants.put(LoaderPrinterName.TRIGGER, TRIGGER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, PROC_FUNC_PARAMETER_NAME);
        nameAttributeConstants.put(LoaderPrinterName.VIEW_COLUMN, COLUMN_NAME);
        return nameAttributeConstants;
    }

    public Map<String, String> getNameAttributes() {
        return nameAttributes;
    }
}
