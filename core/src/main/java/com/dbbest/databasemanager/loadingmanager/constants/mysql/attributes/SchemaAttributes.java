package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the schema node.
 */
public class SchemaAttributes implements Attributes {

    public static final String SCHEMA_NAME = "SCHEMA_NAME";
    public static final String DEFAULT_CHARACTER_SET_NAME = "DEFAULT_CHARACTER_SET_NAME";
    public static final String DEFAULT_COLLATION_NAME = "DEFAULT_COLLATION_NAME";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfSchemaAttribute();
    }

    private List<String> getListOfSchemaAttribute() {
        DbList<String> listOfAttribute = new ListOfChildren(); // do not include SCHEMA_NAME as it is in AttributSingleConstants
        String[] listOfConstants = {"CATALOG_NAME", "SQL_PATH"};
        listOfAttribute.addAll(listOfConstants);

        listOfAttribute.add(DEFAULT_CHARACTER_SET_NAME);
        listOfAttribute.add(DEFAULT_COLLATION_NAME);

        return (List<String>) listOfAttribute;
    }
}
