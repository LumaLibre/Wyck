package dev.wyck.v1_21_11.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.feature.configurations.VegetationPatchConfiguration;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record VegetationPatchConfigurationImpl(
    @Override TagSet<Material> replaceable,
    @Override BlockStateProvider groundState,
    @Override PlacedFeature vegetationFeature,
    @Override CaveSurface surface,
    @Override IntProvider depth,
    @Override float extraBottomBlockChance,
    @Override int verticalRange,
    @Override float vegetationChance,
    @Override IntProvider xzRadius,
    @Override float extraEdgeColumnChance
) implements VegetationPatchConfiguration {
    @Override
    public Object toMinecraft() {
        Preconditions.checkState(replaceable.isTag(), "replaceable must be a tag in this version of Minecraft");

        return new net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration(
            replaceable.asTagKey(),
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
