package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.jetbrains.annotations.NotNull;

@AsOf("1.0.2")
public class SpecialEffectsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeSpecialEffects> {

    @Override
    public void handle(BiomeSpecialEffects value, @NotNull Biome.BiomeBuilder builder) {
        if (value == null) return;

        builder.specialEffects(value);
    }

    @Override
    public BiomeSpecialEffects build(@NotNull CustomBiome biome) {
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
