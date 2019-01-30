package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

@PrinterAnnotation(LoaderPrinterName.TRIGGER)
public class TriggerPrinter implements Printer {
    @Override
    public String execute(Container triggerContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER //\n" + "CREATE\n" + getDefiner(triggerContainer)
            + " TRIGGER " + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.EVENT_OBJECT_SCHEMA))
            + "." + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.TRIGGER_NAME)) + "\n"
            + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.ACTION_TIMING) + " ")
            + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.EVENT_MANIPULATION)) + "\n"
            + "ON " + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.EVENT_OBJECT_TABLE))
            + " FOR EACH ROW\n"
            + ((String) triggerContainer.getAttributes().get(AttributeSingleConstants.ACTION_STATEMENT)) + "; \n"
            + "DELIMITER;\n");

        return query.toString();
    }

    private String getDefiner(Container triggerContainer) throws ContainerException {
        if (triggerContainer.getAttributes().get(AttributeSingleConstants.DEFINER) != null
            && !((String) triggerContainer.getAttributes().get(AttributeSingleConstants.DEFINER)).trim().isEmpty()) {
            return " DEFINER = " + (String) triggerContainer.getAttributes().get(AttributeSingleConstants.DEFINER) + "\n";
        } else {
            return "";
        }
    }
}
