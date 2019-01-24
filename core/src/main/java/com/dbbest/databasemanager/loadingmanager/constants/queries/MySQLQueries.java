package com.dbbest.databasemanager.loadingmanager.constants.queries;

public class MySQLQueries {
    public static final String TABLELAZY = "SELECT '%s' FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' ;";
    public static final String TABLEDETAILED = "SELECT '%s' FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' AND table_name = '%s' ;";
}
