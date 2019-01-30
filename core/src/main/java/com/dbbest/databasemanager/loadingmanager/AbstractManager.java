package com.dbbest.databasemanager.loadingmanager;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.DatabaseException;

import java.util.logging.Level;

public abstract class AbstractManager {

    protected String getLazyOrDetailedLoaderMatching(String containerName) throws DatabaseException {
        switch (containerName) {
            case LoaderPrinterName.SCHEMA:
                return LoaderPrinterName.SCHEMA;
            case LoaderPrinterName.TABLES:
                return LoaderPrinterName.TABLE;
            case LoaderPrinterName.VIEWS:
                return LoaderPrinterName.VIEW;
            case LoaderPrinterName.STORED_PROCEDURES:
                return LoaderPrinterName.STORED_PROCEDURE;
            case LoaderPrinterName.FUNCTIONS:
                return LoaderPrinterName.FUNCTION;
            case LoaderPrinterName.TABLE_COLUMNS:
                return LoaderPrinterName.TABLE_COLUMN;
            case LoaderPrinterName.TABLE_INDEXES:
                return LoaderPrinterName.INDEX;
            case LoaderPrinterName.TABLE_FOREIGN_KEYS:
                return LoaderPrinterName.FOREIGN_KEY;
            case LoaderPrinterName.TABLE_TRIGGERS:
                return LoaderPrinterName.TRIGGER;
            case LoaderPrinterName.TABLE_CONSTRAINTS:
                return LoaderPrinterName.CONSTRAINT;
            case LoaderPrinterName.VIEW:
                return LoaderPrinterName.VIEW_COLUMN;
            default:
                throw new DatabaseException(Level.SEVERE,
                    "The manager can not find a respective loader or printer matching for the container " + containerName);
        }
    }
}
