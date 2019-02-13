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
    public static final String VIEWDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
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
    private Map<String, String> sqlQueriesLazyLoader = new HashMap();
    private Map<String, String> sqlQueriesDetailLoader = new HashMap();
    private static MySQLQueries instance;

    private MySQLQueries() {
    }
/*
    private void addSqlLazyQueries() {
        sqlQueriesLazyLoader.put(LoaderPrinterName.TABLE, TABLELAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.FUNCTION, FUNCTIONLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.STORED_PROCEDURE, STOREDPROCEDURELAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.VIEW, VIEWLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.TABLE_COLUMN, COLUMNLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.INDEX, INDEXLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.FOREIGN_KEY, FOREIGNKEYLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.TRIGGER, TRIGGERLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.CONSTRAINT, TABLECONSTRAINTLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, PROCEDUREFUNCTIONPARAMETERLAZY);
        sqlQueriesLazyLoader.put(LoaderPrinterName.VIEW_COLUMN, COLUMNLAZY);
    }

    private void addSqlDetailedQueries() {
        sqlQueriesDetailLoader.put(LoaderPrinterName.TABLE, TABLEDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.FUNCTION, FUNCTIONDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.STORED_PROCEDURE, STOREDPROCEDUREDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.SCHEMA, SCHEMADETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.VIEW, VIEWDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.TABLE_COLUMN, COLUMNDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.INDEX, INDEXDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.FOREIGN_KEY, FOREIGNKEYDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.TRIGGER, TRIGGERDEATILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.CONSTRAINT, TABLECONSTRAINTDETAIED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, PROCEDUREFUNCTIONPARAMETERDETAILED);
        sqlQueriesDetailLoader.put(LoaderPrinterName.VIEW_COLUMN, COLUMNDETAILED);
    }
*/
    /**
     * @return returns the map of the mysql queries for lazy load.
     */
    /*
    public Map<String, String> getSqlQueriesLazyLoader() {
        if (sqlQueriesLazyLoader.isEmpty()) {
            addSqlLazyQueries();
        }
        return sqlQueriesLazyLoader;
    }
*/
    /**
     * @return returns the map of the mysql queries for detailed load.
     */
    /*
    public Map<String, String> getSqlQueriesDetailLoader() {
        if (sqlQueriesDetailLoader.isEmpty()) {
            addSqlDetailedQueries();
        }
        return sqlQueriesDetailLoader;
    }
    */
}
