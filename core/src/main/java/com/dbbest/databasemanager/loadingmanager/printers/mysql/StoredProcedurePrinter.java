package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionProcedureParameterAttributes;
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
        query.append("\n" + "PROCEDURE " + storedProcedureContainer.getAttributes().get(FunctionAttributes.ROUTINE_SCHEMA)
            + "." + storedProcedureContainer.getAttributes().get(FunctionAttributes.FUNCTION_PROCEDURE_NAME));

        query.append(" (");
        if (storedProcedureContainer.hasChildren()) {
            getParameters(query, storedProcedureContainer);
        }
        query.append(")");

        getCharacteristics(query, storedProcedureContainer);
        query.append("\n" + storedProcedureContainer.getAttributes().get(FunctionAttributes.ROUTINE_DEFINITION));
        query.append(" // \nDELIMITER ;");
        return query.toString();
    }

    private void getDefiner(StringBuilder query, Container storedProcedureContainer) {
        Map<String, String> procedureAttributes = storedProcedureContainer.getAttributes();
        if (procedureAttributes.get(FunctionAttributes.DEFINER) != null
            && !procedureAttributes.get(FunctionAttributes.DEFINER).trim().isEmpty()) {
            query.append("\n" + "DEFINER = " + procedureAttributes.get(FunctionAttributes.DEFINER));
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
        if (procedureAttributes.get(FunctionAttributes.ROUTINE_COMMENT) != null
            && !procedureAttributes.get(FunctionAttributes.ROUTINE_COMMENT).trim().isEmpty()) {
            query.append("\n" + "COMMENT '" + procedureAttributes.get(FunctionAttributes.ROUTINE_COMMENT) + "'");
        }
    }

    private void getExternalLanguage(StringBuilder query, Container storedProcedureContainer,
                                     Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE) != null
            && !procedureAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE).trim().isEmpty()) {
            query.append("\n" + "LANGUAGE " + procedureAttributes.get(FunctionAttributes.EXTERNAL_LANGUAGE));
        }
    }

    private void getDetermenisticOption(StringBuilder query, Container storedProcedureContainer,
                                        Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(FunctionAttributes.IS_DETERMINISTIC) != null
            && !procedureAttributes.get(FunctionAttributes.IS_DETERMINISTIC).trim().isEmpty()) {
            if (procedureAttributes.get(FunctionAttributes.IS_DETERMINISTIC).trim().equals("NO")) {
                query.append("\n" + "NOT DETERMINISTIC");
            } else {
                query.append("\n" + "DETERMINISTIC");
            }
        }
    }

    private void getSqlDataAccess(StringBuilder query, Container storedProcedureContainer,
                                  Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(FunctionAttributes.SQL_DATA_ACCESS) != null
            && !procedureAttributes.get(FunctionAttributes.SQL_DATA_ACCESS).trim().isEmpty()) {
            query.append("\n" + procedureAttributes.get(FunctionAttributes.SQL_DATA_ACCESS));
        }
    }

    private void getSecurityType(StringBuilder query, Container storedProcedureContainer,
                                 Map<String, String> procedureAttributes) {
        if (procedureAttributes.get(FunctionAttributes.SECURITY_TYPE) != null
            && !procedureAttributes.get(FunctionAttributes.SECURITY_TYPE).trim().isEmpty()) {
            query.append("\n" + "SQL SECURITY " + procedureAttributes.get(FunctionAttributes.SECURITY_TYPE));
        }
    }

    private void getParameters(StringBuilder query, Container storedProcedureContainer) {
        List<Container> parameters = storedProcedureContainer.getChildren();

        for (Container parameter : parameters) {
            Map<String, String> parameterAttributes = parameter.getAttributes();

            if (parameterAttributes.get(FunctionProcedureParameterAttributes.PARAMETER_MODE) != null
                && !parameterAttributes.get(FunctionProcedureParameterAttributes.PARAMETER_MODE).trim().isEmpty()) {
                query.append(parameterAttributes.get(FunctionProcedureParameterAttributes.PARAMETER_MODE) + " ");
            }
            query.append(parameterAttributes.get(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME)
                + " " + parameterAttributes.get(FunctionAttributes.DATA_TYPE) + ", ");
        }
        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
    }
}
