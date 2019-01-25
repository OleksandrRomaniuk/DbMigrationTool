package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.ProcedureFunctionParameterAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.StoredProceduresAndFunctionsAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionPrinterTest {

    @Test
    public void execute() throws ContainerException {
        Container function = new Container();
        function.setName("inventory_held_by_customer1");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_SCHEMA.getElement(), "sakila");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.DEFINER.getElement(), "root@localhost");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.DATA_TYPE.getElement(), "int");

        Container parameter = new Container();
        parameter.setName("p_inventory_id");
        parameter.addAttribute(ProcedureFunctionParameterAttributes.PARAMETER_MODE.getElement(), "IN");
        parameter.addAttribute(ProcedureFunctionParameterAttributes.DATA_TYPE.getElement(), "int");
        function.addChild(parameter);

        function.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_COMMENT.getElement(), "Comment made by ROI");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.EXTERNAL_LANGUAGE.getElement(), "SQL");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.IS_DETERMINISTIC.getElement(), "NO");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.SQL_DATA_ACCESS.getElement(), "READS SQL DATA");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.SECURITY_TYPE.getElement(), "DEFINER");
        function.addAttribute(StoredProceduresAndFunctionsAttributes.ROUTINE_DEFINITION.getElement(), "BEGIN\n" +
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