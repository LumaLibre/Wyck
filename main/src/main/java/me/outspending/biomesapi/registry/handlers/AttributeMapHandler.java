package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeMapHandler implements BuilderHandler<Biome.BiomeBuilder, WrappedEnvironmentAttributeMap> {

    @Override
    public void handle(WrappedEnvironmentAttributeMap value, Biome.@NotNull BiomeBuilder key) {
        if (value == null || value.empty()) return;


        value.applyToBiomeBuilder(key);
    }


    public void apply(WrappedEnvironmentAttributeMap wrappedEnvironmentAttributeMap, EnvironmentAttributeMap attributeMap) {
        if (wrappedEnvironmentAttributeMap == null || wrappedEnvironmentAttributeMap.empty()) return;

        wrappedEnvironmentAttributeMap.applyToMap(attributeMap);
    }


    @Override
    public @Nullable WrappedEnvironmentAttributeMap build(@NotNull CustomBiome biome) {
        return null;
    }
}
