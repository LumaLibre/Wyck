package dev.wyck.registry.handlers;

import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.registry.internal.BuilderHandler;
import dev.wyck.wrapper.environment.attribute.NmsEnvironmentAttributes;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public class AttributeMapHandler implements BuilderHandler<Biome.BiomeBuilder, EnvironmentAttributeMap> {

    @Override
    public void handle(@Nullable EnvironmentAttributeMap value, Biome.BiomeBuilder key) {
        if (value == null || value.empty()) return;
        NmsEnvironmentAttributes.applyTo(key, value);
    }

    @Override
    public @Nullable EnvironmentAttributeMap build(AbstractBiome biome) {
        return null;
    }
}