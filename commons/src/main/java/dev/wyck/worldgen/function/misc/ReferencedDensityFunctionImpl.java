package dev.wyck.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ReferencedDensityFunctionImpl(@Override ResourceKey key) implements ReferencedDensityFunction {

    @Override
    public Object toMinecraft() {
        Identifier id = key.identifier();
        Registry<DensityFunction> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        return new DensityFunctions.HolderHolder(registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.DENSITY_FUNCTION, id)));
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(key);
    }
}
