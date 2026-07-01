package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.keys.KeyChains;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class DensityFunctionRegistryImpl implements DensityFunctionRegistry {

    private final Lazy<FrozenRegistry> densityFunctionRegistry = FrozenRegistry.lazy("worldgen/density_function");

    @Override
    @SuppressWarnings("unchecked")
    public void register(ResourceKey key, DensityFunction function) {
        net.minecraft.world.level.levelgen.DensityFunction nms =
            (net.minecraft.world.level.levelgen.DensityFunction) function.toMinecraft();
        Identifier id = (Identifier) key.resourceLocation();
        this.densityFunctionRegistry.get().whileUnfrozen(raw -> {
            MappedRegistry<net.minecraft.world.level.levelgen.DensityFunction> registry =
                (MappedRegistry<net.minecraft.world.level.levelgen.DensityFunction>) raw.toMinecraft();

            if (!registry.containsKey(id)) {
                Registry.register(registry, id, nms);
            }
        });

        if (function instanceof DensityFunction.Keyed keyedImpl) {
            KeyChains.DENSITY_FUNCTIONS.append(keyedImpl);
        } else {
            KeyChains.DENSITY_FUNCTIONS.append(new DensityFunction.Keyed(key, function));
        }
    }
}