package me.outspending.biomesapi.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiStatus.Internal
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@AsOf("2.0.0")
public @interface WireFactory {
}
