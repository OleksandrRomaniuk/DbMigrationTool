package com.dbbest.databasemanager.loadingmanager.constants;

import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.exceptions.DatabaseException;

import java.util.List;

public interface AttributeFactory {
    List<String> getAttributes(Loader loader) throws DatabaseException;
}
