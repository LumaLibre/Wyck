package me.outspending.biomesapi.wrapper.environment.attribute;

import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.NotNull;

public record EnvironmentAttributeHandleImpl<T>(EnvironmentAttribute<@NotNull T> nms) implements EnvironmentAttributeHandle<T> {

}