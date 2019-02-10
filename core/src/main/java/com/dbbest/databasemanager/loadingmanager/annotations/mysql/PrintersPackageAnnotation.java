package com.dbbest.databasemanager.loadingmanager.annotations.mysql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation for package-info of the printers package.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface PrintersPackageAnnotation {
    /**
     * @return returns database type.
     */
    String value();
}
