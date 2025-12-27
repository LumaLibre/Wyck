package me.outspending.biomesapi.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark elements that are experimental.
 * Experimental elements are those that are not yet stable and may change in the future.
 * This annotation can be applied to methods, types, constructors, modules, and packages.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 * @deprecated Use {@link org.jetbrains.annotations.ApiStatus.Experimental} instead
 */
@ApiStatus.Internal
@Deprecated(since = "0.0.19", forRemoval = true)
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.MODULE,
        ElementType.PACKAGE
})
@AsOf("0.0.1")
public @interface Experimental {
}