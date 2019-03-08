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
 * The class-loader of the stored procedures.
 */
@LoaderAnnotation(NameConstants.STORED_PROCEDURE)
public class StoredProcedureLoader extends AbstractLoader {

    /*public StoredProcedureLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        try {
            String procedureName = (String) storedProcedureContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String schemaName = (String) storedProcedureContainer.getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERLAZY, schemaName, procedureName);
            super.executeLazyLoaderQuery(storedProcedureContainer, query, NameConstants.PROCEDURE_FUNCTION_PARAMETER,
                FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        try {
            String storedProcedureName = (String) storedProcedureContainer.getAttributes()
                .get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String schemaName = (String) storedProcedureContainer.getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(MySQLQueries.STOREDPROCEDUREDETAILED,
                listRepresentationOfAttributes, schemaName, storedProcedureName);
            super.executeDetailedLoaderQuery(storedProcedureContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(storedProcedureContainer);
        this.detailedLoad(storedProcedureContainer);
        if (storedProcedureContainer.hasChildren()) {
            List<Container> storedProcedureParameters = storedProcedureContainer.getChildren();
            for (Container storedProcedureParameter : storedProcedureParameters) {
                ProcedureFunctionParametersLoader procedureFunctionParametersLoader = new ProcedureFunctionParametersLoader();
                procedureFunctionParametersLoader.setConnection(super.getConnection());
                procedureFunctionParametersLoader.fullLoad(storedProcedureParameter);
            }
        }
    }
}
