package dev.wyck.worldgen.feature.rootplacers;

import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType") // vanilla parity
public final class MangroveRootPlacerImpl extends RootPlacerImpl implements MangroveRootPlacer {

    private final MangroveRootPlacement mangroveRootPlacement;

    public MangroveRootPlacerImpl(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement, MangroveRootPlacement mangroveRootPlacement) {
        super(trunkOffsetY, rootProvider, aboveRootPlacement);
        this.mangroveRootPlacement = mangroveRootPlacement;
    }

    @Override
    public MangroveRootPlacement mangroveRootPlacement() {
        return mangroveRootPlacement;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer(
            trunkOffsetY.asHandle(),
            rootProvider.asHandle(),
            aboveRootPlacement.map(AboveRootPlacement::<net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement>asHandle),
            mangroveRootPlacement.asHandle()
        );
    }
}
