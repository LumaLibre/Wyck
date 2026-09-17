package dev.wyck.worldgen.feature.configurations;


import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record TreeFeatureImpl(
    @Override BlockStateProvider trunkProvider,
    @Override TrunkPlacer trunkPlacer,
    @Override BlockStateProvider foliageProvider,
    @Override FoliagePlacer foliagePlacer,
    @Override Optional<RootPlacer>rootPlacer,
    @Override FeatureSize minimumSize,
    @Override List<TreeDecorator> decorators,
    @Override boolean ignoreVines,
    @Override BlockStateProvider belowTrunkProvider
) implements TreeFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.TreeFeature(
            net.minecraft.core.Holder.direct(
                trunkProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            ),
            trunkPlacer.asHandle(),
            net.minecraft.core.Holder.direct(
                foliageProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            ),
            foliagePlacer.asHandle(),
            rootPlacer.map(RootPlacer::asHandle),
            minimumSize.asHandle(),
            decorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList(),
            ignoreVines,
            net.minecraft.core.Holder.direct(
                belowTrunkProvider.<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle()
            )
        );
    }
}
