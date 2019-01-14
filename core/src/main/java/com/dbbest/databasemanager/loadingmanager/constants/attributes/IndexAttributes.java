package com.dbbest.databasemanager.loadingmanager.constants.attributes;

public enum IndexAttributes {

    TABLE_CATALOG("TABLE_CATALOG"), TABLE_SCHEMA("TABLE_SCHEMA"), TABLE_NAME("TABLE_NAME"), NON_UNIQUE("NON_UNIQUE"), INDEX_SCHEMA("INDEX_SCHEMA"),
    INDEX_NAME("INDEX_NAME"), SEQ_IN_INDEX("SEQ_IN_INDEX"), COLUMN_NAME("COLUMN_NAME"), COLLATION("COLLATION"), CARDINALITY("CARDINALITY"),
    SUB_PART("SUB_PART"), PACKED("PACKED"), NULLABLE("NULLABLE"), INDEX_TYPE("INDEX_TYPE"), COMMENT("COMMENT"), INDEX_COMMENT("INDEX_COMMENT");

    private String element;

    IndexAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
