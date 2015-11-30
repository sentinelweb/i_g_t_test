package uk.co.sentinelweb.igttest.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scope that exists for the activity lifetime
 *
 * Not use in this example but this would be use for activity level component scoping
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {

}