package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.registry.internal.BuilderHandler;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("1.0.2")
@ApiStatus.Internal
public class SpecialEffectsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeSpecialEffects> {

    @Override
    public void handle(@Nullable BiomeSpecialEffects value, Biome.BiomeBuilder builder) {
        if (value == null) return;

        builder.specialEffects(value);
    }

    @Override
    public BiomeSpecialEffects build(AbstractBiome biome) {
        BiomeSpecialEffects.Builder builder = new BiomeSpecialEffects.Builder();
        if (biome.getFoliageColor() != null) {
            builder.foliageColorOverride(biome.getFoliageColor());
        }

        if (biome.getGrassColor() != null) {
            builder.grassColorOverride(biome.getGrassColor());
        }

        if (biome.getDryFoliageColor() != null) {
            builder.dryFoliageColorOverride(biome.getDryFoliageColor());
        }

        builder.waterColor(biome.getWaterColor());
        builder.grassColorModifier(biome.getGrassColorModifier().toNms(BiomeSpecialEffects.GrassColorModifier.class));

        return builder.build();
    }

}
