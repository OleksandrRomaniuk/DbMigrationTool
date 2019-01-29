package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.CONSTRAINT)
public class ConstraintLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoadTableConstraint(constraintCategoryContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container constraintContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableConstraint(constraintContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(constraintCategoryContainer);

            List<Container> constraintContainers = constraintCategoryContainer.getChildren();
        if (constraintContainers != null && !constraintContainers.isEmpty()) {
            for (Container constraint : constraintContainers) {
                detailedLoad(constraint);
            }
        }
    }
}
