package com.dbbest.databasemanager.loadingmanager.constants;

public enum MySqlQueriesConstants {
    INFORMATIONSCHEMASELECTALL("SELECT * FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s' ;"),
    TableInformationSchemaSelectAll("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' AND table_name = '%s'");

    private String query;

    MySqlQueriesConstants(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
