package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record EnvironmentAttributeHandleImpl<T>(EnvironmentAttribute<T> nms) implements EnvironmentAttributeHandle<T> {


    @Override
    public T defaultValue() {
        return nms.defaultValue();
    }

    @Override
    public boolean isClientAware() {
        return nms.isSyncable();
    }

    @Override
    public String key() {
        Identifier id = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.ENVIRONMENT_ATTRIBUTE).getKey(nms);
        if (id == null) {
            return "unknown_attribute";
        }
        return id.getPath();
    }

    @Override
    public String toString() {
        return nms.toString();
    }
}