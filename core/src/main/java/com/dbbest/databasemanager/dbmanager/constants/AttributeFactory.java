package com.dbbest.databasemanager.dbmanager.constants;

import com.dbbest.databasemanager.dbmanager.loaders.Loader;
import com.dbbest.exceptions.DatabaseException;

import java.util.List;

/**
 * The interface of attribute factories foe different types of databases.
 */
public interface AttributeFactory {
    List<String> getAttributes(Loader loader) throws DatabaseException;
}
