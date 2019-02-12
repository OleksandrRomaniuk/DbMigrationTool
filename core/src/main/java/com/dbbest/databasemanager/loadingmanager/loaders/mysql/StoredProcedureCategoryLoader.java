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

public class StoredProcedureCategoryLoader extends AbstractLoader {
    public StoredProcedureCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        if (!storedProcedureContainer.hasName()) {
            storedProcedureContainer.setName(LoaderPrinterName.TABLES);
        }
        if (!storedProcedureContainer.getAttributes().containsKey(CustomAttributes.IS_CATEGORY)) {
            storedProcedureContainer.addAttribute(CustomAttributes.IS_CATEGORY, true);
        }
        if (!storedProcedureContainer.getAttributes().containsKey(CustomAttributes.CHILD_TYPE)) {
            storedProcedureContainer.addAttribute(CustomAttributes.CHILD_TYPE, LoaderPrinterName.STORED_PROCEDURE);
        }
        try {
            String query = String.format(MySQLQueries.STOREDPROCEDURELAZY,
                storedProcedureContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(storedProcedureContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        if (storedProcedureContainer.getAttributes().get(CustomAttributes.CHILD_TYPE).equals(LoaderPrinterName.STORED_PROCEDURE)
            && storedProcedureContainer.hasChildren()) {
            for (Container table : (List<Container>)storedProcedureContainer.getChildren()) {
                new TableLoader().detailedLoad(table);
            }
        }
    }

    @Override
    public void fullLoad(Container storedProcedureContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(storedProcedureContainer);
        if (storedProcedureContainer.hasChildren()) {
            for (Container storedProcedure : (List<Container>)storedProcedureContainer.getChildren()) {
                new StoredProcedureLoader().fullLoad(storedProcedure);
            }
        }
    }
}
