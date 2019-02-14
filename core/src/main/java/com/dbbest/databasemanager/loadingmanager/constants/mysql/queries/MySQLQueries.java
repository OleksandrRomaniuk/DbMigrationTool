package com.dbbest.databasemanager.loadingmanager.constants.mysql.queries;

import java.util.HashMap;
import java.util.Map;

/**
 * The class with the constants of mysql select queries for detailed and lazy loaders.
 */
public final class MySQLQueries {

    public static final String SCHEMADETAILED = "SELECT %s FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s' ;";
    public static final String TABLELAZY = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' ;";
    public static final String TABLEDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.TABLES "
        + "WHERE table_schema = '%s' AND table_name = '%s' ;";
    public static final String FUNCTIONLAZY = "SELECT SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' ;";
    public static final String FUNCTIONDETAILED = "SELECT %s  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' AND SPECIFIC_NAME = '%s' ;";
    public static final String STOREDPROCEDURELAZY = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'PROCEDURE' ;";
    public static final String STOREDPROCEDUREDETAILED = "SELECT %s  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'PROCEDURE' AND SPECIFIC_NAME = '%s' ;";
    public static final String VIEWLAZY = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' ;";
    public static final String VIEWDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' "
        + "AND TABLE_NAME = '%s' ;";
    public static final String COLUMNLAZY = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS "
        + "WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' ;";
    public static final String COLUMNDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.COLUMNS "
        + "WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' AND  COLUMN_NAME = '%s' ;";
    public static final String INDEXLAZY = "SELECT DISTINCT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS "
        + "WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
    public static final String INDEXDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.STATISTICS "
        + "WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'  AND INDEX_NAME =  '%s' ;";
    public static final String FOREIGNKEYLAZY = "SELECT DISTINCT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS "
        + "WHERE information_schema.TABLE_CONSTRAINTS.CONSTRAINT_TYPE = 'FOREIGN KEY' "
        + "AND information_schema.TABLE_CONSTRAINTS.TABLE_SCHEMA = '%s' "
        + "AND information_schema.TABLE_CONSTRAINTS.TABLE_NAME = '%s' ;";
    //"SELECT DISTINCT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
    //+ "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
    public static final String FOREIGNKEYDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;";
    public static final String TRIGGERLAZY = "SELECT TRIGGER_NAME  FROM INFORMATION_SCHEMA.TRIGGERS "
        + "WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' ;";
    public static final String TRIGGERDEATILED = "SELECT %s FROM INFORMATION_SCHEMA.TRIGGERS "
        + "WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' AND  TRIGGER_NAME = '%s' ;";
    public static final String TABLECONSTRAINTLAZY = "SELECT %s FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND  TABLE_NAME = '%s' ;";
    public static final String TABLECONSTRAINTDETAIED = "SELECT %s FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;";
    public static final String PROCEDUREFUNCTIONPARAMETERLAZY = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS "
        + "WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' ;";
    public static final String PROCEDUREFUNCTIONPARAMETERDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.PARAMETERS "
        + "WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' AND PARAMETER_NAME = '%s' ;";

    private MySQLQueries() {
    }
}
