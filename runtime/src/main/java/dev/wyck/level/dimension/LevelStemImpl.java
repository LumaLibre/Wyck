package dev.wyck.level.dimension;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record LevelStemImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override Dimension dimension,
    @Override ChunkGenerator chunkGenerator
) implements LevelStem {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.LEVEL_STEM);

    @Override
    public net.minecraft.world.level.dimension.LevelStem toMinecraft() {
        net.minecraft.core.Holder<net.minecraft.world.level.dimension.DimensionType> dimensionTypeHolder =
            BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.DIMENSION_TYPE)
                .getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION_TYPE, dimension.resourceKey().identifier()));
        return new net.minecraft.world.level.dimension.LevelStem(
            dimensionTypeHolder,
            this.chunkGenerator.asHandle()
        );
    }

    @Override
    public LevelStem register() {
        net.minecraft.resources.Identifier id = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a LevelStem without a resource key")).identifier();

        REGISTRY.get().whileUnfrozen(r -> {
            net.minecraft.core.Registry<net.minecraft.world.level.dimension.LevelStem> registry = r.asHandle();
            if (!registry.containsKey(id)) {
                net.minecraft.core.Registry.register(registry, id, this.toMinecraft());
            }
        });
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot get key of a LevelStem without a resource key"));
    }
}
