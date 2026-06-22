package me.outspending.biomesapi.wrapper.level.noise;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LevelNoiseRouterImpl(LevelNoiseRouter.Slots slots) implements LevelNoiseRouter {

    @Override
    public Object toMinecraft() {
        Registry<DensityFunction> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        return new NoiseRouter(
            resolve(registry, this.slots.barrier()),
            resolve(registry, this.slots.fluidLevelFloodedness()),
            resolve(registry, this.slots.fluidLevelSpread()),
            resolve(registry, this.slots.lava()),
            resolve(registry, this.slots.temperature()),
            resolve(registry, this.slots.vegetation()),
            resolve(registry, this.slots.continents()),
            resolve(registry, this.slots.erosion()),
            resolve(registry, this.slots.depth()),
            resolve(registry, this.slots.ridges()),
            resolve(registry, this.slots.preliminarySurfaceLevel()),
            resolve(registry, this.slots.finalDensity()),
            resolve(registry, this.slots.veinToggle()),
            resolve(registry, this.slots.veinRidged()),
            resolve(registry, this.slots.veinGap())
        );
    }

    private static DensityFunction resolve(Registry<DensityFunction> registry, ResourceKey key) {
        Preconditions.checkNotNull(key, "noise router slot is missing a density function key");
        Identifier id = (Identifier) key.resourceLocation();
        Holder<DensityFunction> holder = registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.DENSITY_FUNCTION, id));
        return new DensityFunctions.HolderHolder(holder);
    }
}