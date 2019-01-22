package com.dbbest.databasemanager.loadingmanager.constants.attributes;

public enum TriggerAttributes {

    TRIGGER_CATALOG("TRIGGER_CATALOG"), TRIGGER_SCHEMA("TRIGGER_SCHEMA"), TRIGGER_NAME("TRIGGER_NAME"),
    EVENT_MANIPULATION("EVENT_MANIPULATION"), EVENT_OBJECT_CATALOG("EVENT_OBJECT_CATALOG"),
    EVENT_OBJECT_SCHEMA("EVENT_OBJECT_SCHEMA"), EVENT_OBJECT_TABLE("EVENT_OBJECT_TABLE"),
    ACTION_ORDER("ACTION_ORDER"), ACTION_CONDITION("ACTION_CONDITION"), ACTION_STATEMENT("ACTION_STATEMENT"),
    ACTION_ORIENTATION("ACTION_ORIENTATION"), ACTION_TIMING("ACTION_TIMING"), ACTION_REFERENCE_OLD_TABLE("ACTION_REFERENCE_OLD_TABLE"),
    ACTION_REFERENCE_NEW_TABLE("ACTION_REFERENCE_NEW_TABLE"), ACTION_REFERENCE_OLD_ROW("ACTION_REFERENCE_OLD_ROW"),
    ACTION_REFERENCE_NEW_ROW("ACTION_REFERENCE_NEW_ROW"), CREATED("CREATED"), SQL_MODE("SQL_MODE"), DEFINER("DEFINER"),
    CHARACTER_SET_CLIENT("CHARACTER_SET_CLIENT"), COLLATION_CONNECTION("COLLATION_CONNECTION"),
    DATABASE_COLLATION("DATABASE_COLLATION");

    private String element;

    TriggerAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}