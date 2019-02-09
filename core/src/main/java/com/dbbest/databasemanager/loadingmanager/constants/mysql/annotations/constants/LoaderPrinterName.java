package com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class with constants defining names of the loaded and printed containers.
 */
public final class LoaderPrinterName {
    public static final String SCHEMA = "Schema";
    public static final String VIEWS = "Views";
    public static final String VIEW = "View";
    public static final String VIEW_COLUMN = "ViewColumn";
    public static final String STORED_PROCEDURES = "StoredProcedures";
    public static final String STORED_PROCEDURE = "StoredProcedure";
    public static final String PROCEDURE_FUNCTION_PARAMETER = "ProcedureFunctionparameter";
    public static final String FUNCTIONS = "Functions";
    public static final String FUNCTION = "Function";
    public static final String TABLES = "Tables";
    public static final String TABLE = "Table";
    public static final String TABLE_COLUMNS = "Columns";
    public static final String TABLE_COLUMN = "TableColumn";
    public static final String TABLE_INDEXES = "Indexes";
    public static final String INDEX = "Index";
    public static final String TABLE_TRIGGERS = "Triggers";
    public static final String TRIGGER = "Trigger";
    public static final String TABLE_FOREIGN_KEYS = "ForeignKeys";
    public static final String FOREIGN_KEY = "ForeignKey";
    public static final String TABLE_CONSTRAINTS = "Constraints";
    public static final String CONSTRAINT = "Constraint";

    private static LoaderPrinterName instance;
    private List<String> loaders;
    private List<String> printers;
    private Map<String, String> lazyLoaderMatching;

    private LoaderPrinterName() {
    }

    /**
     * @return returns the instance of the class.
     */
    public static LoaderPrinterName getInstance() {
        if (instance == null) {
            instance = new LoaderPrinterName();
        }
        return instance;
    }

    /**
     * @return returns the list of name of the containers to be loaded by the loader manager.
     */
    public List<String> getListOfLoaders() {
        if (loaders == null || loaders.isEmpty()) {
            intializeListOfLoaders();
        }
        return loaders;
    }

    /**
     * @return returns the list of name of the containers to be printed by the printer manager.
     */
    public List<String> getListOfPrinters() {
        if (printers == null || printers.isEmpty()) {
            intializeListOfPrinters();
        }
        return printers;
    }

    public Map<String, String> getLazyLoaderName() {
        if (lazyLoaderMatching == null || lazyLoaderMatching.isEmpty()) {
            initializeLazyLoaderMatching();
        }
        return lazyLoaderMatching;
    }

    private void intializeListOfLoaders() {
        loaders = new ArrayList();
        loaders.add(SCHEMA);
        loaders.add(FOREIGN_KEY);
        loaders.add(FUNCTION);
        loaders.add(INDEX);
        loaders.add(STORED_PROCEDURE);
        loaders.add(TABLE_COLUMN);
        loaders.add(TABLE);
        loaders.add(TRIGGER);
        loaders.add(VIEW_COLUMN);
        loaders.add(VIEW);
        loaders.add(CONSTRAINT);
        loaders.add(PROCEDURE_FUNCTION_PARAMETER);
    }

    private void intializeListOfPrinters() {
        printers = new ArrayList();
        printers.add(SCHEMA);
        printers.add(TABLE);
        printers.add(VIEW);
        printers.add(STORED_PROCEDURE);
        printers.add(FUNCTION);
        printers.add(TRIGGER);
        printers.add(INDEX);
    }

    private void initializeLazyLoaderMatching() {
        lazyLoaderMatching = new HashMap();
        lazyLoaderMatching.put(LoaderPrinterName.SCHEMA, LoaderPrinterName.SCHEMA);
        lazyLoaderMatching.put(LoaderPrinterName.TABLES, LoaderPrinterName.TABLE);
        lazyLoaderMatching.put(LoaderPrinterName.VIEWS, LoaderPrinterName.VIEW);
        lazyLoaderMatching.put(LoaderPrinterName.STORED_PROCEDURES, LoaderPrinterName.STORED_PROCEDURE);
        lazyLoaderMatching.put(LoaderPrinterName.FUNCTIONS, LoaderPrinterName.FUNCTION);
        lazyLoaderMatching.put(LoaderPrinterName.TABLE_COLUMNS, LoaderPrinterName.TABLE_COLUMN);
        lazyLoaderMatching.put(LoaderPrinterName.TABLE_INDEXES, LoaderPrinterName.INDEX);
        lazyLoaderMatching.put(LoaderPrinterName.TABLE_FOREIGN_KEYS, LoaderPrinterName.FOREIGN_KEY);
        lazyLoaderMatching.put(LoaderPrinterName.TABLE_TRIGGERS, LoaderPrinterName.TRIGGER);
        lazyLoaderMatching.put(LoaderPrinterName.TABLE_CONSTRAINTS, LoaderPrinterName.CONSTRAINT);
        lazyLoaderMatching.put(LoaderPrinterName.VIEW, LoaderPrinterName.VIEW_COLUMN);
    }
}
