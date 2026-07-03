package dev.wyck.wrapper.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.internal.FriendlyColorUtil;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.internal.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Various biome-specific visuals as they appear in vanilla.
 *
 * @since 2.5.0
 * @version 2.5.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.5.0")
public interface BiomeSpecialEffects extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<BiomeSpecialEffects> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.biome.BiomeSpecialEffectsImpl");

    BiomeSpecialEffects DEFAULT = of(0x3F75C4, null, null, null, GrassColorModifier.NONE);

    /**
     * The color of water in a biome.
     * @return the water color of the biome
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    int waterColor();

    /**
     * The color of leaves in a biome.
     * @return the foliage color override of the biome, if present
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    Optional<Integer> foliageColorOverride();

    /**
     * The color of dry foliage such as leaf litter in a biome.
     * @return the dry foliage color override of the biome, if present
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    Optional<Integer> dryFoliageColorOverride();

    /**
     * The color of grass in a biome.
     * @return the grass color override of the biome, if present
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    Optional<Integer> grassColorOverride();

    /**
     * The tint modifier for grass colors in a biome.
     * @return the grass color modifier of the biome
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    GrassColorModifier grassColorModifier();

    /**
     * Converts this object back to a builder.
     * @return a new BiomeSpecialEffects builder from this instance
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new BiomeSpecialEffects instance.
     * @param waterColor the water color of the biome
     * @param foliageColorOverride the foliage color override of the biome, or null for none
     * @param dryFoliageColorOverride the dry foliage color override of the biome, or null for none
     * @param grassColorOverride the grass color override of the biome, or null for none
     * @param grassColorModifier the grass color modifier of the biome
     * @return a new BiomeSpecialEffects instance
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    @NullUnmarked
    static BiomeSpecialEffects of(int waterColor, Integer foliageColorOverride, Integer dryFoliageColorOverride, Integer grassColorOverride, @NonNull GrassColorModifier grassColorModifier) {
        return WIRE.construct(waterColor, Optional.ofNullable(foliageColorOverride), Optional.ofNullable(dryFoliageColorOverride), Optional.ofNullable(grassColorOverride), grassColorModifier);
    }

    /**
     * Creates a new BiomeSpecialEffects builder.
     * @return a new BiomeSpecialEffects builder
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating BiomeSpecialEffects instances.
     * @since 2.5.0
     * @version 2.5.0
     * @author Jsinco
     */
    @AsOf("2.5.0")
    final class Builder {
        private int waterColor = 0x3F75C4;
        private @Nullable Integer foliageColorOverride;
        private @Nullable Integer dryFoliageColorOverride;
        private @Nullable Integer grassColorOverride;
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        public Builder() {}

        public Builder(BiomeSpecialEffects other) {
            this.waterColor = other.waterColor();
            this.foliageColorOverride = other.foliageColorOverride().orElse(null);
            this.dryFoliageColorOverride = other.dryFoliageColorOverride().orElse(null);
            this.grassColorOverride = other.grassColorOverride().orElse(null);
            this.grassColorModifier = other.grassColorModifier();
        }

        /**
         * Sets the water color of the biome.
         * @param waterColor the water color of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColorOverride the foliage color override of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder foliageColorOverride(@Nullable Integer foliageColorOverride) {
            this.foliageColorOverride = foliageColorOverride;
            return this;
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColorOverride the dry foliage color override of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder dryFoliageColorOverride(@Nullable Integer dryFoliageColorOverride) {
            this.dryFoliageColorOverride = dryFoliageColorOverride;
            return this;
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColorOverride the grass color override of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder grassColorOverride(@Nullable Integer grassColorOverride) {
            this.grassColorOverride = grassColorOverride;
            return this;
        }

        /**
         * Sets the grass color modifier of the biome.
         * @param grassColorModifier the grass color modifier of the biome
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.grassColorModifier = grassColorModifier;
            return this;
        }

        // Friendly colors

        /**
         * Sets the water color of the biome.
         * @param waterColor the water color of the biome, as a hex string
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder waterColor(String waterColor) {
            this.waterColor = FriendlyColorUtil.hex(waterColor);
            return this;
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColorOverride the foliage color override of the biome, as a hex string
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder foliageColorOverride(@Nullable String foliageColorOverride) {
            this.foliageColorOverride = FriendlyColorUtil.hexOrNull(foliageColorOverride);
            return this;
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColorOverride the dry foliage color override of the biome, as a hex string
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder dryFoliageColorOverride(@Nullable String dryFoliageColorOverride) {
            this.dryFoliageColorOverride = FriendlyColorUtil.hexOrNull(dryFoliageColorOverride);
            return this;
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColorOverride the grass color override of the biome, as a hex string
         * @return this builder
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public Builder grassColorOverride(@Nullable String grassColorOverride) {
            this.grassColorOverride = FriendlyColorUtil.hexOrNull(grassColorOverride);
            return this;
        }

        /**
         * Builds a new BiomeSpecialEffects instance.
         * @return a new BiomeSpecialEffects instance
         * @since 2.5.0
         */
        @AsOf("2.5.0")
        public BiomeSpecialEffects build() {
            return of(waterColor, foliageColorOverride, dryFoliageColorOverride, grassColorOverride, grassColorModifier);
        }
    }
}
