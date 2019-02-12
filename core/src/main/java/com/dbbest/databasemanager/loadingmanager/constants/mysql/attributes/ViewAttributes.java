package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the view node.
 */
public final class ViewAttributes {

    private ViewAttributes() {
    }

    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
    public static final String VIEW_DEFINITION = "VIEW_DEFINITION";

    public static List<String> getListOfViewAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", /*"TABLE_SCHEMA",*/ "TABLE_NAME", /*"VIEW_DEFINITION",*/
            "CHECK_OPTION", "IS_UPDATABLE", "DEFINER", "SECURITY_TYPE", "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION"};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(VIEW_DEFINITION);
        listOfAttributes.add(TABLE_SCHEMA);

        return (List<String>) listOfAttributes;
    }
}
