package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PointedDripstoneConfigurationImpl(
    @Override float chanceOfTallerDripstone,
    @Override float chanceOfDirectionalSpread,
    @Override float chanceOfSpreadRadius2,
    @Override float chanceOfSpreadRadius3
) implements PointedDripstoneConfiguration {

    private static final Lazy<net.minecraft.world.level.block.state.BlockState> DRIPSTONE_BLOCK = Lazy.of(net.minecraft.world.level.block.Blocks.DRIPSTONE_BLOCK::defaultBlockState);
    private static final Lazy<net.minecraft.world.level.block.state.BlockState> POINTED_DRIPSTONE = Lazy.of(net.minecraft.world.level.block.Blocks.POINTED_DRIPSTONE::defaultBlockState);
    private static final Lazy<net.minecraft.core.HolderSet<net.minecraft.world.level.block.Block>> DRIPSTONE_REPLACEABLE = Lazy.of(() -> {
        net.minecraft.core.Registry<net.minecraft.world.level.block.Block> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.BLOCK);
        return registry.getOrThrow(net.minecraft.tags.BlockTags.DRIPSTONE_REPLACEABLE);
    });

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.SpeleothemConfiguration(
            DRIPSTONE_BLOCK.get(),
            POINTED_DRIPSTONE.get(),
            DRIPSTONE_REPLACEABLE.get(),
            chanceOfTallerDripstone,
            chanceOfDirectionalSpread,
            chanceOfSpreadRadius2,
            chanceOfSpreadRadius3
        );
    }
}