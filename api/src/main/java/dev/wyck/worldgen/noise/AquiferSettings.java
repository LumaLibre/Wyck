package dev.wyck.worldgen.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The density functions used by Minecraft's optional aquifer system.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface AquiferSettings extends Wrapper {

    /**
     * The density function used to create barriers between neighboring aquifers.
     * @return the aquifer barrier density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction barrier();

    /**
     * The density function controlling how readily an area becomes flooded.
     * @return the fluid-level floodedness density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction fluidLevelFloodedness();

    /**
     * The density function controlling variation in aquifer fluid levels.
     * @return the fluid-level spread density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction fluidLevelSpread();

    /**
     * The density function controlling where aquifers contain lava.
     * @return the lava density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction lava();

    /**
     * The density function controlling areas excluded from aquifer generation.
     * @return the aquifer exclusion density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction exclusion();

    /**
     * The density function used to determine aquifer surface levels.
     * @return the aquifer surface-level density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction surfaceLevel();

    /**
     * Converts these aquifer settings back into a builder.
     * @return a builder containing these settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates aquifer settings.
     * @param barrier the barrier density function
     * @param fluidLevelFloodedness the fluid-level floodedness density function
     * @param fluidLevelSpread the fluid-level spread density function
     * @param lava the lava density function
     * @param exclusion the aquifer exclusion density function
     * @param surfaceLevel the aquifer surface-level density function
     * @return new aquifer settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static AquiferSettings of(
        DensityFunction barrier,
        DensityFunction fluidLevelFloodedness,
        DensityFunction fluidLevelSpread,
        DensityFunction lava,
        DensityFunction exclusion,
        DensityFunction surfaceLevel
    ) {
        record Holder() {
            static final ConstructWireProvider<AquiferSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.AquiferSettingsImpl");
        }
        return Holder.WIRE.construct(barrier, fluidLevelFloodedness, fluidLevelSpread, lava, exclusion, surfaceLevel);
    }

    /**
     * Creates a new aquifer settings builder.
     * @return a new builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft aquifer settings.
     * @param minecraftAquiferSettings the Minecraft aquifer settings to read
     * @return the decoded aquifer settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static AquiferSettings decode(Object minecraftAquiferSettings) {
        record Holder() {
            static final Decoder<AquiferSettings> DECODER = Decoder.create(
                "dev.wyck.decode.worldgen.noise.AquiferSettingsDecoder"
            );
        }
        return Holder.DECODER.decode(minecraftAquiferSettings);
    }

    /**
     * Builder for {@link AquiferSettings}.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder {
        private @Nullable DensityFunction barrier;
        private @Nullable DensityFunction fluidLevelFloodedness;
        private @Nullable DensityFunction fluidLevelSpread;
        private @Nullable DensityFunction lava;
        private @Nullable DensityFunction exclusion;
        private @Nullable DensityFunction surfaceLevel;

        /**
         * Creates an empty aquifer settings builder.
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder() {}

        /**
         * Creates a builder containing the values of existing aquifer settings.
         * @param settings the aquifer settings to copy
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder(AquiferSettings settings) {
            this.barrier = settings.barrier();
            this.fluidLevelFloodedness = settings.fluidLevelFloodedness();
            this.fluidLevelSpread = settings.fluidLevelSpread();
            this.lava = settings.lava();
            this.exclusion = settings.exclusion();
            this.surfaceLevel = settings.surfaceLevel();
        }

        /**
         * Sets the density function used to create barriers between neighboring aquifers.
         * @param barrier the aquifer barrier density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder barrier(DensityFunction barrier) {
            this.barrier = barrier;
            return this;
        }

        /**
         * Sets the density function controlling how readily an area becomes flooded.
         * @param fluidLevelFloodedness the fluid-level floodedness density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder fluidLevelFloodedness(DensityFunction fluidLevelFloodedness) {
            this.fluidLevelFloodedness = fluidLevelFloodedness;
            return this;
        }

        /**
         * Sets the density function controlling variation in aquifer fluid levels.
         * @param fluidLevelSpread the fluid-level spread density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder fluidLevelSpread(DensityFunction fluidLevelSpread) {
            this.fluidLevelSpread = fluidLevelSpread;
            return this;
        }

        /**
         * Sets the density function controlling where aquifers contain lava.
         * @param lava the lava density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder lava(DensityFunction lava) {
            this.lava = lava;
            return this;
        }

        /**
         * Sets the density function controlling areas excluded from aquifer generation.
         * @param exclusion the aquifer exclusion density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder exclusion(DensityFunction exclusion) {
            this.exclusion = exclusion;
            return this;
        }

        /**
         * Sets the density function used to determine aquifer surface levels.
         * @param surfaceLevel the aquifer surface-level density function
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder surfaceLevel(DensityFunction surfaceLevel) {
            this.surfaceLevel = surfaceLevel;
            return this;
        }

        /**
         * Builds the aquifer settings.
         * @return the built aquifer settings
         * @throws NullPointerException if any required density function has not been set
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public AquiferSettings build() {
            return AquiferSettings.of(
                Preconditions.checkNotNull(this.barrier, "barrier must be set"),
                Preconditions.checkNotNull(this.fluidLevelFloodedness, "fluidLevelFloodedness must be set"),
                Preconditions.checkNotNull(this.fluidLevelSpread, "fluidLevelSpread must be set"),
                Preconditions.checkNotNull(this.lava, "lava must be set"),
                Preconditions.checkNotNull(this.exclusion, "exclusion must be set"),
                Preconditions.checkNotNull(this.surfaceLevel, "surfaceLevel must be set")
            );
        }
    }
}
