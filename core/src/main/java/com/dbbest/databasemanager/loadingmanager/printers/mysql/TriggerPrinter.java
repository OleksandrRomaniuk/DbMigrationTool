package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.TriggerAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.Printer;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

public class TriggerPrinter implements Printer {
    @Override
    public String execute(Container triggerContainer) throws ContainerException {
        StringBuilder query = new StringBuilder();
        query.append("DELIMITER //\n" + "CREATE\n" + getDefiner(triggerContainer)
            + " TRIGGER " + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_OBJECT_SCHEMA.getElement()))
            + "." + ((String) triggerContainer.getAttributes().get(TriggerAttributes.TRIGGER_NAME.getElement())) + "\n"
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.ACTION_TIMING.getElement()) + " ")
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_MANIPULATION.getElement())) + "\n"
            + "ON " + ((String) triggerContainer.getAttributes().get(TriggerAttributes.EVENT_OBJECT_TABLE.getElement()))
            + " FOR EACH ROW\n"
            + ((String) triggerContainer.getAttributes().get(TriggerAttributes.ACTION_STATEMENT.getElement())) + "; \n"
            + "DELIMITER;\n");

        return query.toString();
    }

    private String getDefiner(Container triggerContainer) throws ContainerException {
        if (triggerContainer.getAttributes().get(TriggerAttributes.DEFINER.getElement()) != null
            && !((String) triggerContainer.getAttributes().get(TriggerAttributes.DEFINER.getElement())).trim().isEmpty()) {
            return " DEFINER = " + (String) triggerContainer.getAttributes().get(TriggerAttributes.DEFINER.getElement()) + "\n";
        } else {
            return "";
        }
    }
}
