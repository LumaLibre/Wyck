package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NullMarked
@AsOf("3.0.0")
public interface UpwardsBranchingTrunkPlacer extends TrunkPlacer {

    @ApiStatus.Internal
    ConstructWireProvider<UpwardsBranchingTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.feature.trunkplacers.UpwardsBranchingTrunkPlacerImpl");

    @AsOf("3.0.0")
    IntProvider extraBranchSteps();

    @AsOf("3.0.0")
    float placeBranchPerLogProbability();

    @AsOf("3.0.0")
    IntProvider extraBranchLength();

    @AsOf("3.0.0")
    Set<Material> canGrowThrough();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static UpwardsBranchingTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB, IntProvider extraBranchSteps, float placeBranchPerLogProbability, IntProvider extraBranchLength, Set<Material> canGrowThrough) {
        return WIRE.construct(baseHeight, heightRandA, heightRandB, extraBranchSteps, placeBranchPerLogProbability, extraBranchLength, canGrowThrough);
    }

    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, UpwardsBranchingTrunkPlacer> {
        private IntProvider extraBranchSteps = IntProvider.constant(0);
        private float placeBranchPerLogProbability = 0.0F;
        private IntProvider extraBranchLength = IntProvider.constant(0);
        private Set<Material> canGrowThrough = new HashSet<>();

        public Builder() {}

        public Builder(UpwardsBranchingTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.extraBranchSteps = trunkPlacer.extraBranchSteps();
            this.placeBranchPerLogProbability = trunkPlacer.placeBranchPerLogProbability();
            this.extraBranchLength = trunkPlacer.extraBranchLength();
            this.canGrowThrough = new HashSet<>(trunkPlacer.canGrowThrough());
        }

        @AsOf("3.0.0")
        public Builder extraBranchSteps(IntProvider extraBranchSteps) {
            this.extraBranchSteps = extraBranchSteps;
            return this;
        }

        @AsOf("3.0.0")
        public Builder placeBranchPerLogProbability(float placeBranchPerLogProbability) {
            this.placeBranchPerLogProbability = placeBranchPerLogProbability;
            return this;
        }

        @AsOf("3.0.0")
        public Builder extraBranchLength(IntProvider extraBranchLength) {
            this.extraBranchLength = extraBranchLength;
            return this;
        }

        @AsOf("3.0.0")
        public Builder canGrowThrough(Set<Material> canGrowThrough) {
            this.canGrowThrough = canGrowThrough;
            return this;
        }

        @AsOf("3.0.0")
        public Builder canGrowThrough(Material... canGrowThrough) {
            Collections.addAll(this.canGrowThrough, canGrowThrough);
            return this;
        }

        @Override
        protected UpwardsBranchingTrunkPlacer create() {
            Preconditions.checkArgument(extraBranchSteps.minInclusive() >= 0, "extraBranchSteps must be positive");
            Preconditions.checkArgument(placeBranchPerLogProbability >= 0.0F && placeBranchPerLogProbability <= 1.0F, "placeBranchPerLogProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(extraBranchLength.minInclusive() >= 0, "extraBranchLength must be positive");
            return of(baseHeight, heightRandA, heightRandB, extraBranchSteps, placeBranchPerLogProbability, extraBranchLength, canGrowThrough);
        }
    }
}
