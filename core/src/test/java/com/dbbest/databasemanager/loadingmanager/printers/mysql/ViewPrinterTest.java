package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewPrinterTest {

    @Test
    public void execute() throws ContainerException {
        ViewPrinter viewPrinter = new ViewPrinter();

        Container view = new Container();
        view.addAttribute(AttributeSingleConstants.TABLE_NAME, "actor_info");
        view.addAttribute(AttributeSingleConstants.DEFINER, "root@localhost");
        view.addAttribute(AttributeSingleConstants.SECURITY_TYPE, "INVOKER");
        view.addAttribute(AttributeSingleConstants.VIEW_DEFINITION, "select `a`.`actor_id` AS `actor_id`,`a`.`first_name` AS `first_name`,`a`.`last_name` AS `last_name`,group_concat(distinct concat(`c`.`name`,': ',(select group_concat(`f`.`title` order by `f`.`title` ASC separator ', ') from ((`sakila`.`film` `f` join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`film_actor` `fa` on((`f`.`film_id` = `fa`.`film_id`))) where ((`fc`.`category_id` = `c`.`category_id`) and (`fa`.`actor_id` = `a`.`actor_id`)))) order by `c`.`name` ASC separator '; ') AS `film_info` from (((`sakila`.`actor` `a` left join `sakila`.`film_actor` `fa` on((`a`.`actor_id` = `fa`.`actor_id`))) left join `sakila`.`film_category` `fc` on((`fa`.`film_id` = `fc`.`film_id`))) left join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `a`.`actor_id`,`a`.`first_name`,`a`.`last_name`");
        view.addAttribute(AttributeSingleConstants.TABLE_SCHEMA, "sakila");

        Container viewColumn = new Container();
        viewColumn.addAttribute(AttributeSingleConstants.COLUMN_NAME, "first_name");
        Container viewColumn2 = new Container();
        viewColumn2.addAttribute(AttributeSingleConstants.COLUMN_NAME, "last_name");

        viewColumn.addAttribute(AttributeSingleConstants.COLUMN_TYPE, "varchar(45)");
        viewColumn.addAttribute(AttributeSingleConstants.COLUMN_IS_NULLABLE, "NO");
        viewColumn2.addAttribute(AttributeSingleConstants.COLUMN_TYPE, "varchar(45)");
        viewColumn2.addAttribute(AttributeSingleConstants.COLUMN_IS_NULLABLE, "NO");
        view.addChild(viewColumn);
        view.addChild(viewColumn2);

        assertEquals("CREATE\n" +
                "DEFINER = root@localhost\n" +
                "SQL SECURITY INVOKER\n" +
                "VIEW sakila.actor_info (\n" +
                "`first_name`,\n" +
                "`last_name`)\n" +
                "AS select `a`.`actor_id` AS `actor_id`,`a`.`first_name` AS `first_name`,`a`.`last_name` AS `last_name`,group_concat(distinct concat(`c`.`name`,': ',(select group_concat(`f`.`title` order by `f`.`title` ASC separator ', ') from ((`sakila`.`film` `f` join `sakila`.`film_category` `fc` on((`f`.`film_id` = `fc`.`film_id`))) join `sakila`.`film_actor` `fa` on((`f`.`film_id` = `fa`.`film_id`))) where ((`fc`.`category_id` = `c`.`category_id`) and (`fa`.`actor_id` = `a`.`actor_id`)))) order by `c`.`name` ASC separator '; ') AS `film_info` from (((`sakila`.`actor` `a` left join `sakila`.`film_actor` `fa` on((`a`.`actor_id` = `fa`.`actor_id`))) left join `sakila`.`film_category` `fc` on((`fa`.`film_id` = `fc`.`film_id`))) left join `sakila`.`category` `c` on((`fc`.`category_id` = `c`.`category_id`))) group by `a`.`actor_id`,`a`.`first_name`,`a`.`last_name`",
            viewPrinter.execute(view));
    }
}