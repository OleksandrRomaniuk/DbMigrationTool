package com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for printer classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrinterAnnotation {
    /**
     * @return returns the type of the printer.
     */
    String value();
}
