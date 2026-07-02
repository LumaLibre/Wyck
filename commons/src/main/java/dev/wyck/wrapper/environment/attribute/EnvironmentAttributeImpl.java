package dev.wyck.wrapper.environment.attribute;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.internal.Wrapper;
import net.minecraft.world.attribute.AttributeTypes;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Logger;

@NullMarked
@ApiStatus.Internal
public class EnvironmentAttributeImpl<V, U> implements EnvironmentAttribute<V> {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.ENVIRONMENT_ATTRIBUTE);
    private static final net.minecraft.world.attribute.EnvironmentAttribute<?> UNSUPPORTED = net.minecraft.world.attribute.EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).build();
    private static final Logger LOGGER = Logger.getLogger(EnvironmentAttributeImpl.class.getName());

    private final ResourceKey key;
    private final @Nullable Converter<V, U> converter;
    private final net.minecraft.world.attribute.EnvironmentAttribute<U> nms;
    private @MonotonicNonNull V value;

    @SuppressWarnings("unchecked")
    public EnvironmentAttributeImpl(ResourceKey key, @Nullable Converter<V, U> converter) {
        this.key = key;
        this.converter = converter;
        net.minecraft.world.attribute.EnvironmentAttribute<U> nullableNms = REGISTRY.get().retrieve(key);
        if (nullableNms == null) {
            LOGGER.warning("Environment attribute with key '" + key + "' not found in registry; this attribute is not supported by this Minecraft version!");
            this.nms = (net.minecraft.world.attribute.EnvironmentAttribute<U>) UNSUPPORTED;
        } else {
            this.nms = nullableNms;
        }

        if (converter == null) {
            this.value = (V) nms.defaultValue(); // 🙏
        }
    }

    @Override
    public ResourceKey key() {
        return this.key;
    }

    @Override
    public @Nullable V value() {
        return this.value;
    }

    @Override
    public void value(V value) {
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V defaultValue() {
        Object defaultValue = nms.defaultValue();
        if (isBasic(defaultValue)) {
            return (V) defaultValue;
        }
        // TODO: NMS decoders
        throw new UnsupportedOperationException("default value reversal not implemented for " + key);
    }

    @SuppressWarnings("unchecked")
    public U minecraftValue() {
        if (this.converter == null && this.value != null) {
            return (U) this.value;
        } else if (this.converter == null) {
            throw new IllegalStateException("No converter defined");
        }
        return this.converter.convert(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <SHADOWED> SHADOWED minecraftDefaultValue() {
        return (SHADOWED) this.nms.defaultValue();
    }

    @Override
    public Object toMinecraft() {
        return this.nms;
    }

    @Override
    public boolean syncable() {
        return this.nms.isSyncable();
    }

    @Override
    public boolean spatiallyInterpolated() {
        return this.nms.isSpatiallyInterpolated();
    }

    @Override
    public boolean positional() {
        return this.nms.isPositional();
    }

    @Override
    public String toString() {
        return this.nms.toString();
    }

    private static boolean isBasic(Object value) {
        return value instanceof Number || value instanceof Boolean || value instanceof Character || value instanceof String;
    }
}