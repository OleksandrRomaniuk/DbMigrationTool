package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.TriggerAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

public class TriggerPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container trigger = new Container();

        trigger.setName("upd_film1");
        trigger.addAttribute(TriggerAttributes.DEFINER.getElement(), "root@localhost");
        trigger.addAttribute(TriggerAttributes.ACTION_TIMING.getElement(), "AFTER");
        trigger.addAttribute(TriggerAttributes.EVENT_MANIPULATION.getElement(), "UPDATE");
        trigger.addAttribute(TriggerAttributes.TRIGGER_NAME.getElement(), "upd_film1");
        trigger.addAttribute(TriggerAttributes.EVENT_OBJECT_TABLE.getElement(), "film");
        trigger.addAttribute(TriggerAttributes.EVENT_OBJECT_SCHEMA.getElement(), "sakila");
        trigger.addAttribute(TriggerAttributes.ACTION_STATEMENT.getElement(), "BEGIN\n" +
            "     IF (old.title != new.title) OR (old.description != new.description) OR (old.film_id != new.film_id)\n" +
            "     THEN\n" +
            "         UPDATE film_text\n" +
            "             SET title=new.title,\n" +
            "                 description=new.description,\n" +
            "                 film_id=new.film_id\n" +
            "         WHERE film_id=old.film_id;\n" +
            "     END IF;\n" +
            "   END");

        TriggerPrinter triggerPrinter = new TriggerPrinter();
        System.out.println(triggerPrinter.execute(trigger));

    }
}