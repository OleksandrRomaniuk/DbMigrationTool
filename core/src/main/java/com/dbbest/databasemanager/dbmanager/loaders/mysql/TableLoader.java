package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.databasemanager.dbmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the tables.
 */
@LoaderAnnotation(NameConstants.TABLE)
public class TableLoader extends AbstractLoader {

    /*public TableLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container tableContainer) throws DatabaseException, ContainerException {

        TableColumnCategoryLoader tableColumnCategoryLoader = new TableColumnCategoryLoader();
        tableColumnCategoryLoader.setConnection(super.getConnection());
        this.lazyLoadCategory(tableContainer, NameConstants.TABLE_COLUMNS,
            tableColumnCategoryLoader, NameConstants.TABLE_COLUMN);

        IndexCategoryLoader indexCategoryLoader = new IndexCategoryLoader();
        indexCategoryLoader.setConnection(super.getConnection());
        this.lazyLoadCategory(tableContainer, NameConstants.TABLE_INDEXES,
                indexCategoryLoader, NameConstants.INDEX);

        ForeignKeyCategoryLoader foreignKeyCategoryLoader = new ForeignKeyCategoryLoader();
        foreignKeyCategoryLoader.setConnection(super.getConnection());
        this.lazyLoadCategory(tableContainer, NameConstants.TABLE_FOREIGN_KEYS,
                foreignKeyCategoryLoader, NameConstants.FOREIGN_KEY);

        TriggerCategoryLoader triggerCategoryLoader = new TriggerCategoryLoader();
        triggerCategoryLoader.setConnection(super.getConnection());
        this.lazyLoadCategory(tableContainer, NameConstants.TABLE_TRIGGERS,
                triggerCategoryLoader, NameConstants.TRIGGER);

        ConstraintCategoryLoader constraintCategoryLoader = new ConstraintCategoryLoader();
        constraintCategoryLoader.setConnection(super.getConnection());
        this.lazyLoadCategory(tableContainer, NameConstants.TABLE_CONSTRAINTS,
                constraintCategoryLoader, NameConstants.CONSTRAINT);
    }

    @Override
    public void detailedLoad(Container tableContainer) throws DatabaseException {
        try {
            String tableName = (String) tableContainer.getAttributes().get(TableAttributes.TABLE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) tableContainer.getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.TABLEDETAILED, listRepresentationOfAttributes,
                schemaName, tableName);
            super.executeDetailedLoaderQuery(tableContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container tableContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(tableContainer);
        this.detailedLoad(tableContainer);

        TableColumnCategoryLoader tableColumnCategoryLoader = new TableColumnCategoryLoader();
        tableColumnCategoryLoader.setConnection(super.getConnection());
        tableColumnCategoryLoader.fullLoad(tableContainer.getChildByName(NameConstants.TABLE_COLUMNS));

        IndexCategoryLoader indexCategoryLoader = new IndexCategoryLoader();
        indexCategoryLoader.setConnection(super.getConnection());
        indexCategoryLoader.fullLoad(tableContainer.getChildByName(NameConstants.TABLE_INDEXES));

        ForeignKeyCategoryLoader foreignKeyCategoryLoader = new ForeignKeyCategoryLoader();
        foreignKeyCategoryLoader.setConnection(super.getConnection());
        foreignKeyCategoryLoader
            .fullLoad(tableContainer.getChildByName(NameConstants.TABLE_FOREIGN_KEYS));

        TriggerCategoryLoader triggerCategoryLoader = new TriggerCategoryLoader();
        triggerCategoryLoader.setConnection(super.getConnection());
        triggerCategoryLoader.fullLoad(tableContainer.getChildByName(NameConstants.TABLE_TRIGGERS));

        ConstraintCategoryLoader constraintCategoryLoader = new ConstraintCategoryLoader();
        constraintCategoryLoader.setConnection(super.getConnection());
        constraintCategoryLoader
            .fullLoad(tableContainer.getChildByName(NameConstants.TABLE_CONSTRAINTS));
    }

    private void lazyLoadCategory(Container tableContainer, String categoryName, Loader categoryLoader, String childType)
        throws ContainerException, DatabaseException {
        Container category = new Container();
        tableContainer.addChild(category);
        category.setName(categoryName);
        category.addAttribute(CustomAttributes.IS_CATEGORY, true);
        category.addAttribute(CustomAttributes.CHILD_TYPE, childType);
        categoryLoader.lazyLoad(category);
    }
}
