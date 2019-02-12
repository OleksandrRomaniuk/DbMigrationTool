package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class FunctionCategoryLoader extends AbstractLoader {
    public FunctionCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        if (!functionCategoryContainer.hasName()) {
            functionCategoryContainer.setName(LoaderPrinterName.FUNCTIONS);
        }
        functionCategoryContainer.addAttribute(CustomAttributes.IS_CATEGORY, true);
        functionCategoryContainer.addAttribute(CustomAttributes.CHILD_TYPE, LoaderPrinterName.FUNCTION);
        try {
            String query = String.format(MySQLQueries.FUNCTIONLAZY,
                functionCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(functionCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        if (functionCategoryContainer.getAttributes().get(CustomAttributes.CHILD_TYPE).equals(LoaderPrinterName.FUNCTION)
            && functionCategoryContainer.hasChildren()) {
            for (Container function : (List<Container>) functionCategoryContainer.getChildren()) {
                new FunctionLoader(super.getContext()).detailedLoad(function);
            }
        }
    }

    @Override
    public void fullLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(functionCategoryContainer);
        if (functionCategoryContainer.hasChildren()) {
            for (Container function : (List<Container>) functionCategoryContainer.getChildren()) {
                new FunctionLoader(super.getContext()).fullLoad(function);
            }
        }
    }
}
