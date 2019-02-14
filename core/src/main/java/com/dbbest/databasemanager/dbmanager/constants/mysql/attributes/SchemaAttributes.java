package com.dbbest.databasemanager.dbmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the schema node.
 */
public final class SchemaAttributes {

    private SchemaAttributes() {
    }

    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String DEFAULT_CHARACTER_SET_NAME = "DEFAULT_CHARACTER_SET_NAME";
    public static final String DEFAULT_COLLATION_NAME = "DEFAULT_COLLATION_NAME";

    /**
     * @return returns the list of schema attributes for the detailed schema loader.
     */
    public static List<String> getListOfSchemaAttribute() {
        DbList<String> listOfAttribute = new ListOfChildren();
        String[] listOfConstants = {"CATALOG_NAME", "SQL_PATH"};
        listOfAttribute.addAll(listOfConstants);

        listOfAttribute.add(DEFAULT_CHARACTER_SET_NAME);
        listOfAttribute.add(DEFAULT_COLLATION_NAME);

        return (List<String>) listOfAttribute;
    }
}
