package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the schema.
 */
@LoaderAnnotation(LoaderPrinterName.SCHEMA)
public class SchemaLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container schemaContainer) throws DatabaseException, ContainerException {
        if (!schemaContainer.hasName()) {
            schemaContainer.setName(LoaderPrinterName.SCHEMA);
        }

        schemaContainer.addAttribute(AttributeSingleConstants.SCHEMA_NAME, Context.getInstance().getSchemaName());

        Container tablesCategory = new Container();
        tablesCategory.setName(LoaderPrinterName.TABLES);
        schemaContainer.addChild(tablesCategory);

        Container viewsCategory = new Container();
        viewsCategory.setName(LoaderPrinterName.VIEWS);
        schemaContainer.addChild(viewsCategory);

        Container functionsCategory = new Container();
        functionsCategory.setName(LoaderPrinterName.FUNCTIONS);
        schemaContainer.addChild(functionsCategory);

        Container storedProceduresCategory = new Container();
        storedProceduresCategory.setName(LoaderPrinterName.STORED_PROCEDURES);
        schemaContainer.addChild(storedProceduresCategory);
    }

    @Override
    public void detailedLoad(Container schemaContainer) throws DatabaseException, ContainerException {

        try {
            super.executeSchemaDetailedLoad(schemaContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        lazyLoad(schemaContainer);
        detailedLoad(schemaContainer);
        new TableLoader().fullLoad(schemaContainer.getChildByName(LoaderPrinterName.TABLES));
        new ViewLoader().fullLoad(schemaContainer.getChildByName(LoaderPrinterName.VIEWS));
        new StoredProcedureLoader().fullLoad(schemaContainer.getChildByName(LoaderPrinterName.STORED_PROCEDURES));
        new FunctionLoader().fullLoad(schemaContainer.getChildByName(LoaderPrinterName.FUNCTIONS));
    }
}
