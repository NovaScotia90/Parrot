package com.rocklobstre.parrot.dependencyinjection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Matin on 18-Oct-17.
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface MainApplicationScope {

        /* create your custom scope because
         if you use singleton
         each time when it require it will create new instance.
         Also it is useful in cross scoping.
         And easy to understand by names  */
}
