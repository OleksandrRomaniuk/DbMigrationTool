package com.dbbest.databasemanager.loadingmanager.constants.enumsattributes;

public enum TableAttributes {
    TABLE_NAME("TABLE_NAME"), TABLE_TYPE("TABLE_TYPE"), TABLE_ENGINE("ENGINE"), TABLE_VERSION("VERSION"), TABLE_ROW_FORMAT("ROW_FORMAT"),
    TABLE_ROWS("TABLE_ROWS"), TABLE_AVG_ROW_LENGTH("AVG_ROW_LENGTH"), TABLE_DATA_LENGTH("DATA_LENGTH"), TABLE_MAX_DATA_LENGTH("MAX_DATA_LENGTH"),
    TABLE_INDEX_LENGTH("INDEX_LENGTH"), TABLE_DATA_FREE("DATA_FREE"), TABLE_AUTO_INCREMENT("AUTO_INCREMENT"), TABLE_CREATE_TIME("CREATE_TIME"),
    TABLE_UPDATE_TIME("UPDATE_TIME"), TABLE_CHECK_TIME("CHECK_TIME"), TABLE_TABLE_COLLATION("TABLE_COLLATION"), TABLE_CHECKSUM("CHECKSUM"),
    TABLE_CREATE_OPTIONS("CREATE_OPTIONS"), TABLE_TABLE_COMMENT("TABLE_COMMENT");

    private String element;

    TableAttributes(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}

