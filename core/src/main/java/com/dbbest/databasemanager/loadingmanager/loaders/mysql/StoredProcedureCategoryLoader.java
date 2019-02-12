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
    public void lazyLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        if (!storedProcedureCategoryContainer.hasName()) {
            storedProcedureCategoryContainer.setName(LoaderPrinterName.TABLES);
        }
        storedProcedureCategoryContainer.addAttribute(CustomAttributes.IS_CATEGORY, true);
        storedProcedureCategoryContainer.addAttribute(CustomAttributes.CHILD_TYPE, LoaderPrinterName.STORED_PROCEDURE);
        try {
            String query = String.format(MySQLQueries.STOREDPROCEDURELAZY,
                storedProcedureCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(storedProcedureCategoryContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        if (storedProcedureCategoryContainer.hasChildren()) {
            for (Container table : (List<Container>) storedProcedureCategoryContainer.getChildren()) {
                new StoredProcedureLoader(super.getContext()).detailedLoad(table);
            }
        }
    }

    @Override
    public void fullLoad(Container storedProcedureCategoryContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(storedProcedureCategoryContainer);
        if (storedProcedureCategoryContainer.hasChildren()) {
            for (Container storedProcedure : (List<Container>) storedProcedureCategoryContainer.getChildren()) {
                new StoredProcedureLoader(super.getContext()).fullLoad(storedProcedure);
            }
        }
    }
}
