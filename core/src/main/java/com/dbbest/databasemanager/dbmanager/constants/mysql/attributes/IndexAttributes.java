package com.dbbest.databasemanager.dbmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the index node.
 */
public final class IndexAttributes {

    private IndexAttributes() {}

    public static final String INDEX_NAME = "INDEX_NAME";
    public static final String NON_UNIQUE = "NON_UNIQUE";
    public static final String INDEX_TYPE = "INDEX_TYPE";
    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
    public static final String INDEX_TABLE_NAME = "TABLE_NAME";
    public static final String SEQ_IN_INDEX = "SEQ_IN_INDEX";
    public static final String INDEX_COLUMN_NAME = "COLUMN_NAME";
    public static final String SUB_PART = "SUB_PART";

    /**
     * @return returns the list of attributes for the detailed index loader.
     */
    public static List<String> getListOfIndexAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", /*"TABLE_SCHEMA", "TABLE_NAME", "NON_UNIQUE",*/ "INDEX_SCHEMA",
            /*"SEQ_IN_INDEX",*/ /*"COLUMN_NAME",*/ "COLLATION", "CARDINALITY",
            /*"SUB_PART",*/ "PACKED", "NULLABLE", /*"INDEX_TYPE",*/ "COMMENT", "INDEX_COMMENT"};
        listOfAttributes.addAll(listOfConstants);

        listOfAttributes.add(NON_UNIQUE);
        listOfAttributes.add(INDEX_TYPE);
        listOfAttributes.add(TABLE_SCHEMA);
        listOfAttributes.add(INDEX_TABLE_NAME);
        listOfAttributes.add(SEQ_IN_INDEX);
        listOfAttributes.add(INDEX_COLUMN_NAME);
        listOfAttributes.add(SUB_PART);


        return (List<String>) listOfAttributes;
    }

}
