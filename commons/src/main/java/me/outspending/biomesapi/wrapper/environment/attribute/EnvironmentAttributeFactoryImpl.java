package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

@WireFactory
public final class EnvironmentAttributeFactoryImpl implements EnvironmentAttributeFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> @NotNull EnvironmentAttributeHandle<T> byKey(String key) {
        Identifier id = Identifier.withDefaultNamespace(key);
        EnvironmentAttribute<?> nms = BuiltInRegistries.ENVIRONMENT_ATTRIBUTE.getValue(id);
        if (nms == null) {
            return (EnvironmentAttributeHandle<T>) EnvironmentAttributeHandleImpl.UNSUPPORTED; // backward compat
        }
        return new EnvironmentAttributeHandleImpl<>((EnvironmentAttribute<@NotNull T>) nms);
    }

    @Override
    public <T> @NotNull T defaultValue(EnvironmentAttributeHandle<T> handle) {
        return ((EnvironmentAttributeHandleImpl<T>) handle).nms().defaultValue();
    }
}