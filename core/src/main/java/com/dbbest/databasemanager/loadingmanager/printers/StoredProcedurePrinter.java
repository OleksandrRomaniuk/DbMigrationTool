package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ProcedureFunctionParameterAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.StoredProceduresAndFunctionsAttributes;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

public class StoredProcedurePrinter implements Printer {
    @Override
    public String execute(Container storedProcedureContainer) {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER $$ \n");
        query.append("CREATE ");
        getDefiner(query, storedProcedureContainer);
        query.append(" PROCEDURE " + storedProcedureContainer.getAttributes().get(StoredProceduresAndFunctionsAttributes.ROUTINE_SCHEMA.getElement())
            + "." + storedProcedureContainer.getName());

        if (storedProcedureContainer.hasChildren()) {
            getParameters(query, storedProcedureContainer);
        }

        getCharacteristics(query, storedProcedureContainer);
        query.append(" " + storedProcedureContainer.getAttributes().get(StoredProceduresAndFunctionsAttributes.ROUTINE_DEFINITION.getElement()));
        query.append("$$ \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container storedProcedureContainer) {
        Map<String, String> procedureAttributes = storedProcedureContainer.getAttributes();
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()).trim().isEmpty()) {
            query.append(" DEFINER = " + procedureAttributes.get(StoredProceduresAndFunctionsAttributes.DEFINER.getElement()));
        }
    }

    private void getCharacteristics(StringBuilder query, Container storedProcedureContainer) {
        Map<String, String> procedureAttributes = storedProcedureContainer.getAttributes();
        geRoutineComment(query, storedProcedureContainer, procedureAttributes);
        getExternalLanguage(query, storedProcedureContainer, procedureAttributes);
        getDetermenisticOption(query, storedProcedureContainer, procedureAttributes);
        getSqlDataAccess(query, storedProcedureContainer, procedureAttributes);
        getSecurityType(query, storedProcedureContainer, procedureAttributes);
    }

    private void geRoutineComment(StringBuilder query, Container storedProcedureContainer, Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()).trim().isEmpty()) {
            query.append(" COMMENT '" + procedureAttributes.get(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement()) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container storedProcedureContainer, Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()).trim().isEmpty()) {
            query.append(" LANGUAGE " + procedureAttributes.get(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement()));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container storedProcedureContainer, Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()).trim().isEmpty()) {
            if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement()).trim().equals("NO")) {
                query.append(" NOT DETERMINISTIC");
            } else {
                query.append(" DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container storedProcedureContainer, Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()).trim().isEmpty()) {
            query.append(" " + procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement()));
        }
    }

    private void getSecurityType(StringBuilder query, Container storedProcedureContainer, Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()) != null
            && !procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()).trim().isEmpty()) {
            query.append(" SQL SECURITY " + procedureAttributes.get(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement()));
        }
    }

    private void getParameters(StringBuilder query, Container storedProcedureContainer) {
        List<Container> parameters = storedProcedureContainer.getChildren();
        query.append(" (");
        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            if (parameterAttributes.get(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement()) != null
                && !parameterAttributes.get(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement()).trim().isEmpty()) {
                query.append(parameterAttributes.get(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement()) + " ");
            }
            query.append(parameter.getName() + " " + parameterAttributes.get(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement()) + ", ");
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");
    }
}
