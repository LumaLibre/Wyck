package dev.wyck.worldgen.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * The density-function routes used by Minecraft 26.3 terrain generation.
 * Aquifer density functions are represented separately by {@link AquiferSettings}.
 *
 * @since 2.4.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseRouter extends Wrapper {

    /**
     * The density function used for the temperature climate axis.
     * @return the temperature density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction temperature();

    /**
     * The density function used for the vegetation climate axis.
     * @return the vegetation density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction vegetation();

    /**
     * The density function used for the continentalness climate axis.
     * @return the continentalness density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction continents();

    /**
     * The density function used for the erosion climate axis.
     * @return the erosion density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction erosion();

    /**
     * The density function used for the depth climate axis.
     * @return the depth density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction depth();

    /**
     * The density function used for the ridges climate axis.
     * @return the ridges density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction ridges();

    /**
     * The density function used to estimate the terrain surface level in a chunk.
     * @return the chunk surface-level density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction chunkSurfaceLevel();

    /**
     * The final density function used to decide whether terrain is solid.
     * @return the final terrain density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction finalDensity();

    /**
     * Converts this noise router back to a builder.
     * @return a builder containing this router's values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a noise router.
     * @param temperature the temperature density function
     * @param vegetation the vegetation density function
     * @param continents the continentalness density function
     * @param erosion the erosion density function
     * @param depth the depth density function
     * @param ridges the ridges density function
     * @param chunkSurfaceLevel the chunk surface-level density function
     * @param finalDensity the final terrain density function
     * @return a new noise router
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static NoiseRouter of(
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction chunkSurfaceLevel,
        DensityFunction finalDensity
    ) {
        record Holder() {
            static final ConstructWireProvider<NoiseRouter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.NoiseRouterImpl");
        }
        return Holder.WIRE.construct(
            temperature,
            vegetation,
            continents,
            erosion,
            depth,
            ridges,
            chunkSurfaceLevel,
            finalDensity
        );
    }

    /**
     * Reads a Minecraft noise router.
     * @param minecraftRouter the Minecraft noise router to read
     * @return the decoded noise router
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static NoiseRouter decode(Object minecraftRouter) {
        record Holder() {
            static final Decoder<NoiseRouter> DECODER = Decoder.create(
                "dev.wyck.decode.worldgen.noise.NoiseRouterDecoder"
            );
        }
        return Holder.DECODER.decode(minecraftRouter);
    }

    /**
     * Creates a new noise router builder.
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NoiseRouter}.
     *
     * @since 2.4.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    final class Builder {
        private DensityFunction temperature = DensityFunction.zero();
        private DensityFunction vegetation = DensityFunction.zero();
        private DensityFunction continents = DensityFunction.zero();
        private DensityFunction erosion = DensityFunction.zero();
        private DensityFunction depth = DensityFunction.zero();
        private DensityFunction ridges = DensityFunction.zero();
        private DensityFunction chunkSurfaceLevel = DensityFunction.zero();
        private DensityFunction finalDensity = DensityFunction.zero();

        public Builder() {}

        /**
         * Creates a builder containing the values of an existing noise router.
         * @param router the noise router to copy
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder(NoiseRouter router) {
            this.temperature = router.temperature();
            this.vegetation = router.vegetation();
            this.continents = router.continents();
            this.erosion = router.erosion();
            this.depth = router.depth();
            this.ridges = router.ridges();
            this.chunkSurfaceLevel = router.chunkSurfaceLevel();
            this.finalDensity = router.finalDensity();
        }

        /**

         * Sets the temperature density function.
         * @param temperature the temperature density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder temperature(DensityFunction temperature) {
            this.temperature = temperature;
            return this;
        }

        /**

         * Sets the vegetation density function.
         * @param vegetation the vegetation density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder vegetation(DensityFunction vegetation) {
            this.vegetation = vegetation;
            return this;
        }

        /**

         * Sets the continentalness density function.
         * @param continents the continentalness density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder continents(DensityFunction continents) {
            this.continents = continents;
            return this;
        }

        /**

         * Sets the erosion density function.
         * @param erosion the erosion density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder erosion(DensityFunction erosion) {
            this.erosion = erosion;
            return this;
        }

        /**

         * Sets the depth density function.
         * @param depth the depth density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder depth(DensityFunction depth) {
            this.depth = depth;
            return this;
        }

        /**

         * Sets the ridges density function.
         * @param ridges the ridges density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder ridges(DensityFunction ridges) {
            this.ridges = ridges;
            return this;
        }

        /**

         * Sets the chunk surface-level density function.
         * @param chunkSurfaceLevel the chunk surface-level density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder chunkSurfaceLevel(DensityFunction chunkSurfaceLevel) {
            this.chunkSurfaceLevel = chunkSurfaceLevel;
            return this;
        }

        /**

         * Sets the final terrain density function.
         * @param finalDensity the final terrain density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder finalDensity(DensityFunction finalDensity) {
            this.finalDensity = finalDensity;
            return this;
        }

        /**
         * Builds the noise router.
         * @return the noise router
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseRouter build() {
            return NoiseRouter.of(
                temperature,
                vegetation,
                continents,
                erosion,
                depth,
                ridges,
                chunkSurfaceLevel,
                finalDensity
            );
        }
    }
}
