package com.dbbest.databasemanager.dbmanager.printers.mysql;

import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.FunctionProcedureParameterAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container function = new Container();
        function.addAttribute(FunctionAttributes.FUNCTION_PROCEDURE_NAME, "inventory_held_by_customer1");
        function.addAttribute(FunctionAttributes.ROUTINE_SCHEMA, "sakila");
        function.addAttribute(FunctionAttributes.DEFINER, "root@localhost");
        function.addAttribute(FunctionAttributes.DATA_TYPE, "int");

        Container parameter = new Container();
        parameter.addAttribute(FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME, "p_inventory_id");
        parameter.addAttribute(FunctionProcedureParameterAttributes.PARAMETER_MODE, "IN");
        parameter.addAttribute(FunctionAttributes.DATA_TYPE, "int");
        function.addChild(parameter);

        function.addAttribute(FunctionAttributes.ROUTINE_COMMENT, "Comment made by ROI");
        function.addAttribute(FunctionAttributes.EXTERNAL_LANGUAGE, "SQL");
        function.addAttribute(FunctionAttributes.IS_DETERMINISTIC, "NO");
        function.addAttribute(FunctionAttributes.SQL_DATA_ACCESS, "READS SQL DATA");
        function.addAttribute(FunctionAttributes.SECURITY_TYPE, "DEFINER");
        function.addAttribute(FunctionAttributes.ROUTINE_DEFINITION, "BEGIN\n" +
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
        System.out.println(query);

        assertEquals("DELIMITER // \n" +
                "CREATE DEFINER = root@localhost FUNCTION sakila.inventory_held_by_customer1 (p_inventory_id int)\n" +
                "RETURNS int\n" +
                "COMMENT 'Comment made by ROI'\n" +
                "LANGUAGE SQL\n" +
                "NOT DETERMINISTIC\n" +
                "READS SQL DATA\n" +
                "SQL SECURITY DEFINER\n" +
                "BEGIN\n" +
                "  DECLARE v_customer_id INT;\n" +
                "  DECLARE EXIT HANDLER FOR NOT FOUND RETURN NULL;\n" +
                "\n" +
                "  SELECT customer_id INTO v_customer_id\n" +
                "  FROM rental\n" +
                "  WHERE return_date IS NULL\n" +
                "  AND inventory_id = p_inventory_id;\n" +
                "\n" +
                "  RETURN v_customer_id;\n" +
                "END // \n" +
                "DELIMITER ;", query);
    }
}
