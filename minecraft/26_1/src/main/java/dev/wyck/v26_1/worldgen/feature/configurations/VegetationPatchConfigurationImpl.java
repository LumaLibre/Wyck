package dev.wyck.v26_1.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.configurations.VegetationPatchConfiguration;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record VegetationPatchConfigurationImpl(
    @Override Set<Material> replaceable,
    @Override BlockStateProvider groundState,
    @Override PlacedFeature vegetationFeature,
    @Override CaveSurface surface,
    @Override IntProvider depth,
    @Override float extraBottomBlockChance,
    @Override int verticalRange,
    @Override float vegetationChance,
    @Override IntProvider xzRadius,
    @Override float extraEdgeColumnChance,
    @Override @Nullable ResourceKey legacy$replaceable
) implements VegetationPatchConfiguration {
    @Override
    public Object toMinecraft() {
        Preconditions.checkNotNull(legacy$replaceable, "legacy$replaceable");

        net.minecraft.resources.Identifier location = legacy$replaceable.asHandle();
        net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> replaceableTag =
            net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, location);

        return new net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration(
            replaceableTag,
            groundState.asHandle(),
            vegetationFeature.asHandle(),
            surface.toNms(net.minecraft.world.level.levelgen.placement.CaveSurface.class),
            depth.asHandle(),
            extraBottomBlockChance,
            verticalRange,
            vegetationChance,
            xzRadius.asHandle(),
            extraEdgeColumnChance
        );
    }
}
