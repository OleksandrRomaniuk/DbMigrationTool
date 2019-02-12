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
    public void lazyLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            String functionName = (String) functionContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String schemaName = (String) functionContainer.getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA);
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERLAZY, schemaName, functionName);
            super.executeLazyLoaderQuery(functionContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            String functionName = (String) functionContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) functionContainer.getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA);
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERDETAILED, listRepresentationOfAttributes,
                schemaName, functionName);
            this.executeDetailedLoaderQuery(functionContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container functionsCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(functionsCategoryContainer);
        this.detailedLoad(functionsCategoryContainer);
        if (functionsCategoryContainer.hasChildren()) {
            List<Container> functionParameters = functionsCategoryContainer.getChildren();
            for (Container functionParameter : functionParameters) {
                new ProcedureFunctionParametersLoader(super.getContext()).fullLoad(functionParameter);
            }
        }
    }
}
