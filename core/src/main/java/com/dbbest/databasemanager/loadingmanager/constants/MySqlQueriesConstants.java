package com.dbbest.databasemanager.loadingmanager.constants;

public enum MySqlQueriesConstants {
    INFORMATIONSCHEMASELECTALL("SELECT * FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s' ;"),
    TableInformationSchemaSelectAll("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' AND table_name = '%s'"),
    ColumnInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' AND  COLUMN_NAME = '%s' ;"),
    IndexInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'  AND INDEX_NAME =  '%s' ;"),
    ForeignKeyInformationSchemaSelectAll("");

    private String query;

    MySqlQueriesConstants(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
