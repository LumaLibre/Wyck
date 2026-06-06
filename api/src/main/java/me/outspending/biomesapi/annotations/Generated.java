package me.outspending.biomesapi.annotations;

import org.jspecify.annotations.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark generated code.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Generated {
    /**
     * The time that this code was generated at in ISO-8601 format (e.g. "2024-06-01T12:00:00Z").
     * @return the time that this code was generated at
     * @since 2.3.0
     */
    @NonNull String value() default "";
}
