package com.dbbest.databasemanager.dbmanager.printers;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

/**
 * The interface for the printer.
 */
public interface Printer {
    String execute(Container tree) throws ContainerException;
}
