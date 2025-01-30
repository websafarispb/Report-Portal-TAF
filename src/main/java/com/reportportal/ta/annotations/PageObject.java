package com.reportportal.ta.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Target(TYPE)
@Retention(RUNTIME)
@Component
@Lazy
@Scope(SCOPE_PROTOTYPE)
public @interface PageObject {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}
