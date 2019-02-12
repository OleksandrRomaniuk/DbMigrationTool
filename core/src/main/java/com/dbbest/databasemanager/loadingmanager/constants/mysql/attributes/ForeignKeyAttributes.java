package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the fk node.
 */
public final class ForeignKeyAttributes {

    private ForeignKeyAttributes() {
    }

    public static final String FUNCTION_PROCEDURE_NAME = "SPECIFIC_NAME";
    public static final String REFERENCED_TABLE_SCHEMA = "REFERENCED_TABLE_SCHEMA";
    public static final String REFERENCED_TABLE_NAME = "REFERENCED_TABLE_NAME";
    public static final String POSITION_IN_UNIQUE_CONSTRAINT = "POSITION_IN_UNIQUE_CONSTRAINT";
    public static final String REFERENCED_COLUMN_NAME = "REFERENCED_COLUMN_NAME";
    public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
    public static final String FK_COLUMN_NAME = "COLUMN_NAME";

    public static List<String> getListOfFKAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA", "TABLE_CATALOG",
            "TABLE_SCHEMA", "TABLE_NAME", "COLUMN_NAME", "CONSTRAINT_NAME"
            /*"ORDINAL_POSITION"*/, /*"POSITION_IN_UNIQUE_CONSTRAINT",*/
            /*"REFERENCED_TABLE_SCHEMA",*/ /*"REFERENCED_TABLE_NAME",*/ /*"REFERENCED_COLUMN_NAME"*/};
        listOfAttributes.addAll(listOfConstants);

        listOfAttributes.add(ORDINAL_POSITION);
        listOfAttributes.add(POSITION_IN_UNIQUE_CONSTRAINT);
        listOfAttributes.add(REFERENCED_TABLE_SCHEMA);
        listOfAttributes.add(REFERENCED_TABLE_NAME);
        listOfAttributes.add(REFERENCED_COLUMN_NAME);

        return (List<String>) listOfAttributes;
    }
}
