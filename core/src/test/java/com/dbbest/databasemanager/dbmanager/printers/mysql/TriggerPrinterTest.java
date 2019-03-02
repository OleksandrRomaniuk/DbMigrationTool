package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TriggerAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

public class TriggerPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container trigger = new Container();

        trigger.setName("upd_film1");
        trigger.addAttribute(FunctionAttributes.DEFINER, "root@localhost");
        trigger.addAttribute(TriggerAttributes.ACTION_TIMING, "AFTER");
        trigger.addAttribute(TriggerAttributes.EVENT_MANIPULATION, "UPDATE");
        trigger.addAttribute(TriggerAttributes.TRIGGER_NAME, "upd_film1");
        trigger.addAttribute(TriggerAttributes.EVENT_OBJECT_TABLE, "film");
        trigger.addAttribute(TriggerAttributes.EVENT_OBJECT_SCHEMA, "sakila");
        trigger.addAttribute(TriggerAttributes.ACTION_STATEMENT, "BEGIN\n" +
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
