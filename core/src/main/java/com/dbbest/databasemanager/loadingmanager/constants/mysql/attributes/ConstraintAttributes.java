package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

public class ConstraintAttributes implements Attributes {

    public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfDetailedLoadAttributeNames();
    }

    public List<String> getListOfLazyLoadAttributeNames() {
        DbList<String> listOfAttributeNames = new ListOfChildren(); // do not include SCHEMA_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA", "TABLE_SCHEMA", "TABLE_NAME"/*, "CONSTRAINT_TYPE",
            "CONSTRAINT_NAME"*/};
        listOfAttributeNames.addAll(listOfConstants);
        listOfAttributeNames.add(CONSTRAINT_TYPE);
        listOfAttributeNames.add(CONSTRAINT_NAME);
        return (List<String>) listOfAttributeNames;
    }

    private List<String> getListOfDetailedLoadAttributeNames() {
        DbList<String> listOfAttributeNames = new ListOfChildren(); // do not include SCHEMA_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"CONSTRAINT_CATALOG", "CONSTRAINT_SCHEMA", "TABLE_CATALOG",
            "TABLE_SCHEMA", "TABLE_NAME", "COLUMN_NAME", "ORDINAL_POSITION", "POSITION_IN_UNIQUE_CONSTRAINT",
            "REFERENCED_TABLE_SCHEMA", "REFERENCED_TABLE_NAME", "REFERENCED_COLUMN_NAME", "CONSTRAINT_NAME"};
        listOfAttributeNames.addAll(listOfConstants);
        return (List<String>) listOfAttributeNames;
    }
}
