package dev.wyck.biome;

import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.environment.GrassColorModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BiomeSpecialEffectsImpl(
    @Override int waterColor,
    @Override Optional<Integer> foliageColorOverride,
    @Override Optional<Integer> dryFoliageColorOverride,
    @Override Optional<Integer> grassColorOverride,
    @Override GrassColorModifier grassColorModifier
) implements BiomeSpecialEffects {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.biome.BiomeSpecialEffects(
            waterColor,
            foliageColorOverride,
            dryFoliageColorOverride,
            grassColorOverride,
            grassColorModifier.toNms(net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.class)
        );
    }
}
