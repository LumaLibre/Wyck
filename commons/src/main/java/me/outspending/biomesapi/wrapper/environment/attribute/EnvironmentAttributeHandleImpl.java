package me.outspending.biomesapi.wrapper.environment.attribute;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

public record EnvironmentAttributeHandleImpl<T>(EnvironmentAttribute<@NotNull T> nms) implements EnvironmentAttributeHandle<T> {


    @Override
    public @NotNull T defaultValue() {
        return nms.defaultValue();
    }

    @Override
    public boolean isClientAware() {
        return nms.isSyncable();
    }

    @Override
    public @NotNull String key() {
        Identifier id = BuiltInRegistries.ENVIRONMENT_ATTRIBUTE.getKey(nms);
        if (id == null) {
            throw new IllegalStateException("Attribute '" + nms + "' in registry, but it wasn't found");
        }
        return id.getPath();
    }

    @Override
    public @NotNull String toString() {
        return nms.toString();
    }
}