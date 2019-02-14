package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the view category.
 */
@LoaderAnnotation(LoaderPrinterName.VIEWS)
public class ViewCategoryLoader extends AbstractLoader {
    public ViewCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.VIEWLAZY,
                viewCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(viewCategoryContainer, query, LoaderPrinterName.VIEW, ViewAttributes.TABLE_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        if (viewCategoryContainer.hasChildren()) {
            for (Container view : (List<Container>) viewCategoryContainer.getChildren()) {
                new ViewLoader(super.getContext()).detailedLoad(view);
            }
        }
    }

    @Override
    public void fullLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        if (viewCategoryContainer.hasChildren()) {
            for (Container view : (List<Container>) viewCategoryContainer.getChildren()) {
                new ViewLoader(super.getContext()).fullLoad(view);
            }
        }
    }
}
