package dev.wyck.wrapper.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NullMarked
@AsOf("3.0.0")
public interface MangroveRootPlacement extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<MangroveRootPlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.rootplacers.MangroveRootPlacementImpl");

    @AsOf("3.0.0")
    Set<Material> canGrowThrough();

    @AsOf("3.0.0")
    Set<Material> muddyRootsIn();

    @AsOf("3.0.0")
    BlockStateProvider muddyRootsProvider();

    @AsOf("3.0.0")
    int maxRootWidth();

    @AsOf("3.0.0")
    int maxRootLength();

    @AsOf("3.0.0")
    float randomSkewChance();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static MangroveRootPlacement of(Set<Material> canGrowThrough, Set<Material> muddyRootsIn, BlockStateProvider muddyRootsProvider, int maxRootWidth, int maxRootLength) {
        return WIRE.construct(canGrowThrough, muddyRootsIn, muddyRootsProvider, maxRootWidth, maxRootLength);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder {
        private Set<Material> canGrowThrough = new HashSet<>();
        private Set<Material> muddyRootsIn = new HashSet<>();
        private @Nullable BlockStateProvider muddyRootsProvider;
        private int maxRootWidth = 1;
        private int maxRootLength = 1;
        private float randomSkewChance = 0.0F;

        private Builder() {}

        private Builder(MangroveRootPlacement rootPlacement) {
            this.canGrowThrough.addAll(rootPlacement.canGrowThrough());
            this.muddyRootsIn.addAll(rootPlacement.muddyRootsIn());
            this.muddyRootsProvider = rootPlacement.muddyRootsProvider();
            this.maxRootWidth = rootPlacement.maxRootWidth();
            this.maxRootLength = rootPlacement.maxRootLength();
        }

        @AsOf("3.0.0")
        public Builder canGrowThrough(Set<Material> canGrowThrough) {
            this.canGrowThrough = canGrowThrough;
            return this;
        }

        @AsOf("3.0.0")
        public Builder muddyRootsIn(Set<Material> muddyRootsIn) {
            this.muddyRootsIn = muddyRootsIn;
            return this;
        }

        @AsOf("3.0.0")
        public Builder muddyRootsProvider(BlockStateProvider muddyRootsProvider) {
            this.muddyRootsProvider = muddyRootsProvider;
            return this;
        }

        @AsOf("3.0.0")
        public Builder maxRootWidth(int maxRootWidth) {
            this.maxRootWidth = maxRootWidth;
            return this;
        }

        @AsOf("3.0.0")
        public Builder maxRootLength(int maxRootLength) {
            this.maxRootLength = maxRootLength;
            return this;
        }

        @AsOf("3.0.0")
        public Builder randomSkewChance(float randomSkewChance) {
            this.randomSkewChance = randomSkewChance;
            return this;
        }

        // Friendly builder methods

        @AsOf("3.0.0")
        public Builder canGrowThrough(Material... canGrowThrough) {
            Collections.addAll(this.canGrowThrough, canGrowThrough);
            return this;
        }

        @AsOf("3.0.0")
        public Builder muddyRootsIn(Material... muddyRootsIn) {
            Collections.addAll(this.muddyRootsIn, muddyRootsIn);
            return this;
        }

        @AsOf("3.0.0")
        public MangroveRootPlacement build() {
            Preconditions.checkNotNull(muddyRootsProvider, "muddyRootsProvider must be set");
            Preconditions.checkArgument(maxRootWidth >= 1 && maxRootWidth <= 12, "maxRootWidth must be between 1 and 12");
            Preconditions.checkArgument(maxRootLength >= 1 && maxRootLength <= 64, "maxRootLength must be between 1 and 64");
            Preconditions.checkArgument(randomSkewChance >= 0.0F && randomSkewChance <= 1.0F, "randomSkewChance must be between 0.0F and 1.0F");
            return of(canGrowThrough, muddyRootsIn, muddyRootsProvider, maxRootWidth, maxRootLength);
        }
    }
}
