package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;

public interface Printer {
    String execute(Container tree) throws ContainerException;
}
