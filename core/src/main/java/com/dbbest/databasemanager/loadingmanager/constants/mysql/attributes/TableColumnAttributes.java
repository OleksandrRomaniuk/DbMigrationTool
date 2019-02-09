package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

public class TableColumnAttributes implements Attributes {

    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String EXTRA = "EXTRA";
    public static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    public static final String GENERATION_EXPRESSION = "GENERATION_EXPRESSION";
    public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
    public static final String COLUMN_KEY = "COLUMN_KEY";
    public static final String COLLATION_NAME = "COLLATION_NAME";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfTableColumnAttributes();
    }

    private List<String> getListOfTableColumnAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "ORDINAL_POSITION",
            /*"COLUMN_DEFAULT",*/ "IS_NULLABLE", "DATA_TYPE", "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH",
            "NUMERIC_PRECISION", "NUMERIC_SCALE", "DATETIME_PRECISION", "CHARACTER_SET_NAME",
            /*"COLLATION_NAME",*/ "COLUMN_TYPE", /*"COLUMN_KEY",*/ /*"EXTRA",*/ "PRIVILEGES"/*, "COLUMN_COMMENT"*/,
            /*"GENERATION_EXPRESSION"*/};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(EXTRA);
        listOfAttributes.add(COLUMN_COMMENT);
        listOfAttributes.add(GENERATION_EXPRESSION);
        listOfAttributes.add(COLUMN_DEFAULT);
        listOfAttributes.add(COLUMN_KEY);
        listOfAttributes.add(COLLATION_NAME);
        return (List<String>) listOfAttributes;
    }

}
