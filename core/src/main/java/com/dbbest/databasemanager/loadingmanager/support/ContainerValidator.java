package com.dbbest.databasemanager.loadingmanager.support;

import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerValidator {

    private static final Logger logger = Logger.getLogger("Connection logger");

    public boolean ifContainerContainsSchemaName(Container tree) {
        if (tree.getName() != null && !tree.getName().trim().isEmpty()) {
            return true;
        } else {
            logger.log(Level.SEVERE, "The container does not contain the name.");
            return false;
        }
    }

    public boolean ifSchemaContainsCategoryTables(Container tree) {
        if (ifContainerContainsSchemaName(tree)) {
            try {
                tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement());
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "There is no category Tables in the tree.");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean ifThereAreTablesInCategoryTables(Container tree) {
        if (ifSchemaContainsCategoryTables(tree)) {
            try {
                List<Container> tables = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildren();
                return (!tables.isEmpty() && tables != null);
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "There are no tables in the category Tables.");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean ifTableContainesCategoryColumns(Container tree, Container table) {
        if (ifThereAreTablesInCategoryTables(tree)) {
            try {
                tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Columns.getElement());
                return true;
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain category " + TableCategoriesTagNameCategories.Columns.getElement());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean ifThereAreColumnsInCategoryColumns(Container tree, Container table) {
        if (ifTableContainesCategoryColumns(tree, table)) {
            List<Container> columns = null;
            try {
                columns = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Columns.getElement()).getChildren();
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain columns in the category " + TableCategoriesTagNameCategories.Columns.getElement());
            }
            return (!columns.isEmpty() && columns != null);
        } else {
            return false;
        }
    }

    public boolean ifTableContainsCategoryIndexes(Container tree, Container table) {
        if (ifThereAreTablesInCategoryTables(tree)) {
            try {
                tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Indexes.getElement());
                return true;
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain category " + TableCategoriesTagNameCategories.Indexes.getElement());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean ifThereAreIndexesInCategoryIndexes(Container tree, Container table) {
        if (ifTableContainsCategoryIndexes(tree, table)) {
            List<Container> indexes = null;
            try {
                indexes = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()).getChildren();
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain indexes in the category " + TableCategoriesTagNameCategories.Indexes.getElement());
            }
            return (!indexes.isEmpty() && indexes != null);
        } else {
            return false;
        }
    }

    public boolean ifTableContainsCategoryForeignKeys(Container tree, Container table) {
        if (ifThereAreTablesInCategoryTables(tree)) {
            try {
                tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement());
                return true;
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain category " + TableCategoriesTagNameCategories.Foreign_Keys.getElement());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean ifThereAreFKsInCategoryFKs(Container tree, Container table) {
        if (ifTableContainsCategoryForeignKeys(tree, table)) {
            List<Container> foreignKeys = null;
            try {
                foreignKeys = tree.getChildByName(SchemaCategoriesTagNameConstants.Tables.getElement()).getChildByName(table.getName())
                    .getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement()).getChildren();
            } catch (ContainerException e) {
                logger.log(Level.SEVERE, "The table " + table.getName()
                    + " does not contain foreign keys in the category " + TableCategoriesTagNameCategories.Foreign_Keys.getElement());
            }
            return (!foreignKeys.isEmpty() && foreignKeys != null);
        } else {
            return false;
        }
    }

}
