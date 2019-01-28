package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ProcedureFunctionParameterAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.StoredProceduresAndFunctionsAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoredProcedurePrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container storedProcedure = new Container();
        storedProcedure.setName("film_in_stock1");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_SCHEMA.getElement(), "sakila");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.DEFINER.getElement(), "root@localhost");

        Container parameter = new Container();
        parameter.setName("p_film_id");
        parameter.addAttribute(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement(), "IN");
        parameter.addAttribute(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement(), "int");
        storedProcedure.addChild(parameter);

        Container parameter2 = new Container();
        parameter2.setName("p_store_id");
        parameter2.addAttribute(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement(), "IN");
        parameter2.addAttribute(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement(), "int");
        storedProcedure.addChild(parameter2);

        Container parameter3 = new Container();
        parameter3.setName("p_film_count");
        parameter3.addAttribute(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement(), "OUT");
        parameter3.addAttribute(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement(), "int");
        storedProcedure.addChild(parameter3);

        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement(), "Comment made by ROI");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement(), "SQL");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement(), "NO");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement(), "READS SQL DATA");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement(), "DEFINER");
        storedProcedure.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_DEFINITION.getElement(), "BEGIN SELECT inventory_id FROM inventory WHERE film_id = p_film_id AND store_id = p_store_id AND inventory_in_stock(inventory_id); SELECT FOUND_ROWS() INTO p_film_count; END");

        StoredProcedurePrinter storedProcedurePrinter = new StoredProcedurePrinter();
        String query = storedProcedurePrinter.execute(storedProcedure);

        assertEquals("DELIMITER $$ \n" +
            "CREATE  DEFINER = root@localhost PROCEDURE sakila.film_in_stock1 (IN p_film_id int, IN p_store_id int, OUT p_film_count int) COMMENT 'Comment made by ROI' LANGUAGE SQL NOT DETERMINISTIC READS SQL DATA SQL SECURITY DEFINER BEGIN SELECT inventory_id FROM inventory WHERE film_id = p_film_id AND store_id = p_store_id AND inventory_in_stock(inventory_id); SELECT FOUND_ROWS() INTO p_film_count; END$$ \n" +
            "DELIMITER ;", query);
    }
}