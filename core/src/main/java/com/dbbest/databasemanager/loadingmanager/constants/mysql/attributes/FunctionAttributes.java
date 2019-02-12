package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the function node.
 */
public final class FunctionAttributes {

    private FunctionAttributes() {
    }

    public static final String FUNCTION_PROCEDURE_NAME = "SPECIFIC_NAME";
    public static final String ROUTINE_SCHEMA = "ROUTINE_SCHEMA";
    public static final String DATA_TYPE = "DATA_TYPE";
    public static final String ROUTINE_DEFINITION = "ROUTINE_DEFINITION";
    public static final String DEFINER = "DEFINER";
    public static final String ROUTINE_COMMENT = "ROUTINE_COMMENT";
    public static final String EXTERNAL_LANGUAGE = "EXTERNAL_LANGUAGE";
    public static final String IS_DETERMINISTIC = "IS_DETERMINISTIC";
    public static final String SQL_DATA_ACCESS = "SQL_DATA_ACCESS";
    public static final String SECURITY_TYPE = "SECURITY_TYPE";

    public static List<String> getListOfFunctionAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"ROUTINE_CATALOG", /*"ROUTINE_SCHEMA",*/ "ROUTINE_NAME",
            "ROUTINE_TYPE", /*"DATA_TYPE",*/ "CHARACTER_MAXIMUM_LENGTH",
            "CHARACTER_OCTET_LENGTH", "NUMERIC_PRECISION", "NUMERIC_SCALE",
            "DATETIME_PRECISION", "CHARACTER_SET_NAME", "COLLATION_NAME",
            "DTD_IDENTIFIER", "ROUTINE_BODY", /*"ROUTINE_DEFINITION",*/
            "EXTERNAL_NAME", /*"EXTERNAL_LANGUAGE",*/ "PARAMETER_STYLE",
            /*"IS_DETERMINISTIC"*//*, "SQL_DATA_ACCESS",*/ "SQL_PATH",
            /*"SECURITY_TYPE",*/ "CREATED", "LAST_ALTERED", "SQL_MODE",
            /*"ROUTINE_COMMENT",*/ /*"DEFINER",*/ "CHARACTER_SET_CLIENT",
            "COLLATION_CONNECTION", "DATABASE_COLLATION"};
        listOfAttributes.addAll(listOfConstants);

        listOfAttributes.add(ROUTINE_SCHEMA);
        listOfAttributes.add(DATA_TYPE);
        listOfAttributes.add(ROUTINE_DEFINITION);
        listOfAttributes.add(DEFINER);
        listOfAttributes.add(ROUTINE_COMMENT);
        listOfAttributes.add(EXTERNAL_LANGUAGE);
        listOfAttributes.add(IS_DETERMINISTIC);
        listOfAttributes.add(SQL_DATA_ACCESS);
        listOfAttributes.add(SECURITY_TYPE);

        return (List<String>) listOfAttributes;
    }
}
