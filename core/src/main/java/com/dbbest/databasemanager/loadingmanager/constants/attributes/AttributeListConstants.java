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
        attributeListConstants.put(LoaderPrinterName.FUNCTION, getListOfFunctionProcedureAttributes());
        attributeListConstants.put(LoaderPrinterName.STORED_PROCEDURE, getListOfFunctionProcedureAttributes());
        attributeListConstants.put(LoaderPrinterName.SCHEMA, getListOfSchemaAttributes());
        attributeListConstants.put(LoaderPrinterName.VIEW, getListOfViewAttributes());
        attributeListConstants.put(LoaderPrinterName.TABLE_COLUMN, getListOfColumnAttributes());
        attributeListConstants.put(LoaderPrinterName.INDEX, getListOfIndexAttributes());
        attributeListConstants.put(LoaderPrinterName.FOREIGN_KEY, getListOfForeignKeyAttributes());
        return attributeListConstants;
    }

    private List<String> getListOfTableAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren(); // do not include TABLE_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "TABLE_TYPE", "ENGINE", "VERSION", "ROW_FORMAT",
            "TABLE_ROWS", "AVG_ROW_LENGTH", "DATA_LENGTH", "MAX_DATA_LENGTH", "INDEX_LENGTH", "DATA_FREE", "AUTO_INCREMENT", "CREATE_TIME",
            "UPDATE_TIME", "CHECK_TIME", "TABLE_COLLATION", "CHECKSUM", "CREATE_OPTIONS", "TABLE_COMMENT"};
        listOfAttributes.addAll(listOfConstants);
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfFunctionProcedureAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren(); // do not include SPECIFIC_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"ROUTINE_CATALOG", "ROUTINE_SCHEMA", "ROUTINE_NAME",
            "ROUTINE_TYPE", "DATA_TYPE", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH", "NUMERIC_PRECISION", "NUMERIC_SCALE",
            "DATETIME_PRECISION", "CHARACTER_SET_NAME", "COLLATION_NAME", "DTD_IDENTIFIER", "ROUTINE_BODY", "ROUTINE_DEFINITION", "EXTERNAL_NAME",
            "EXTERNAL_LANGUAGE", "PARAMETER_STYLE", "IS_DETERMINISTIC", "SQL_DATA_ACCESS", "SQL_PATH", "SECURITY_TYPE", "CREATED", "LAST_ALTERED", "SQL_MODE", "ROUTINE_COMMENT",
            "DEFINER", "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION", "DATABASE_COLLATION"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfSchemaAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren(); // do not include SCHEMA_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"CATALOG_NAME", "SCHEMA_NAME", "DEFAULT_CHARACTER_SET_NAME",
            "DEFAULT_COLLATION_NAME", "SQL_PATH"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfViewAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();//do not include TABLE_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "VIEW_DEFINITION",
            "CHECK_OPTION", "IS_UPDATABLE", "DEFINER", "SECURITY_TYPE", "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfColumnAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();//do not include COLUMN_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "ORDINAL_POSITION",
            "COLUMN_DEFAULT", "IS_NULLABLE", "DATA_TYPE", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH",
            "NUMERIC_PRECISION", "NUMERIC_SCALE", "DATETIME_PRECISION", "CHARACTER_SET_NAME",
            "COLLATION_NAME", "COLUMN_TYPE", "COLUMN_KEY", "EXTRA", "PRIVILEGES", "COLUMN_COMMENT",
            "GENERATION_EXPRESSION"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfIndexAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();//do not include INDEX_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "NON_UNIQUE", "INDEX_SCHEMA",
            "SEQ_IN_INDEX", "COLUMN_NAME", "COLLATION", "CARDINALITY",
            "SUB_PART", "PACKED", "NULLABLE", "INDEX_TYPE", "COMMENT", "INDEX_COMMENT"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfForeignKeyAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();//do not include CONSTRAINT_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA", "TABLE_CATALOG",
            "TABLE_SCHEMA", "TABLE_NAME", "COLUMN_NAME", "ORDINAL_POSITION",
            "POSITION_IN_UNIQUE_CONSTRAINT", "REFERENCED_TABLE_SCHEMA",
            "REFERENCED_TABLE_NAME", "REFERENCED_COLUMN_NAME"};
        return (List<String>) listOfAttributes;
    }

    private List<String> getListOfTriggerAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();//do not include TRIGGER_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"TRIGGER_CATALOG", "TRIGGER_SCHEMA", "TRIGGER_NAME",
            "EVENT_MANIPULATION", "EVENT_OBJECT_CATALOG",
            "EVENT_OBJECT_SCHEMA", "EVENT_OBJECT_TABLE",
            "ACTION_ORDER", "ACTION_CONDITION", "ACTION_STATEMENT",
            "ACTION_ORIENTATION", "ACTION_TIMING", "ACTION_REFERENCE_OLD_TABLE",
            "ACTION_REFERENCE_NEW_TABLE", "ACTION_REFERENCE_OLD_ROW",
            "ACTION_REFERENCE_NEW_ROW", "CREATED", "SQL_MODE", "DEFINER",
            "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION",
            "DATABASE_COLLATION"};
        return (List<String>) listOfAttributes;
    }

    public Map<String, List<String>> getMapOfConstants() {
        return constants;
    }
}
