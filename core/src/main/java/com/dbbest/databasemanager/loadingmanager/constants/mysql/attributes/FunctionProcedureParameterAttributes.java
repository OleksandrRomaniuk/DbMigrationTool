package com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes;

import com.dbbest.databasemanager.loadingmanager.constants.Attributes;
import com.dbbest.xmlmanager.container.DbList;
import com.dbbest.xmlmanager.container.ListOfChildren;

import java.util.List;

/**
 * The class with the attributes of the node of function(procedure) parameters.
 */
public class FunctionProcedureParameterAttributes implements Attributes {

    public static final String PROC_FUNC_PARAMETER_NAME = "PARAMETER_NAME";
    public static final String PARAMETER_MODE = "PARAMETER_MODE";

    @Override
    public List<String> getListOfAttributes() {
        return getListOfFunctionProcedureParameterAttributes();
    }

    private List<String> getListOfFunctionProcedureParameterAttributes() {
        DbList<String> listOfAttributes = new ListOfChildren();
        String[] listOfConstants = {"ORDINAL_POSITION", /*"PARAMETER_MODE", "PARAMETER_NAME",*/
            "SPECIFIC_CATALOG", "SPECIFIC_SCHEMA", "SPECIFIC_NAME", "DATA_TYPE",
            "CHARACTER_MAXIMUM_LENGTH", "CHARACTER_OCTET_LENGTH", "NUMERIC_PRECISION", "NUMERIC_SCALE", "CHARACTER_SET_NAME",
            "COLLATION_NAME", "DTD_IDENTIFIER", "ROUTINE_TYPE"};
        listOfAttributes.addAll(listOfConstants);
        listOfAttributes.add(PARAMETER_MODE);

        return (List<String>) listOfAttributes;
    }
}
