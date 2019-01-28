package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.STORED_PROCEDURE)
public class StoredProcedureLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container storedProceduresCategoryContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadSchemaChildren(storedProceduresCategoryContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container storedProcedure) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadSchemaChildren(storedProcedure);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container storedProceduresCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(storedProceduresCategoryContainer);
        List<Container> storedProcedures = storedProceduresCategoryContainer.getChildren();
        if (storedProcedures != null && !storedProcedures.isEmpty()) {
            for (Container storedProcedure : storedProcedures) {
                detailedLoad(storedProcedure);
                new ProcedureFunctionParameteresLoader().fullLoad(storedProcedure);
            }
        }
    }
}
