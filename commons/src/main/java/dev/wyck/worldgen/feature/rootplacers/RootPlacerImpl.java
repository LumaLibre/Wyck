package dev.wyck.worldgen.feature.rootplacers;

import dev.wyck.worldgen.feature.rootplacers.AboveRootPlacement;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType") // vanilla parity
public abstract class RootPlacerImpl implements RootPlacer {

    protected final IntProvider trunkOffsetY;
    protected final BlockStateProvider rootProvider;
    protected final Optional<AboveRootPlacement> aboveRootPlacement;

    public RootPlacerImpl(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement) {
        this.trunkOffsetY = trunkOffsetY;
        this.rootProvider = rootProvider;
        this.aboveRootPlacement = aboveRootPlacement;
    }

    @Override
    public IntProvider trunkOffsetY() {
        return trunkOffsetY;
    }

    @Override
    public BlockStateProvider rootProvider() {
        return rootProvider;
    }

    @Override
    public Optional<AboveRootPlacement> aboveRootPlacement() {
        return aboveRootPlacement;
    }
}
