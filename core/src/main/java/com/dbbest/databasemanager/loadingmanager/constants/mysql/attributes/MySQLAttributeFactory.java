package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.AttributeFactory;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;

import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ConstraintLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ForeignKeyLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.FunctionLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.IndexLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ProcedureFunctionParametersLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.SchemaLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.StoredProcedureLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.TableColumnLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.TableLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.TriggerLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ViewColumnLoader;
import com.dbbest.databasemanager.loadingmanager.loaders.mysql.ViewLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The realization of the attribute factory for mysql database.
 */
public final class MySQLAttributeFactory implements AttributeFactory {

    private static MySQLAttributeFactory instance;
    private Map<String, List<String>> attributeClasses = new HashMap();

    private MySQLAttributeFactory() {
        attributeClasses.put(SchemaLoader.class.getName(), SchemaAttributes.getListOfSchemaAttribute());
        attributeClasses.put(ViewLoader.class.getName(), ViewAttributes.getListOfViewAttributes());
        attributeClasses.put(ViewColumnLoader.class.getName(), TableColumnAttributes.getListOfTableColumnAttributes());
        attributeClasses.put(TriggerLoader.class.getName(), TriggerAttributes.getListOfTriggerAttributes());
        attributeClasses.put(TableColumnLoader.class.getName(), TableColumnAttributes.getListOfTableColumnAttributes());
        attributeClasses.put(FunctionLoader.class.getName(), FunctionAttributes.getListOfFunctionAttributes());
        attributeClasses.put(StoredProcedureLoader.class.getName(), FunctionAttributes.getListOfFunctionAttributes());
        attributeClasses.put(TableLoader.class.getName(), TableAttributes.getListOfTableAttributes());
        attributeClasses.put(ConstraintLoader.class.getName(), ConstraintAttributes.getListOfDetailedLoadAttributeNames());
        attributeClasses.put(ForeignKeyLoader.class.getName(), ForeignKeyAttributes.getListOfFKAttributes());
        attributeClasses.put(IndexLoader.class.getName(), IndexAttributes.getListOfIndexAttributes());
        attributeClasses.put(ProcedureFunctionParametersLoader.class.getName(),
            FunctionProcedureParameterAttributes.getListOfFunctionProcedureParameterAttributes());
    }

    /**
     * @return returns the instance of the class.
     */
    public static MySQLAttributeFactory getInstance() {
        if (instance == null) {
            instance = new MySQLAttributeFactory();
        }
        return instance;
    }

    @Override
    public List<String> getAttributes(Loader object) {
        return attributeClasses.get(object.getClass().getName());
    }

}
