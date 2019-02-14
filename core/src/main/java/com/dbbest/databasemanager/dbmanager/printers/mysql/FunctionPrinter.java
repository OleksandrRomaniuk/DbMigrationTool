package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionProcedureParameterAttributes;
import com.dbbest.databasemanager.dbmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

/**
 * The class-printer of the functions.
 */
@PrinterAnnotation(NameConstants.FUNCTION)
public class FunctionPrinter implements Printer {
    @Override
    public String execute(Container functionContainer) {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER // \n");
        query.append("CREATE");
        getDefiner(query, functionContainer);
        query.append(" FUNCTION " + functionContainer.getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA)
            + "." + functionContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME));

        query.append(" (");
        if (functionContainer.hasChildren()) {
            getParameters(query, functionContainer);
        }
        query.append(")");


        query.append("\n" + "RETURNS " + functionContainer.getAttributes().get(FunctionAttributes.DATA_TYPE));

        getCharacteristics(query, functionContainer);
        query.append("\n" + functionContainer.getAttributes().get(FunctionAttributes.ROUTINE_DEFINITION));
        query.append(" // \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container functionContainer) {
        Map<String, String> functionAttributes = functionContainer.getAttributes();
        if (functionAttributes.get(FunctionAttributes.DEFINER) != null
            && !functionAttributes.get(FunctionAttributes.DEFINER).trim().isEmpty()) {
            query.append(" DEFINER = " + functionAttributes.get(FunctionAttributes.DEFINER));
        }
    }

    private void getCharacteristics(StringBuilder query, Container functionContainer) {
        Map<String, String> functionAttributes = functionContainer.getAttributes();
        geRoutineComment(query, functionContainer, functionAttributes);
        getExternalLanguage(query, functionContainer, functionAttributes);
        getDetermenisticOption(query, functionContainer, functionAttributes);
        getSqlDataAccess(query, functionContainer, functionAttributes);
        getSecurityType(query, functionContainer, functionAttributes);
    }

    private void geRoutineComment(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(FunctionAttributes.ROUTINE_COMMENT) != null
            && !functionAttributes.get(FunctionAttributes.ROUTINE_COMMENT).trim().isEmpty()) {
            query.append("\n" + "COMMENT '" + functionAttributes.get(FunctionAttributes.ROUTINE_COMMENT) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE) != null
            && !functionAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE).trim().isEmpty()) {
            query.append("\n" + "LANGUAGE " + functionAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(FunctionAttributes.IS_DETERMINISTIC) != null
            && !functionAttributes.get(FunctionAttributes.IS_DETERMINISTIC).trim().isEmpty()) {
            if (functionAttributes.get(FunctionAttributes.IS_DETERMINISTIC).trim().equals("NO")) {
                query.append("\n" + "NOT DETERMINISTIC");
            } else {
                query.append("\n" + "DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(FunctionAttributes.SQL_DATA_ACCESS) != null
            && !functionAttributes.get(FunctionAttributes.SQL_DATA_ACCESS).trim().isEmpty()) {
            query.append("\n" + functionAttributes.get(FunctionAttributes.SQL_DATA_ACCESS));
        }
    }

    private void getSecurityType(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(FunctionAttributes.SECURITY_TYPE) != null
            && !functionAttributes.get(FunctionAttributes.SECURITY_TYPE).trim().isEmpty()) {
            query.append("\n" + "SQL SECURITY " + functionAttributes.get(FunctionAttributes.SECURITY_TYPE));
        }
    }

    private void getParameters(StringBuilder query, Container functionContainer) {
        List<Container> parameters = functionContainer.getChildren();
        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            if (parameterAttributes.get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME) != null
                && !parameterAttributes.get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME).isEmpty()
                && !parameterAttributes.get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME).equals("null")) {
                query.append(parameterAttributes.get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME)
                    + " " + parameterAttributes.get(FunctionAttributes.DATA_TYPE) + ", ");
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
    }
}
