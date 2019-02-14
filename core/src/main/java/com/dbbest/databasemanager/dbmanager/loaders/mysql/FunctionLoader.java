package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionProcedureParameterAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the functions.
 */
@LoaderAnnotation(NameConstants.FUNCTION)
public class FunctionLoader extends AbstractLoader {
    public FunctionLoader(Connection connection) {
        super(connection);
    }

    @Override
    public void lazyLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            String functionName = (String) functionContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String schemaName = (String) functionContainer.getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERLAZY, schemaName, functionName);
            super.executeLazyLoaderQuery(functionContainer, query, NameConstants.PROCEDURE_FUNCTION_PARAMETER,
                FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container functionContainer) throws DatabaseException, ContainerException {
        try {
            String functionName = (String) functionContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) functionContainer.getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.FUNCTIONDETAILED, listRepresentationOfAttributes,
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
                new ProcedureFunctionParametersLoader(super.getConnection()).fullLoad(functionParameter);
            }
        }
    }
}
