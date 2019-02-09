package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

public class TriggerAttributes implements Attributes {

    public static final String TRIGGER_NAME = "TRIGGER_NAME";
    public static final String EVENT_OBJECT_SCHEMA = "EVENT_OBJECT_SCHEMA";
    public static final String ACTION_TIMING = "ACTION_TIMING";
    public static final String EVENT_MANIPULATION = "EVENT_MANIPULATION";
    public static final String EVENT_OBJECT_TABLE = "EVENT_OBJECT_TABLE";
    public static final String ACTION_STATEMENT = "ACTION_STATEMENT";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfTriggerAttributes();
    }

    private List<String> getListOfTriggerAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"TRIGGER_CATALOG", "TRIGGER_SCHEMA",
            /*"EVENT_MANIPULATION",*/ "EVENT_OBJECT_CATALOG", /*"EVENT_OBJECT_SCHEMA",*/ /*"EVENT_OBJECT_TABLE",*/
            "ACTION_ORDER", "ACTION_CONDITION", /*"ACTION_STATEMENT",*/ "ACTION_ORIENTATION", /*"ACTION_TIMING",*/
            "ACTION_REFERENCE_OLD_TABLE", "ACTION_REFERENCE_NEW_TABLE", "ACTION_REFERENCE_OLD_ROW",
            "ACTION_REFERENCE_NEW_ROW", "CREATED", "SQL_MODE", "DEFINER", "CHARACTER_SET_CLIENT",
            "COLLATION_CONNECTION", "DATABASE_COLLATION"};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(EVENT_OBJECT_SCHEMA);
        listOfAttributes.add(ACTION_TIMING);
        listOfAttributes.add(EVENT_MANIPULATION);
        listOfAttributes.add(EVENT_OBJECT_TABLE);
        listOfAttributes.add(ACTION_STATEMENT);
        return (List<String>) listOfAttributes;
    }
}
