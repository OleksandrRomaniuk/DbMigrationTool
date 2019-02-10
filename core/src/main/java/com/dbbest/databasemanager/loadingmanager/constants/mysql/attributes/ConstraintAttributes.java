package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the constraint node.
 */
public class ConstraintAttributes implements Attributes {

    public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfDetailedLoadAttributeNames();
    }

    /**
     * @return returns the list of the attributes of the constraint node.
     */
    public List<String> getListOfLazyLoadAttributeNames() {
        DbList<String> listOfAttributeNames = new ListOfChildren();
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA"
            /*, "TABLE_SCHEMA", "TABLE_NAME"/, "CONSTRAINT_TYPE",
            "CONSTRAINT_NAME"*/};
        listOfAttributeNames.addAll(listOfConstants);
        listOfAttributeNames.add(TABLE_SCHEMA);
        listOfAttributeNames.add(TABLE_NAME);
        listOfAttributeNames.add(CONSTRAINT_TYPE);
        listOfAttributeNames.add(CONSTRAINT_NAME);
        return (List<String>) listOfAttributeNames;
    }

    private List<String> getListOfDetailedLoadAttributeNames() {
        DbList<String> listOfAttributeNames = new ListOfChildren();
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA", "TABLE_CATALOG",
            "TABLE_SCHEMA", "TABLE_NAME", "COLUMN_NAME", "ORDINAL_POSITION", "POSITION_IN_UNIQUE_CONSTRAINT",
            "REFERENCED_TABLE_SCHEMA", "REFERENCED_TABLE_NAME", "REFERENCED_COLUMN_NAME", "CONSTRAINT_NAME"};
        listOfAttributeNames.addAll(listOfConstants);
        return (List<String>) listOfAttributeNames;
    }
}
