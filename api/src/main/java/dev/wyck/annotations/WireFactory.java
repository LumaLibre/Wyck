package dev.wyck.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class is a wire factory, which is responsible for providing
 * instances of the interface it implements.
 *
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@AsOf("2.0.0")
@ApiStatus.Internal
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface WireFactory {
}
