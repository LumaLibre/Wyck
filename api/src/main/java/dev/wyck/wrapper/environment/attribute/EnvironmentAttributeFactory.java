package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * A factory for creating EnvironmentAttribute instances.
 *
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public interface EnvironmentAttributeFactory {

    @ApiStatus.Internal
    WireProvider<EnvironmentAttributeFactory> WIRE = WireProvider.create("dev.wyck.wrapper.environment.attribute.EnvironmentAttributeFactoryImpl");

    /**
     * Resolves a vanilla environment attribute by name (e.g. "fog_color").
     * @since 2.0.0
     * @param key the name of the attribute to resolve
     * @return an EnvironmentAttributeHandle for the specified attribute, or null if no such attribute exists
     */
    @AsOf("2.0.0")
    @Nullable <T> EnvironmentAttributeHandle<T> byKey(String key);

    /**
     * Returns the default value of the wrapped attribute. Needed because the
     * default value comes from NMS (EnvironmentAttribute#defaultValue).
     * @since 2.0.0
     * @param handle the EnvironmentAttributeHandle to get the default value of
     * @return the default value of the wrapped attribute
     */
    @AsOf("2.0.0")
    <T> T defaultValue(EnvironmentAttributeHandle<T> handle);
}