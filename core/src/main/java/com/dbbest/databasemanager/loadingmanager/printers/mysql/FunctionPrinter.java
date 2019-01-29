package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ProcedureFunctionParameterAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.StoredProceduresAndFunctionsAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

public class FunctionPrinter implements Printer {
    @Override
    public String execute(Container functionContainer) {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER $$ \n");
        query.append("CREATE ");
        getDefiner(query, functionContainer);
        query.append(" FUNCTION " + functionContainer.getAttributes().get(AttributeSingleConstants.ROUTINE_SCHEMA)
            + "." + functionContainer.getAttributes().get(AttributeSingleConstants.FUNCTION_PROCEDURE_NAME));

        if (functionContainer.hasChildren()) {
            getParameters(query, functionContainer);
        }

        query.append("\nRETURNS " + functionContainer.getAttributes().get(AttributeSingleConstants.DATA_TYPE) + "\n");

        getCharacteristics(query, functionContainer);
        query.append(" " + functionContainer.getAttributes().get(AttributeSingleConstants.ROUTINE_DEFINITION));
        query.append("$$ \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container functionContainer) {
        Map<String, String> functionAttributes = functionContainer.getAttributes();
        if (functionAttributes.get(AttributeSingleConstants.DEFINER) != null
            && !functionAttributes.get(AttributeSingleConstants.DEFINER).trim().isEmpty()) {
            query.append(" DEFINER = " + functionAttributes.get(AttributeSingleConstants.DEFINER));
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
        if (functionAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT) != null
            && !functionAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT).trim().isEmpty()) {
            query.append(" COMMENT '" + functionAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE) != null
            && !functionAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE).trim().isEmpty()) {
            query.append(" LANGUAGE " + functionAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC) != null
            && !functionAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC).trim().isEmpty()) {
            if (functionAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC).trim().equals("NO")) {
                query.append(" NOT DETERMINISTIC");
            } else {
                query.append(" DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS) != null
            && !functionAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS).trim().isEmpty()) {
            query.append(" " + functionAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS));
        }
    }

    private void getSecurityType(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(AttributeSingleConstants.SECURITY_TYPE) != null
            && !functionAttributes.get(AttributeSingleConstants.SECURITY_TYPE).trim().isEmpty()) {
            query.append(" SQL SECURITY " + functionAttributes.get(AttributeSingleConstants.SECURITY_TYPE));
        }
    }

    private void getParameters(StringBuilder query, Container functionContainer) {
        List<Container> parameters = functionContainer.getChildren();
        query.append(" (");
        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            query.append(parameterAttributes.get(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME) + " " + parameterAttributes.get(AttributeSingleConstants.DATA_TYPE) + ", ");
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
    }
}
