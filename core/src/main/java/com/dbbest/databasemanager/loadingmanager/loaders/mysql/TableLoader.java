package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
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
            super.executeLazyLoadSchemaChildren(categoryTablesContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }

        if (categoryTablesContainer.getChildren().size() > 0) {
            for (Container tableContainer : (List<Container>) categoryTablesContainer.getChildren()) {
                Container columnsCategory = new Container();
                columnsCategory.setName(LoaderPrinterName.TABLE_COLUMNS);
                columnsCategory.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.TABLE_COLUMNS);
                tableContainer.addChild(columnsCategory);

                Container indexCategory = new Container();
                indexCategory.setName(LoaderPrinterName.TABLE_INDEXES);
                indexCategory.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.TABLE_INDEXES);
                tableContainer.addChild(indexCategory);

                Container fkCategory = new Container();
                fkCategory.setName(LoaderPrinterName.TABLE_FOREIGN_KEYS);
                fkCategory.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.TABLE_FOREIGN_KEYS);
                tableContainer.addChild(fkCategory);

                Container triggerCategory = new Container();
                triggerCategory.setName(LoaderPrinterName.TABLE_TRIGGERS);
                triggerCategory.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.TABLE_TRIGGERS);
                tableContainer.addChild(triggerCategory);

                Container constraintCategory = new Container();
                constraintCategory.setName(LoaderPrinterName.TABLE_CONSTRAINTS);
                constraintCategory.addAttribute(AttributeSingleConstants.ROUTINE_ID, LoaderPrinterName.TABLE_CONSTRAINTS);
                tableContainer.addChild(constraintCategory);
            }
        }
    }

    @Override
    public void detailedLoad(Container tableContainer) throws DatabaseException {
        try {
            super.executeDetailedLoadSchemaChildren(tableContainer);
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
