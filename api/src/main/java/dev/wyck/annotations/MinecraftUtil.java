package dev.wyck.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type is a utility class that also has a
 * Minecraft counterpart.
 */
@AsOf("3.0.0")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MinecraftUtil {
}
