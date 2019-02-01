package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.xmlmanager.container.Container;

import java.util.List;
import java.util.Map;

/**
 * The class-printer of the stored procedures.
 */
@PrinterAnnotation(LoaderPrinterName.STORED_PROCEDURE)
public class StoredProcedurePrinter implements Printer {
    @Override
    public String execute(Container storedProcedureContainer) {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER // \n");
        query.append("CREATE ");
        getDefiner(query, storedProcedureContainer);
        query.append("\n" + "PROCEDURE " + storedProcedureContainer.getAttributes().get(AttributeSingleConstants.ROUTINE_SCHEMA)
            + "." + storedProcedureContainer.getAttributes().get(AttributeSingleConstants.FUNCTION_PROCEDURE_NAME));

        query.append(" (");
        if (storedProcedureContainer.hasChildren()) {
            getParameters(query, storedProcedureContainer);
        }
        query.append(")");

        getCharacteristics(query, storedProcedureContainer);
        query.append("\n" + storedProcedureContainer.getAttributes().get(AttributeSingleConstants.ROUTINE_DEFINITION));
        query.append(" // \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container storedProcedureContainer) {
        Map<String, String> procedureAttributes = storedProcedureContainer.getAttributes();
        if (procedureAttributes.get(AttributeSingleConstants.DEFINER) != null
            && !procedureAttributes.get(AttributeSingleConstants.DEFINER).trim().isEmpty()) {
            query.append("\n" + "DEFINER = " + procedureAttributes.get(AttributeSingleConstants.DEFINER));
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

    private void geRoutineComment(StringBuilder query, Container storedProcedureContainer,
                                  Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT) != null
            && !procedureAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT).trim().isEmpty()) {
            query.append("\n" + "COMMENT '" + procedureAttributes.get(AttributeSingleConstants.ROUTINE_COMMENT) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container storedProcedureContainer,
                                     Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE) != null
            && !procedureAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE).trim().isEmpty()) {
            query.append("\n" + "LANGUAGE " + procedureAttributes.get(AttributeSingleConstants.EXTERNAL_LANGUAGE));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container storedProcedureContainer,
                                        Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC) != null
            && !procedureAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC).trim().isEmpty()) {
            if (procedureAttributes.get(AttributeSingleConstants.IS_DETERMINISTIC).trim().equals("NO")) {
                query.append("\n" + "NOT DETERMINISTIC");
            } else {
                query.append("\n" + "DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container storedProcedureContainer,
                                  Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS) != null
            && !procedureAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS).trim().isEmpty()) {
            query.append("\n" + procedureAttributes.get(AttributeSingleConstants.SQL_DATA_ACCESS));
        }
    }

    private void getSecurityType(StringBuilder query, Container storedProcedureContainer,
                                 Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(AttributeSingleConstants.SECURITY_TYPE) != null
            && !procedureAttributes.get(AttributeSingleConstants.SECURITY_TYPE).trim().isEmpty()) {
            query.append("\n" + "SQL SECURITY " + procedureAttributes.get(AttributeSingleConstants.SECURITY_TYPE));
        }
    }

    private void getParameters(StringBuilder query, Container storedProcedureContainer) {
        List<Container> parameters = storedProcedureContainer.getChildren();

        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            if (parameterAttributes.get(AttributeSingleConstants.PARAMETER_MODE) != null
                && !parameterAttributes.get(AttributeSingleConstants.PARAMETER_MODE).trim().isEmpty()) {
                query.append(parameterAttributes.get(AttributeSingleConstants.PARAMETER_MODE) + " ");
            }
            query.append(parameterAttributes.get(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME)
                + " " + parameterAttributes.get(AttributeSingleConstants.DATA_TYPE) + ", ");
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
    }
}
