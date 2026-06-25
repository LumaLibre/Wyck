package me.outspending.biomesapi.wrapper.level.noise;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

/**
 * Wraps a {@code NoiseRouter} by referencing registered density functions by key.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelNoiseRouter extends NmsHandle {

    Codec<LevelNoiseRouter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        DensityFunction.CODEC.optionalFieldOf("barrier", DensityFunction.zero()).forGetter(r -> r.slots().barrier()),
        DensityFunction.CODEC.optionalFieldOf("fluid_level_floodedness", DensityFunction.zero()).forGetter(r -> r.slots().fluidLevelFloodedness()),
        DensityFunction.CODEC.optionalFieldOf("fluid_level_spread", DensityFunction.zero()).forGetter(r -> r.slots().fluidLevelSpread()),
        DensityFunction.CODEC.optionalFieldOf("lava", DensityFunction.zero()).forGetter(r -> r.slots().lava()),
        DensityFunction.CODEC.optionalFieldOf("temperature", DensityFunction.zero()).forGetter(r -> r.slots().temperature()),
        DensityFunction.CODEC.optionalFieldOf("vegetation", DensityFunction.zero()).forGetter(r -> r.slots().vegetation()),
        DensityFunction.CODEC.optionalFieldOf("continents", DensityFunction.zero()).forGetter(r -> r.slots().continents()),
        DensityFunction.CODEC.optionalFieldOf("erosion", DensityFunction.zero()).forGetter(r -> r.slots().erosion()),
        DensityFunction.CODEC.optionalFieldOf("depth", DensityFunction.zero()).forGetter(r -> r.slots().depth()),
        DensityFunction.CODEC.optionalFieldOf("ridges", DensityFunction.zero()).forGetter(r -> r.slots().ridges()),
        DensityFunction.CODEC.optionalFieldOf("preliminary_surface_level", DensityFunction.zero()).forGetter(r -> r.slots().preliminarySurfaceLevel()),
        DensityFunction.CODEC.optionalFieldOf("final_density", DensityFunction.zero()).forGetter(r -> r.slots().finalDensity()),
        DensityFunction.CODEC.optionalFieldOf("vein_toggle", DensityFunction.zero()).forGetter(r -> r.slots().veinToggle()),
        DensityFunction.CODEC.optionalFieldOf("vein_ridged", DensityFunction.zero()).forGetter(r -> r.slots().veinRidged()),
        DensityFunction.CODEC.optionalFieldOf("vein_gap", DensityFunction.zero()).forGetter(r -> r.slots().veinGap())
    ).apply(instance, LevelNoiseRouter::fromCodec));

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.LevelNoiseRouterFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelNoiseRouter create(Slots slots);
    }

    /**
     * The density-function slots backing this router.
     *
     * @return the slots
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Slots slots();

    /**
     * Creates a builder for a noise router.
     *
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * The density-function slots backing this router.
     * @since 2.4.0
     */
    @NullUnmarked
    @AsOf("2.4.0")
    record Slots(
        DensityFunction barrier,
        DensityFunction fluidLevelFloodedness,
        DensityFunction fluidLevelSpread,
        DensityFunction lava,
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction preliminarySurfaceLevel,
        DensityFunction finalDensity,
        DensityFunction veinToggle,
        DensityFunction veinRidged,
        DensityFunction veinGap
    ) {}

    private static LevelNoiseRouter fromCodec(DensityFunction barrier, DensityFunction fluidLevelFloodedness, DensityFunction fluidLevelSpread, DensityFunction lava, DensityFunction temperature, DensityFunction vegetation, DensityFunction continents, DensityFunction erosion, DensityFunction depth, DensityFunction ridges, DensityFunction preliminarySurfaceLevel, DensityFunction finalDensity, DensityFunction veinToggle, DensityFunction veinRidged, DensityFunction veinGap) {
        return builder().barrier(barrier).fluidLevelFloodedness(fluidLevelFloodedness)
            .fluidLevelSpread(fluidLevelSpread).lava(lava).temperature(temperature)
            .vegetation(vegetation).continents(continents).erosion(erosion).depth(depth)
            .ridges(ridges).preliminarySurfaceLevel(preliminarySurfaceLevel).finalDensity(finalDensity)
            .veinToggle(veinToggle).veinRidged(veinRidged).veinGap(veinGap).build();
    }

    /**
     * Builder for a noise router.
     * Defaults to zero density functions for all slots.
     *
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @NullUnmarked
    @AsOf("2.4.0")
    final class Builder {

        private static final DensityFunction ZERO = DensityFunction.zero();

        private DensityFunction barrier = ZERO;
        private DensityFunction fluidLevelFloodedness = ZERO;
        private DensityFunction fluidLevelSpread = ZERO;
        private DensityFunction lava = ZERO;
        private DensityFunction temperature = ZERO;
        private DensityFunction vegetation = ZERO;
        private DensityFunction continents = ZERO;
        private DensityFunction erosion = ZERO;
        private DensityFunction depth = ZERO;
        private DensityFunction ridges = ZERO;
        private DensityFunction preliminarySurfaceLevel = ZERO;
        private DensityFunction finalDensity = ZERO;
        private DensityFunction veinToggle = ZERO;
        private DensityFunction veinRidged = ZERO;
        private DensityFunction veinGap = ZERO;

        @AsOf("2.4.0")
        public Builder barrier(DensityFunction barrier) {
            this.barrier = barrier;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelFloodedness(DensityFunction fluidLevelFloodedness) {
            this.fluidLevelFloodedness = fluidLevelFloodedness;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelSpread(DensityFunction fluidLevelSpread) {
            this.fluidLevelSpread = fluidLevelSpread;
            return this;
        }

        @AsOf("2.4.0")
        public Builder lava(DensityFunction lava) {
            this.lava = lava;
            return this;
        }

        @AsOf("2.4.0")
        public Builder temperature(DensityFunction temperature) {
            this.temperature = temperature;
            return this;
        }

        @AsOf("2.4.0")
        public Builder vegetation(DensityFunction vegetation) {
            this.vegetation = vegetation;
            return this;
        }

        @AsOf("2.4.0")
        public Builder continents(DensityFunction continents) {
            this.continents = continents;
            return this;
        }

        @AsOf("2.4.0")
        public Builder erosion(DensityFunction erosion) {
            this.erosion = erosion;
            return this;
        }

        @AsOf("2.4.0")
        public Builder depth(DensityFunction depth) {
            this.depth = depth;
            return this;
        }

        @AsOf("2.4.0")
        public Builder ridges(DensityFunction ridges) {
            this.ridges = ridges;
            return this;
        }

        @AsOf("2.4.0")
        public Builder preliminarySurfaceLevel(DensityFunction preliminarySurfaceLevel) {
            this.preliminarySurfaceLevel = preliminarySurfaceLevel;
            return this;
        }

        @AsOf("2.4.0")
        public Builder finalDensity(DensityFunction finalDensity) {
            this.finalDensity = finalDensity;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinToggle(DensityFunction veinToggle) {
            this.veinToggle = veinToggle;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinRidged(DensityFunction veinRidged) {
            this.veinRidged = veinRidged;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinGap(DensityFunction veinGap) {
            this.veinGap = veinGap;
            return this;
        }

        @AsOf("2.4.0")
        public LevelNoiseRouter build() {
            Slots slots = new Slots(
                this.barrier,
                this.fluidLevelFloodedness,
                this.fluidLevelSpread,
                this.lava,
                this.temperature,
                this.vegetation,
                this.continents,
                this.erosion,
                this.depth,
                this.ridges,
                this.preliminarySurfaceLevel,
                this.finalDensity,
                this.veinToggle,
                this.veinRidged,
                this.veinGap
            );
            return WIRE.get().create(slots);
        }
    }
}