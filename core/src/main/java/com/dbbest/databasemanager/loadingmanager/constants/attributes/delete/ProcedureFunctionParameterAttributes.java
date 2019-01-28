package com.dbbest.databasemanager.loadingmanager.constants.attributes.delete;

public enum ProcedureFunctionParameterAttributes {
    ORDINAL_POSITION("ORDINAL_POSITION"), PARAMETER_MODE("PARAMETER_MODE"), PARAMETER_NAME("PARAMETER_NAME"),
    SPECIFIC_CATALOG("SPECIFIC_CATALOG"), SPECIFIC_SCHEMA("SPECIFIC_SCHEMA"), SPECIFIC_NAME("SPECIFIC_NAME"),
    DATA_TYPE("DATA_TYPE"), CHARACTER_MAXIMUM_LENGTH("CHARACTER_MAXIMUM_LENGTH"),
    CHARACTER_OCTET_LENGTH("CHARACTER_OCTET_LENGTH"), NUMERIC_PRECISION("NUMERIC_PRECISION"),
    NUMERIC_SCALE("NUMERIC_SCALE"), CHARACTER_SET_NAME("CHARACTER_SET_NAME"),
    COLLATION_NAME("COLLATION_NAME"), DTD_IDENTIFIER("DTD_IDENTIFIER"),
    ROUTINE_TYPE("ROUTINE_TYPE");

    private String element;

    ProcedureFunctionParameterAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
