package com.dbbest.databasemanager.loadingmanager.printers;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeListConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.delete.ViewAttributes;
import com.dbbest.databasemanager.loadingmanager.printers.mysql.ViewPrinter;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewPrinterTest {

    @Test
    public void execute() throws ContainerException {
        ViewPrinter viewPrinter = new ViewPrinter();

        Container view = new Container();
        view.addAttribute(AttributeSingleConstants.TABLE_NAME, "actor_info");
        view.addAttribute(ViewAttributes.DEFINER.getElement(), "root@localhost");
        view.addAttribute(ViewAttributes.SECURITY_TYPE.getElement(), "INVOKER");
        view.addAttribute(ViewAttributes.VIEW_DEFINITION.getElement(), "select `a`.`actor_id` AS `actor_id`,`a`.`first_name` AS `first_name`,`a`.`last_name` AS `last_name`,group_concat(distinct concat(`c`.`name`,': ',(select group_concat(`f`.`title` order by `f`.`title` ASC separator ', ') from ((`sakila`.`film` `f` join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`film_actor` `fa` on((`f`.`film_id` = `fa`.`film_id`))) where ((`fc`.`category_id` = `c`.`category_id`) and (`fa`.`actor_id` = `a`.`actor_id`)))) order by `c`.`name` ASC separator '; ') AS `film_info` from (((`sakila`.`actor` `a` left join `sakila`.`film_actor` `fa` on((`a`.`actor_id` = `fa`.`actor_id`))) left join `sakila`.`film_category` `fc` on((`fa`.`film_id` = `fc`.`film_id`))) left join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `a`.`actor_id`,`a`.`first_name`,`a`.`last_name`");
        view.addAttribute(ViewAttributes.TABLE_SCHEMA.getElement(), "sakila");

        Container viewColumn = new Container();
        viewColumn.addAttribute(AttributeSingleConstants.COLUMN_NAME, "first_name");
        Container viewColumn2 = new Container();
        viewColumn2.addAttribute(AttributeSingleConstants.COLUMN_NAME, "last_name");

        viewColumn.addAttribute(ColumnAttributes.COLUMN_TYPE.getElement(), "varchar(45)");
        viewColumn.addAttribute(ColumnAttributes.COLUMN_IS_NULLABLE.getElement(), "NO");
        viewColumn2.addAttribute(ColumnAttributes.COLUMN_TYPE.getElement(), "varchar(45)");
        viewColumn2.addAttribute(ColumnAttributes.COLUMN_IS_NULLABLE.getElement(), "NO");
        view.addChild(viewColumn);
        view.addChild(viewColumn2);

        assertEquals("CREATE DEFINER root@localhost SQL SECURITY INVOKER VIEW sakila.actor_info (first_name, last_name) AS select `a`.`actor_id` AS `actor_id`,`a`.`first_name` AS `first_name`,`a`.`last_name` AS `last_name`,group_concat(distinct concat(`c`.`name`,': ',(select group_concat(`f`.`title` order by `f`.`title` ASC separator ', ') from ((`sakila`.`film` `f` join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`film_actor` `fa` on((`f`.`film_id` = `fa`.`film_id`))) where ((`fc`.`category_id` = `c`.`category_id`) and (`fa`.`actor_id` = `a`.`actor_id`)))) order by `c`.`name` ASC separator '; ') AS `film_info` from (((`sakila`.`actor` `a` left join `sakila`.`film_actor` `fa` on((`a`.`actor_id` = `fa`.`actor_id`))) left join `sakila`.`film_category` `fc` on((`fa`.`film_id` = `fc`.`film_id`))) left join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `a`.`actor_id`,`a`.`first_name`,`a`.`last_name`",
            viewPrinter.execute(view));
    }
}