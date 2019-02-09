package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.AttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.*;
import com.dbbest.exceptions.DatabaseException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class MySQLAttributeFactory implements AttributeFactory {

    private static MySQLAttributeFactory instance;
    private Map<String, Class> attributeClasses = new HashMap();

    private MySQLAttributeFactory() {
        attributeClasses.put(SchemaLoader.class.getName(), SchemaAttributes.class);
        attributeClasses.put(ViewLoader.class.getName(), ViewAttributes.class);
        attributeClasses.put(ViewColumnLoader.class.getName(), TableColumnAttributes.class);
        attributeClasses.put(TriggerLoader.class.getName(), TriggerAttributes.class);
        attributeClasses.put(TableColumnLoader.class.getName(), TableColumnAttributes.class);
        attributeClasses.put(FunctionLoader.class.getName(), FunctionAttributes.class);
        attributeClasses.put(StoredProcedureLoader.class.getName(), FunctionAttributes.class);
        attributeClasses.put(TableLoader.class.getName(), TableAttributes.class);
        attributeClasses.put(ConstraintLoader.class.getName(), ConstraintAttributes.class);
        attributeClasses.put(ForeignKeyLoader.class.getName(), ForeignKeyAttributes.class);
        attributeClasses.put(IndexLoader.class.getName(), IndexAttributes.class);
        attributeClasses.put(ProcedureFunctionParameteresLoader.class.getName(), FunctionProcedureParameterAttributes.class);
    }

    public static MySQLAttributeFactory getInstance() {
        if (instance == null) {
            instance = new MySQLAttributeFactory();
        }
        return instance;
    }

    @Override
    public List<String> getAttributes(Loader object) throws DatabaseException {

        Class concreteClassWithAttributes = attributeClasses.get(object.getClass().getName());
        try {
            return ((Attributes) concreteClassWithAttributes
                .getDeclaredConstructor().newInstance()).getListOfAttributes();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

}
