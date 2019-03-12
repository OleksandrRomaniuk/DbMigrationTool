package com.dbbest.services;

import com.dbbest.consolexmlmanager.TreeNavigator;
import com.dbbest.databasemanager.dbmanager.PrinterManager;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.stereotype.Service;

@Service
public class PrintServiceImpl implements PrintService {
    @Override
    public String print(Container root, String dbType, String fullPath) throws DatabaseException, ContainerException {

        Container targetContainer = new TreeNavigator(root).getTargetContainer(this.getFullPath(fullPath));
        PrinterManager printerManager = new PrinterManager(dbType);
        return printerManager.print(targetContainer);
    }

    private String getFullPath(String fullPath) {
        return new StringBuilder(fullPath).deleteCharAt(0).toString().replace("/", ".");
    }
}
