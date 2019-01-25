package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.delete.SchemaCategoriesTagNameConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.logging.Level;

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
        tablesCategory.setName(LoaderPrinterName.VIEWS);
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
            /*
            if (schemaContainer.getName() == null || schemaContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The schema container does not contain the schema name");
            }
            String informationSchemataSelectAllQuery =
                String.format(MySqlQueriesConstants.IinformationSchemaSelectAll.getQuery(), connection.getCatalog());
            PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
            ResultSet schemaAttributes = preparedStatement.executeQuery();

            if (schemaAttributes.next()) {
                for (SchemaAttributes attributeKey : SchemaAttributes.values()) {
                    schemaContainer.addAttribute(attributeKey.getElement(), schemaAttributes.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container schemaContainer) throws ContainerException, DatabaseException {
        lazyLoad(schemaContainer);
        detailedLoad(schemaContainer);
        new TableLoader().fullLoad(schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()));
        new ViewLoader().fullLoad(schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Views.getElement()));
        new StoredProcedureLoader().fullLoad(schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Stored_Procedures.getElement()));
        new FunctionLoader().fullLoad(schemaContainer.getChildByName(SchemaCategoriesTagNameConstants.Functions.getElement()));
    }
}