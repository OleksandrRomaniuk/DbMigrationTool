package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the tables.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE)
public class TableLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container categoryTablesContainer) throws DatabaseException, ContainerException {
        try {
            String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(LoaderPrinterName.TABLE);
            String query = String.format(lazyLoaderQuery, Context.getInstance().getSchemaName());
            super.executeLazyLoaderQuery(categoryTablesContainer, query);
            //super.executeLazyLoadSchemaChildren(categoryTablesContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }

        if (categoryTablesContainer.getChildren() != null && !categoryTablesContainer.getChildren().isEmpty()) {
            for (Container tableContainer : (List<Container>) categoryTablesContainer.getChildren()) {
                Container columnsCategory = new Container();
                columnsCategory.setName(LoaderPrinterName.TABLE_COLUMNS);
                columnsCategory.addAttribute(CustomAttributes.ROUTINE_ID, LoaderPrinterName.TABLE_COLUMNS);
                tableContainer.addChild(columnsCategory);

                Container indexCategory = new Container();
                indexCategory.setName(LoaderPrinterName.TABLE_INDEXES);
                indexCategory.addAttribute(CustomAttributes.ROUTINE_ID, LoaderPrinterName.TABLE_INDEXES);
                tableContainer.addChild(indexCategory);

                Container fkCategory = new Container();
                fkCategory.setName(LoaderPrinterName.TABLE_FOREIGN_KEYS);
                fkCategory.addAttribute(CustomAttributes.ROUTINE_ID, LoaderPrinterName.TABLE_FOREIGN_KEYS);
                tableContainer.addChild(fkCategory);

                Container triggerCategory = new Container();
                triggerCategory.setName(LoaderPrinterName.TABLE_TRIGGERS);
                triggerCategory.addAttribute(CustomAttributes.ROUTINE_ID, LoaderPrinterName.TABLE_TRIGGERS);
                tableContainer.addChild(triggerCategory);

                Container constraintCategory = new Container();
                constraintCategory.setName(LoaderPrinterName.TABLE_CONSTRAINTS);
                constraintCategory.addAttribute(CustomAttributes.ROUTINE_ID, LoaderPrinterName.TABLE_CONSTRAINTS);
                tableContainer.addChild(constraintCategory);
            }
        }
    }

    @Override
    public void detailedLoad(Container tableContainer) throws DatabaseException {
        try {
            String elementName = (String) tableContainer.getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(LoaderPrinterName.TABLE);
            String listOfAttributes = super.getListOfAttributes(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listOfAttributes, Context.getInstance().getSchemaName(), elementName);
            super.executeDetailedLoaderQuery(tableContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }


    }

    @Override
    public void fullLoad(Container categoryTablesContainer) throws DatabaseException, ContainerException {
        lazyLoad(categoryTablesContainer);
        List<Container> tables = categoryTablesContainer.getChildren();
        if (tables != null && !tables.isEmpty()) {
            for (Container table : tables) {
                detailedLoad(table);
                new TableColumnLoader().fullLoad(table.getChildByName(LoaderPrinterName.TABLE_COLUMNS));
                new IndexLoader().fullLoad(table.getChildByName(LoaderPrinterName.TABLE_INDEXES));
                new ForeignKeyLoader().fullLoad(table.getChildByName(LoaderPrinterName.TABLE_FOREIGN_KEYS));
                new TriggerLoader().fullLoad(table.getChildByName(LoaderPrinterName.TABLE_TRIGGERS));
                new ConstraintLoader().fullLoad(table.getChildByName(LoaderPrinterName.TABLE_CONSTRAINTS));
            }
        }
    }
}
