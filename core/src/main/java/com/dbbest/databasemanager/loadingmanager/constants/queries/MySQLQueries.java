package com.dbbest.databasemanager.loadingmanager.constants.queries;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;

import java.util.HashMap;
import java.util.Map;

public class MySQLQueries {
    public static final String TABLELAZY = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' ;";
    public static final String TABLEDETAILED = "SELECT %s FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '%s' AND table_name = '%s' ;";
    public static final String FUNCTIONLAZY = "SELECT SPECIFIC_NAME  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' ;";
    public static final String FUNCTIONDETAILED = "SELECT %s  FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA = '%s' AND ROUTINE_TYPE = 'FUNCTION' AND  ROUTINE_NAME = '%s' ;";

    public static final Map<String, String> SQL_QUERIES_LAZY_LOADER;

    static {
        SQL_QUERIES_LAZY_LOADER = new HashMap();
        SQL_QUERIES_LAZY_LOADER.put(LoaderPrinterName.TABLE, TABLELAZY);
        SQL_QUERIES_LAZY_LOADER.put(LoaderPrinterName.FUNCTION, FUNCTIONLAZY);
    }

    public static final Map<String, String> SQL_QUERIES_DETAIL_LOADER;

    static {
        SQL_QUERIES_DETAIL_LOADER = new HashMap();
        SQL_QUERIES_DETAIL_LOADER.put(LoaderPrinterName.TABLE, TABLEDETAILED);
        SQL_QUERIES_DETAIL_LOADER.put(LoaderPrinterName.FUNCTION, FUNCTIONDETAILED);
    }
}
