package com.dbbest.databasemanager.loadingmanager.printers.mysql;

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
        query.append(" FUNCTION " + functionContainer.getAttributes().get(StoredProceduresAndFunctionsAttributes.ROUTINE_SCHEMA.getElement())
            + "." + functionContainer.getName());

        if (functionContainer.hasChildren()) {
            getParameters(query, functionContainer);
        }

        query.append("\nRETURNS " + functionContainer.getAttributes().get(StoredProceduresAndFunctionsAttributes.DATA_TYPE.getElement()) + "\n");

        getCharacteristics(query, functionContainer);
        query.append(" " + functionContainer.getAttributes().get(StoredProceduresAndFunctionsAttributes.ROUTINE_DEFINITION.getElement()));
        query.append("$$ \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container functionContainer) {
        Map<String, String> functionAttributes = functionContainer.getAttributes();
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()).trim().isEmpty()) {
            query.append(" DEFINER = " + functionAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()));
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
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()).trim().isEmpty()) {
            query.append(" COMMENT '" + functionAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()).trim().isEmpty()) {
            query.append(" LANGUAGE " + functionAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()).trim().isEmpty()) {
            if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()).trim().equals("NO")) {
                query.append(" NOT DETERMINISTIC");
            } else {
                query.append(" DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()).trim().isEmpty()) {
            query.append(" " + functionAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()));
        }
    }

    private void getSecurityType(StringBuilder query, Container functionContainer, Map<String, String> functionAttributes) {
        if (functionAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()) != null
            && !functionAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()).trim().isEmpty()) {
            query.append(" SQL SECURITY " + functionAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()));
        }
    }

    private void getParameters(StringBuilder query, Container functionContainer) {
        List<Container> parameters = functionContainer.getChildren();
        query.append(" (");
        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            query.append(parameter.getName() + " " + parameterAttributes.get(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement()) + ", ");
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
    }
}
