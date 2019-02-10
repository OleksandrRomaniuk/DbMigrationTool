package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the schema.
 */
@LoaderAnnotation(LoaderPrinterName.SCHEMA)
public class SchemaLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container schemaContainer) throws ContainerException {
        if (!schemaContainer.hasName()) {
            schemaContainer.setName(LoaderPrinterName.SCHEMA);
        }

        schemaContainer.addAttribute(SchemaAttributes.SCHEMA_NAME, Context.getInstance().getSchemaName());
        schemaContainer.addAttribute(CustomAttributes.ROUTINE_ID,
            LoaderPrinterName.SCHEMA);

        Container tablesCategory = new Container();
        tablesCategory.setName(LoaderPrinterName.TABLES);
        tablesCategory.addAttribute(CustomAttributes.ROUTINE_ID,
            LoaderPrinterName.TABLES);
        schemaContainer.addChild(tablesCategory);

        Container viewsCategory = new Container();
        viewsCategory.setName(LoaderPrinterName.VIEWS);
        viewsCategory.addAttribute(CustomAttributes.ROUTINE_ID,
            LoaderPrinterName.VIEWS);
        schemaContainer.addChild(viewsCategory);

        Container functionsCategory = new Container();
        functionsCategory.setName(LoaderPrinterName.FUNCTIONS);
        functionsCategory.addAttribute(CustomAttributes.ROUTINE_ID,
            LoaderPrinterName.FUNCTIONS);
        schemaContainer.addChild(functionsCategory);

        Container storedProceduresCategory = new Container();
        storedProceduresCategory.setName(LoaderPrinterName.STORED_PROCEDURES);
        storedProceduresCategory.addAttribute(CustomAttributes.ROUTINE_ID,
            LoaderPrinterName.STORED_PROCEDURES);
        schemaContainer.addChild(storedProceduresCategory);
    }

    @Override
    public void detailedLoad(Container schemaContainer) throws DatabaseException {
        String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.SCHEMA);
        List<String> attributeNames = MySQLAttributeFactory.getInstance().getAttributes(this);
        String listOfAttributes = super.getListOfAttributes(attributeNames);

        try {
            String query = String.format(detailedLoaderQuery, listOfAttributes, Context.getInstance().getSchemaName());
            super.executeDetailedLoaderQuery(schemaContainer, query);
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
