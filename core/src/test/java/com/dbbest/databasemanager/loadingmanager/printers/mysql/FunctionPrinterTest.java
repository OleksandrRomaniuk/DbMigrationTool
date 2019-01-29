package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container function = new Container();
        function.addAttribute(AttributeSingleConstants.FUNCTION_PROCEDURE_NAME, "inventory_held_by_customer1");
        function.addAttribute(AttributeSingleConstants.ROUTINE_SCHEMA, "sakila");
        function.addAttribute(AttributeSingleConstants.DEFINER, "root@localhost");
        function.addAttribute(AttributeSingleConstants.DATA_TYPE, "int");

        Container parameter = new Container();
        parameter.addAttribute(AttributeSingleConstants.PROC_FUNC_PARAMETER_NAME, "p_inventory_id");
        parameter.addAttribute(AttributeSingleConstants.PARAMETER_MODE, "IN");
        parameter.addAttribute(AttributeSingleConstants.DATA_TYPE, "int");
        function.addChild(parameter);

        function.addAttribute(AttributeSingleConstants.ROUTINE_COMMENT, "Comment made by ROI");
        function.addAttribute(AttributeSingleConstants.EXTERNAL_LANGUAGE, "SQL");
        function.addAttribute(AttributeSingleConstants.IS_DETERMINISTIC, "NO");
        function.addAttribute(AttributeSingleConstants.SQL_DATA_ACCESS, "READS SQL DATA");
        function.addAttribute(AttributeSingleConstants.SECURITY_TYPE, "DEFINER");
        function.addAttribute(AttributeSingleConstants.ROUTINE_DEFINITION, "BEGIN\n" +
            "  DECLARE v_customer_id INT;\n" +
            "  DECLARE EXIT HANDLER FOR NOT FOUND RETURN NULL;\n" +
            "\n" +
            "  SELECT customer_id INTO v_customer_id\n" +
            "  FROM rental\n" +
            "  WHERE return_date IS NULL\n" +
            "  AND inventory_id = p_inventory_id;\n" +
            "\n" +
            "  RETURN v_customer_id;\n" +
            "END");

        FunctionPrinter functionPrinter = new FunctionPrinter();
        String query = functionPrinter.execute(function);

        assertEquals("DELIMITER $$ \n" +
            "CREATE  DEFINER = root@localhost FUNCTION sakila.inventory_held_by_customer1 (p_inventory_id int)\n" +
            "RETURNS int\n" +
            " COMMENT 'Comment made by ROI' LANGUAGE SQL NOT DETERMINISTIC READS SQL DATA SQL SECURITY DEFINER BEGIN\n" +
            "  DECLARE v_customer_id INT;\n" +
            "  DECLARE EXIT HANDLER FOR NOT FOUND RETURN NULL;\n" +
            "\n" +
            "  SELECT customer_id INTO v_customer_id\n" +
            "  FROM rental\n" +
            "  WHERE return_date IS NULL\n" +
            "  AND inventory_id = p_inventory_id;\n" +
            "\n" +
            "  RETURN v_customer_id;\n" +
            "END$$ \n" +
            "DELIMITER ;", query);
    }
}