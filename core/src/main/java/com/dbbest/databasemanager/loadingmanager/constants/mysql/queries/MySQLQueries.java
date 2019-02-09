package com.dbbest.databasemanager.loadingmanager.constants.mysql.queries;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

/**
 * The class with the constants of mysql select queries for detailed and lazy loaders.
 */
public final class MySQLQueries {

    private final String schemaDetailed = "SELECT %s FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s' ;";
    private final String tableLazy = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' ;";
    private final String tableDetailed = "SELECT %s FROM INFORMATION_SCHEMA.TABLES "
        + "WHERE table_schema = '%s' AND table_name = '%s' ;";
    private final String functionLazy = "SELECT SPECIFIC_NAME FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' ;";
    private final String functionDetailed = "SELECT %s  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' AND SPECIFIC_NAME = '%s' ;";
    private final String storedProcedureLazy = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'PROCEDURE' ;";
    private final String storedProcedureDetailed = "SELECT %s  FROM INFORMATION_SCHEMA.ROUTINES "
        + "WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'PROCEDURE' AND SPECIFIC_NAME = '%s' ;";
    private final String viewLazy = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' ;";
    private final String viewDetailed = "SELECT %s FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
    private final String columnLazy = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS "
        + "WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' ;";
    private final String columnDetailed = "SELECT %s FROM INFORMATION_SCHEMA.COLUMNS "
        + "WHERE TABLE_SCHEMA= '%s' AND TABLE_NAME = '%s' AND  COLUMN_NAME = '%s' ;";
    private final String indexLazy = "SELECT DISTINCT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS "
        + "WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
    private final String indexDetailed = "SELECT %s FROM INFORMATION_SCHEMA.STATISTICS "
        + "WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'  AND INDEX_NAME =  '%s' ;";
    private final String foreignKeyLazy = "SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' ;";
    private final String foreignKeyDetailed = "SELECT %s FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;";
    private final String triggerLazy = "SELECT TRIGGER_NAME  FROM INFORMATION_SCHEMA.TRIGGERS "
        + "WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' ;";
    private final String triggerDeatiled = "SELECT %s FROM INFORMATION_SCHEMA.TRIGGERS "
        + "WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s' AND  TRIGGER_NAME = '%s' ;";
    private final String tableConstraintLazy = "SELECT %s FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND  TABLE_NAME = '%s' ;";
    private final String tableConstraintDetaied = "SELECT %s FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE "
        + "WHERE CONSTRAINT_SCHEMA = '%s' AND TABLE_NAME = '%s' AND  CONSTRAINT_NAME = '%s' ;";
    private final String procedureFunctionParameterLazy = "SELECT PARAMETER_NAME FROM INFORMATION_SCHEMA.PARAMETERS "
        + "WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' ;";
    private final String procedureFunctionParameterDetailed = "SELECT %s FROM INFORMATION_SCHEMA.PARAMETERS "
        + "WHERE SPECIFIC_SCHEMA = '%s' AND SPECIFIC_NAME = '%s' AND PARAMETER_NAME = '%s' ;";
    private Map<String, String> sqlQueriesLazyLoader = new HashMap();
    private Map<String, String> sqlQueriesDetailLoader = new HashMap();
    private static MySQLQueries instance;

    private MySQLQueries() {
    }

    /**
     * @return returns the instance of the class.
     */
    public static MySQLQueries getInstance() {
        if (instance == null) {
            instance = new MySQLQueries();
        }
        return instance;
    }

    private void addSqlLazyQueries() {
        sqlQueriesLazyLoader.put(LoaderPrinterName.TABLE, tableLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.FUNCTION, functionLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.STORED_PROCEDURE, storedProcedureLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.VIEW, viewLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.TABLE_COLUMN, columnLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.INDEX, indexLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.FOREIGN_KEY, foreignKeyLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.TRIGGER, triggerLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.CONSTRAINT, tableConstraintLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, procedureFunctionParameterLazy);
        sqlQueriesLazyLoader.put(LoaderPrinterName.VIEW_COLUMN, columnLazy);
    }

    private void addSqlDetailedQueries() {
        sqlQueriesDetailLoader.put(LoaderPrinterName.TABLE, tableDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.FUNCTION, functionDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.STORED_PROCEDURE, storedProcedureDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.SCHEMA, schemaDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.VIEW, viewDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.TABLE_COLUMN, columnDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.INDEX, indexDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.FOREIGN_KEY, foreignKeyDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.TRIGGER, triggerDeatiled);
        sqlQueriesDetailLoader.put(LoaderPrinterName.CONSTRAINT, tableConstraintDetaied);
        sqlQueriesDetailLoader.put(LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER, procedureFunctionParameterDetailed);
        sqlQueriesDetailLoader.put(LoaderPrinterName.VIEW_COLUMN, columnDetailed);
    }

    /**
     * @return returns the map of the mysql queries for lazy load.
     */
    public Map<String, String> getSqlQueriesLazyLoader() {
        if (sqlQueriesLazyLoader.isEmpty()) {
            addSqlLazyQueries();
        }
        return sqlQueriesLazyLoader;
    }

    /**
     * @return returns the map of the mysql queries for detailed load.
     */
    public Map<String, String> getSqlQueriesDetailLoader() {
        if (sqlQueriesDetailLoader.isEmpty()) {
            addSqlDetailedQueries();
        }
        return sqlQueriesDetailLoader;
    }
}
