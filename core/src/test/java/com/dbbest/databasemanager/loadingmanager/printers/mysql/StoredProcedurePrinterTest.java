package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StoredProcedurePrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container storedProcedure = new Container();
        storedProcedure.addAttribute(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME, "film_in_stock1");
        storedProcedure.addAttribute(AttributeSingleConstants.ROUTINE_SCHEMA, "sakila");
        storedProcedure.addAttribute(AttributeSingleConstants.DEFINER, "root@localhost");

        Container parameter = new Container();
        parameter.addAttribute(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME, "p_film_id");
        parameter.addAttribute(AttributeSingleConstants.PARAMETER_MODE, "IN");
        parameter.addAttribute(AttributeSingleConstants.DATA_TYPE, "int");
        storedProcedure.addChild(parameter);

        Container parameter2 = new Container();
        parameter2.addAttribute(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME, "p_store_id");
        parameter2.addAttribute(AttributeSingleConstants.PARAMETER_MODE, "IN");
        parameter2.addAttribute(AttributeSingleConstants.DATA_TYPE, "int");
        storedProcedure.addChild(parameter2);

        Container parameter3 = new Container();
        parameter3.addAttribute(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME, "p_film_count");
        parameter3.addAttribute(AttributeSingleConstants.PARAMETER_MODE, "OUT");
        parameter3.addAttribute(AttributeSingleConstants.DATA_TYPE, "int");
        storedProcedure.addChild(parameter3);

        storedProcedure.addAttribute(AttributeSingleConstants.ROUTINE_COMMENT, "Comment made by ROI");
        storedProcedure.addAttribute(AttributeSingleConstants.EXTERNAL_LANGUAGE, "SQL");
        storedProcedure.addAttribute(AttributeSingleConstants.IS_DETERMINISTIC, "NO");
        storedProcedure.addAttribute(AttributeSingleConstants.SQL_DATA_ACCESS, "READS SQL DATA");
        storedProcedure.addAttribute(AttributeSingleConstants.SECURITY_TYPE, "DEFINER");
        storedProcedure.addAttribute(AttributeSingleConstants.ROUTINE_DEFINITION, "BEGIN SELECT inventory_id FROM inventory WHERE film_id = p_film_id AND store_id = p_store_id AND inventory_in_stock(inventory_id); SELECT FOUND_ROWS() INTO p_film_count; END");

        StoredProcedurePrinter storedProcedurePrinter = new StoredProcedurePrinter();
        String query = storedProcedurePrinter.execute(storedProcedure);

        System.out.println(query);
        assertEquals("DELIMITER // \n" +
            "CREATE \n" +
            "DEFINER = root@localhost\n" +
            "PROCEDURE sakila.null (IN p_film_id int, IN p_store_id int, OUT p_film_count int)\n" +
            "COMMENT 'Comment made by ROI'\n" +
            "LANGUAGE SQL\n" +
            "NOT DETERMINISTIC\n" +
            "READS SQL DATA\n" +
            "SQL SECURITY DEFINER\n" +
            "BEGIN SELECT inventory_id FROM inventory WHERE film_id = p_film_id AND store_id = p_store_id AND inventory_in_stock(inventory_id); SELECT FOUND_ROWS() INTO p_film_count; END // \n" +
            "DELIMITER ;", query);
    }
}