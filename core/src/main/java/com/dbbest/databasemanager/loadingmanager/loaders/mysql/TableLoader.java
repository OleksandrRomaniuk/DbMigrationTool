package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the tables.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE)
public class TableLoader extends AbstractLoader {

    public TableLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container tableContainer) throws DatabaseException, ContainerException {
        this.lazyLoadCategory(tableContainer, LoaderPrinterName.TABLE_COLUMNS,
            new TableColumnCategoryLoader(super.getContext()), LoaderPrinterName.TABLE_COLUMN);
        this.lazyLoadCategory(tableContainer, LoaderPrinterName.TABLE_INDEXES,
            new IndexCategoryLoader(super.getContext()), LoaderPrinterName.INDEX);
        this.lazyLoadCategory(tableContainer, LoaderPrinterName.TABLE_FOREIGN_KEYS,
            new ForeignKeyCategoryLoader(super.getContext()), LoaderPrinterName.FOREIGN_KEY);
        this.lazyLoadCategory(tableContainer, LoaderPrinterName.TABLE_TRIGGERS,
            new TriggerCategoryLoader(super.getContext()), LoaderPrinterName.TRIGGER);
        this.lazyLoadCategory(tableContainer, LoaderPrinterName.TABLE_CONSTRAINTS,
            new ConstraintCategoryLoader(super.getContext()), LoaderPrinterName.CONSTRAINT);
    }

    @Override
    public void detailedLoad(Container tableContainer) throws DatabaseException {
        try {
            String tableName = (String) tableContainer.getAttributes().get(TableAttributes.TABLE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(MySQLQueries.TABLEDETAILED, listRepresentationOfAttributes,
                tableContainer.getAttributes().get(TableAttributes.TABLE_SCHEMA), tableName);
            super.executeDetailedLoaderQuery(tableContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container tableContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(tableContainer);
        this.detailedLoad(tableContainer);
        System.out.println(tableContainer.hasChildren());
        new TableColumnCategoryLoader(super.getContext()).fullLoad(tableContainer.getChildByName(LoaderPrinterName.TABLE_COLUMNS));
        new IndexCategoryLoader(super.getContext()).fullLoad(tableContainer.getChildByName(LoaderPrinterName.TABLE_INDEXES));
        new ForeignKeyCategoryLoader(super.getContext())
            .fullLoad(tableContainer.getChildByName(LoaderPrinterName.TABLE_FOREIGN_KEYS));
        new TriggerCategoryLoader(super.getContext()).fullLoad(tableContainer.getChildByName(LoaderPrinterName.TABLE_TRIGGERS));
        new ConstraintLoader(super.getContext()).fullLoad(tableContainer.getChildByName(LoaderPrinterName.TABLE_CONSTRAINTS));
    }

    private void lazyLoadCategory(Container tableContainer, String categoryName, Loader categoryLoader, String childType)
        throws ContainerException, DatabaseException {
        Container category = new Container();
        category.setName(categoryName);
        category.addAttribute(CustomAttributes.IS_CATEGORY, true);
        category.addAttribute(CustomAttributes.CHILD_TYPE, childType);
        tableContainer.addChild(category);
        categoryLoader.lazyLoad(category);
    }
}
