package com.dbbest.databasemanager.loadingmanager.constants;

public enum MySqlQueriesConstants {
    IinformationSchemaSelectAll("SELECT * FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s' ;"),
    TableInformationSchemaSelectAll("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' AND table_name = '%s' ;"),
    ColumnInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' AND  COLUMN_NAME = '%s' ;"),
    IndexInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'  AND INDEX_NAME =  '%s' ;"),
    ForeignKeyInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;"),
    TriggerInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' AND  TRIGGER_NAME = '%s' ;"),
    ViewInformationSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;"),
    StoredProceduresAndFunctionsSchemaSelectAll("SELECT *  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = '%s' AND  ROUTINE_NAME = '%s' ;"),
    TriggerInformationSchemaGetListOfTriggers("SELECT TRIGGER_NAME  FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' ;"),
    IndexesSelectAllName("SELECT DISTINCT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;"),
    ProcedureFunctionParametersGetListOfParameters("SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' ;"),
    ProcedureFunctionParametersSelectAll("SELECT * FROM INFORMATION_SCHEMA.PARAMETERS WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' AND PARAMETER_NAME = '%s' ;"),
    TableConstraintsSelectAll("SELECT *  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA = '%s' AND  TABLE_NAME = '%s' ;"),
    KeyColumnUSageConstarintsSelectAll("SELECT *  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;");

    private String query;

    MySqlQueriesConstants(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
