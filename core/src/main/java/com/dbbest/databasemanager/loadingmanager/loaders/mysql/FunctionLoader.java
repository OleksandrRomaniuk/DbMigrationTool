package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the functions.
 */
@LoaderAnnotation(LoaderPrinterName.FUNCTION)
public class FunctionLoader extends AbstractLoader {
    public FunctionLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {
        try {
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.FUNCTION);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName());
            this.executeLazyLoaderQuery(functionCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) functionContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.FUNCTION);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listRepresentationOfAttributes, Context.getInstance().getSchemaName(), elementName);
            this.executeDetailedLoaderQuery(functionContainer, query);
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
