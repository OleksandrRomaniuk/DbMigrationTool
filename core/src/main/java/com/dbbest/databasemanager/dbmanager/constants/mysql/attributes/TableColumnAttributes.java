package com.dbbest.databasemanager.dbmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the table column node.
 */
public final class TableColumnAttributes {

    private TableColumnAttributes() {
    }

    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String EXTRA = "EXTRA";
    public static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    public static final String GENERATION_EXPRESSION = "GENERATION_EXPRESSION";
    public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
    public static final String COLUMN_KEY = "COLUMN_KEY";
    public static final String COLLATION_NAME = "COLLATION_NAME";
    public static final String COLUMN_TYPE = "COLUMN_TYPE";
    public static final String COLUMN_IS_NULLABLE = "IS_NULLABLE";

    /**
     * @return returns the list of attributes for the detailed table column loader.
     */
    public static List<String> getListOfTableColumnAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "ORDINAL_POSITION",
            /*"COLUMN_DEFAULT",*/ /*"IS_NULLABLE",*/ "DATA_TYPE", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH",
            "NUMERIC_PRECISION", "NUMERIC_SCALE", "DATETIME_PRECISION", "CHARACTER_SET_NAME",
            /*"COLLATION_NAME",*/ /*"COLUMN_TYPE",*/ /*"COLUMN_KEY",*/ /*"EXTRA",*/ "PRIVILEGES"/*, "COLUMN_COMMENT"*/,
            /*"GENERATION_EXPRESSION"*/};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(EXTRA);
        listOfAttributes.add(COLUMN_COMMENT);
        listOfAttributes.add(GENERATION_EXPRESSION);
        listOfAttributes.add(COLUMN_DEFAULT);
        listOfAttributes.add(COLUMN_KEY);
        listOfAttributes.add(COLLATION_NAME);
        listOfAttributes.add(COLUMN_TYPE);
        listOfAttributes.add(COLUMN_IS_NULLABLE);
        return (List<String>) listOfAttributes;
    }

}
