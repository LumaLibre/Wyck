package me.outspending.biomesapi.wrapper.environment.attribute;

import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

public record EnvironmentAttributeHandleImpl<T>(EnvironmentAttribute<@NotNull T> nms) implements EnvironmentAttributeHandle<T> {

    public static final EnvironmentAttribute<@NotNull Boolean> UNSUPPORTED =
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
                    .build();


    @Override
    public T defaultValue() {
        return nms.defaultValue();
    }

    @Override
    public boolean isClientAware() {
        return nms.isSyncable();
    }

    @Override
    public @NotNull String toString() {
        return nms.toString();
    }
}