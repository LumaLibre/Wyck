package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.jetbrains.annotations.NotNull;

@AsOf("0.0.24")
public class SpecialEffectsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeSpecialEffects> {

    @Override
    public void handle(BiomeSpecialEffects value, @NotNull Biome.BiomeBuilder builder) {
        if (value == null) return;

        builder.specialEffects(value);
    }

    @Override
    public BiomeSpecialEffects build(@NotNull CustomBiome biome) {
        // TODO: Expand this to include more special effects settings
        BiomeSpecialEffects.Builder builder = new BiomeSpecialEffects.Builder()
                .foliageColorOverride(biome.getFoliageColor())
                .waterColor(biome.getWaterColor())
                .grassColorOverride(biome.getGrassColor())
                .grassColorModifier(biome.getGrassColorModifier().getDelegateModifier());

        return builder.build();
    }

}
