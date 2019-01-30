package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.FUNCTION)
public class FunctionLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadSchemaChildren(functionCategoryContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadSchemaChildren(functionContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        new ProcedureFunctionParameteresLoader().fullLoad(functionContainer);
    }

    @Override
    public void fullLoad(Container functionsCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(functionsCategoryContainer);
        List<Container> functions = functionsCategoryContainer.getChildren();
        if (functions != null && !functions.isEmpty()) {
            for (Container function : functions) {
                detailedLoad(function);
            }
        }
    }
}
