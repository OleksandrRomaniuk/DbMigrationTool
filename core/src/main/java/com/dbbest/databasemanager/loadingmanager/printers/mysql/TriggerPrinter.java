package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.mysql.PrinterAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TriggerAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

/**
 * The class-printer of the mysql triggers.
 */
@PrinterAnnotation(LoaderPrinterName.TRIGGER)
public class TriggerPrinter implements Printer {
    @Override
    public String execute(Container triggerContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER //\n" + "CREATE" + getDefiner(triggerContainer)
            + "\nTRIGGER " + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_OBJECT_SCHEMA))
            + "." + ((String) triggerContainer.getAttributes().get(TriggerAttributes.TRIGGER_NAME)) + "\n"
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.ACTION_TIMING) + " ")
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_MANIPULATION)) + "\n"
            + "ON " + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_OBJECT_TABLE))
            + " FOR EACH ROW\n"
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.ACTION_STATEMENT)) + "; \n"
            + "DELIMITER;\n");

        return query.toString();
    }

    private String getDefiner(Container triggerContainer) throws ContainerException {
        if (triggerContainer.getAttributes().get(FunctionAttributes.DEFINER) != null
            && !((String) triggerContainer.getAttributes().get(FunctionAttributes.DEFINER)).trim().isEmpty()) {
            return "\nDEFINER = " + (String) triggerContainer.getAttributes().get(FunctionAttributes.DEFINER);
        } else {
            return "";
        }
    }
}
