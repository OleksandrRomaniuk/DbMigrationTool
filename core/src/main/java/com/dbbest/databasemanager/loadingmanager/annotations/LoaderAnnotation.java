package com.dbbest.databasemanager.loadingmanager.annotations;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for loader classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoaderAnnotation {
    /**
     * @return returns the type of the loader.
     */
    LoaderPrinterTypeEnum value();
}