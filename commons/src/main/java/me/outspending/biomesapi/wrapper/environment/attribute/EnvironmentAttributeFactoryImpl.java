package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.logging.Logger;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class EnvironmentAttributeFactoryImpl implements EnvironmentAttributeFactory {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentAttributeFactoryImpl.class.getName());

    /**
     * Sentinel handle returned when an attribute id isn't found in the registry.
     * The underlying NMS attribute is a never-registered Boolean placeholder; reading
     * its value/key/default may surface its sentinel-ness, but the handle is non-null
     * so callers don't need to null-check.
     */
    public static final EnvironmentAttributeHandleImpl<?> UNSUPPORTED =
            new EnvironmentAttributeHandleImpl<>(
                    EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                            .defaultValue(false)
                            .build()
            );

    @Override
    @SuppressWarnings("unchecked")
    public <T> EnvironmentAttributeHandle<T> byKey(String key) {
        Identifier id = Identifier.withDefaultNamespace(key);
        EnvironmentAttribute<?> nms = BuiltInRegistries.ENVIRONMENT_ATTRIBUTE.getValue(id);
        if (nms == null) {
            LOGGER.warning("Environment attribute with key '" + key + "' not found in registry; this attribute is not supported by this Minecraft version!");
            return (EnvironmentAttributeHandle<T>) UNSUPPORTED; // backward compat
        }
        return new EnvironmentAttributeHandleImpl<>((EnvironmentAttribute<T>) nms);
    }

    @Override
    public <T> T defaultValue(EnvironmentAttributeHandle<T> handle) {
        return ((EnvironmentAttributeHandleImpl<T>) handle).nms().defaultValue();
    }
}