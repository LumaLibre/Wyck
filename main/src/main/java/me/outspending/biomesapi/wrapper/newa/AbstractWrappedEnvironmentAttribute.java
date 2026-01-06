package me.outspending.biomesapi.wrapper.newa;

import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

public interface AbstractWrappedEnvironmentAttribute<T> {

    EnvironmentAttribute<@NotNull T> getAttribute();

    @NotNull T getValue();

    void setValue(@NotNull Object value);
}
