package com.dbbest.databasemanager.loadingmanager.constants.annotations;

public enum LoaderPrinterTypeEnum {
    Schema("Schema"), Foreign_Key("ForeignKey"), Function("Function"), Index("Index"), Stored_Procedure("StoredProcedure"),
    TableColumn("TableColumn"), Table("Table"), Trigger("Trigger"), ViewColumn("ViewColumn"), View("View");

    private String element;

    LoaderPrinterTypeEnum(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}
