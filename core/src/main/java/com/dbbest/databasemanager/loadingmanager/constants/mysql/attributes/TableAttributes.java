package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the table node.
 */
public final class TableAttributes {

    private TableAttributes() {
    }

    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String AVG_ROW_LENGTH = "AVG_ROW_LENGTH";
    public static final String CHECKSUM = "CHECKSUM";
    public static final String TABLE_COMMENT = "TABLE_COMMENT";
    public static final String ENGINE = "ENGINE";
    public static final String ROW_FORMAT = "ROW_FORMAT";
    public static final String DATA_FREE = "DATA_FREE";
    public static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    public static final String TABLE_COLLATION = "TABLE_COLLATION";
    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

    /**
     * @return returns the list of table attributes for the detailed table loader.
     */
    public static List<String> getListOfTableAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", /*"TABLE_SCHEMA",*/ "TABLE_TYPE"
            /*, "ENGINE"*/, "VERSION", /*"ROW_FORMAT",*/
            "TABLE_ROWS", /*"AVG_ROW_LENGTH",*/ "DATA_LENGTH",
            "MAX_DATA_LENGTH", "INDEX_LENGTH", /*"DATA_FREE",*/ /*"AUTO_INCREMENT",*/
            "CREATE_TIME", "UPDATE_TIME", "CHECK_TIME",
            /*"TABLE_COLLATION",*/ /*"CHECKSUM",*/ "CREATE_OPTIONS"/*, "TABLE_COMMENT"*/};
        listOfAttributes.addAll(listOfConstants);

        listOfAttributes.add(AVG_ROW_LENGTH);
        listOfAttributes.add(CHECKSUM);
        listOfAttributes.add(TABLE_COMMENT);
        listOfAttributes.add(ENGINE);
        listOfAttributes.add(ROW_FORMAT);
        listOfAttributes.add(DATA_FREE);
        listOfAttributes.add(AUTO_INCREMENT);
        listOfAttributes.add(TABLE_COLLATION);
        listOfAttributes.add(TABLE_SCHEMA);

        return (List<String>) listOfAttributes;
    }
}
