package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the stored procedures.
 */
@LoaderAnnotation(LoaderPrinterName.STORED_PROCEDURE)
public class StoredProcedureLoader extends AbstractLoader {

    public StoredProcedureLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        try {
            String procedureName = (String) storedProcedureContainer
                .getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME);
            String schemaName = (String) storedProcedureContainer.getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.PROCEDUREFUNCTIONPARAMETERLAZY, schemaName, procedureName);
            super.executeLazyLoaderQuery(storedProcedureContainer, query, LoaderPrinterName.PROCEDURE_FUNCTION_PARAMETER);
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
                new ProcedureFunctionParametersLoader(super.getContext()).fullLoad(storedProcedureParameter);
            }
        }
    }
}
