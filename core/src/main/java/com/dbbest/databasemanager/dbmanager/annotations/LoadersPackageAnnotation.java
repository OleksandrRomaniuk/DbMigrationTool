package com.dbbest.databasemanager.dbmanager.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for package-info.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface LoadersPackageAnnotation {

    /**
     * @return returns database type.
     */
    String value();
}
