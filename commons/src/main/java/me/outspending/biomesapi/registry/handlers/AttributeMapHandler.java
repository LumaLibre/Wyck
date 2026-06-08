package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.registry.internal.BuilderHandler;
import me.outspending.biomesapi.wrapper.environment.attribute.NmsEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public class AttributeMapHandler implements BuilderHandler<Biome.BiomeBuilder, WrappedEnvironmentAttributeMap> {

    @Override
    public void handle(@Nullable WrappedEnvironmentAttributeMap value, Biome.BiomeBuilder key) {
        if (value == null || value.empty()) return;
        NmsEnvironmentAttributes.applyTo(key, value);
    }

    public void applyToNms(@Nullable WrappedEnvironmentAttributeMap wrappedEnvironmentAttributeMap, EnvironmentAttributeMap attributeMap) {
        if (wrappedEnvironmentAttributeMap == null || wrappedEnvironmentAttributeMap.empty()) return;
        NmsEnvironmentAttributes.applyTo(attributeMap, wrappedEnvironmentAttributeMap);
    }

    @Override
    public @Nullable WrappedEnvironmentAttributeMap build(AbstractBiome biome) {
        return null;
    }
}