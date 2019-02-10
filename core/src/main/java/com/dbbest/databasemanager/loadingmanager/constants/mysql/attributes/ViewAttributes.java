package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the view node.
 */
public class ViewAttributes implements Attributes {

    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String VIEW_DEFINITION = "VIEW_DEFINITION";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfViewAttributes();
    }

    private List<String> getListOfViewAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", /*"VIEW_DEFINITION",*/
            "CHECK_OPTION", "IS_UPDATABLE", "DEFINER", "SECURITY_TYPE", "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION"};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(VIEW_DEFINITION);

        return (List<String>) listOfAttributes;
    }
}
