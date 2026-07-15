package dev.wyck.worldgen.feature.configurations;


import dev.wyck.worldgen.feature.configurations.TreeConfiguration;
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
public record TreeConfigurationImpl(
    @Override BlockStateProvider trunkProvider,
    @Override TrunkPlacer trunkPlacer,
    @Override BlockStateProvider foliageProvider,
    @Override FoliagePlacer foliagePlacer,
    @Override Optional<RootPlacer>rootPlacer,
    @Override FeatureSize minimumSize,
    @Override List<TreeDecorator> decorators,
    @Override boolean ignoreVines,
    @Override BlockStateProvider belowTrunkProvider
) implements TreeConfiguration {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder builder = new net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder(
            trunkProvider.asHandle(),
            trunkPlacer.asHandle(),
            foliageProvider.asHandle(),
            foliagePlacer.asHandle(),
            rootPlacer.map(RootPlacer::asHandle),
            minimumSize.asHandle(),
            belowTrunkProvider.asHandle()
        ).decorators(decorators.stream().map(TreeDecorator::<net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator>asHandle).toList());

        if (ignoreVines) {
            builder.ignoreVines();
        }

        return builder.build();
    }
}
