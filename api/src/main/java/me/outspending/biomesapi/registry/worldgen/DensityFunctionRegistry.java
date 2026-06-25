package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface DensityFunctionRegistry {

    @ApiStatus.Internal
    WireProvider<DensityFunctionRegistry> WIRE = WireProvider.create("me.outspending.biomesapi.registry.worldgen.DensityFunctionRegistryImpl");

    @AsOf("2.4.0")
    void register(ResourceKey key, DensityFunction function);

    @AsOf("2.4.0")
    static DensityFunctionRegistry registry() {
        return WIRE.get();
    }
}