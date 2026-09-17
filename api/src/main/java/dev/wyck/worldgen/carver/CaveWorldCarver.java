package dev.wyck.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A direct Minecraft cave world carver.
 *
 * @see <a href="https://minecraft.wiki/w/Carver_definition#cave">Carver definition (cave)</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface CaveWorldCarver extends CarverConfiguration {

    /**
     * The number of cave tunnels attempted by the carver.
     * @return the cave tunnel count
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    IntProvider count();

    /**
     * The base thickness of each cave tunnel.
     * @return the cave tunnel thickness
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider thickness();

    /**
     * Whether the carver applies Minecraft's biased thickness calculation.
     * @return whether the thickness calculation is biased
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    boolean weirdThicknessBias();

    /**
     * The vertical radius multiplier used for cave rooms.
     * @return the room vertical radius multiplier
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider roomVerticalRadiusMultiplier();

    /**
     * The horizontal radius multiplier used for cave tunnels.
     * @return the horizontal radius multiplier
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider horizontalRadiusMultiplier();

    /**
     * The vertical radius multiplier used for cave tunnels.
     * @return the vertical radius multiplier
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider verticalRadiusMultiplier();

    /**
     * The vertical radius multiplier used at the start of a cave tunnel.
     * @return the starting vertical radius multiplier
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider startVerticalRadiusMultiplier();

    /**
     * The relative floor level below which the cave is not carved.
     * @return the cave floor level
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    FloatProvider floorLevel();

    /**
     * Converts this cave carver back to a builder.
     * @return a builder containing this carver's values
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a cave world carver.
     * @param probability the probability that the carver starts in an eligible chunk
     * @param y the provider used to select the starting y-coordinate
     * @param count the number of cave tunnels attempted
     * @param thickness the base thickness of each cave tunnel
     * @param weirdThicknessBias whether to apply the biased thickness calculation
     * @param roomVerticalRadiusMultiplier the vertical radius multiplier for cave rooms
     * @param horizontalRadiusMultiplier the horizontal radius multiplier for cave tunnels
     * @param verticalRadiusMultiplier the vertical radius multiplier for cave tunnels
     * @param startVerticalRadiusMultiplier the vertical radius multiplier at the start of a tunnel
     * @param floorLevel the relative floor level below which the cave is not carved
     * @return a new cave world carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static CaveWorldCarver of(
        float probability,
        HeightProvider y,
        IntProvider count,
        FloatProvider thickness,
        boolean weirdThicknessBias,
        FloatProvider roomVerticalRadiusMultiplier,
        FloatProvider horizontalRadiusMultiplier,
        FloatProvider verticalRadiusMultiplier,
        FloatProvider startVerticalRadiusMultiplier,
        FloatProvider floorLevel
    ) {
        Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0 and 1");
        record Holder() {
            static final ConstructWireProvider<CaveWorldCarver> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.CaveWorldCarverImpl");
        }
        return Holder.WIRE.construct(probability, y, count, thickness, weirdThicknessBias, roomVerticalRadiusMultiplier, horizontalRadiusMultiplier, verticalRadiusMultiplier, startVerticalRadiusMultiplier, floorLevel);
    }

    /**
     * Creates a new cave world carver builder.
     * @return a new builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CaveWorldCarver}.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder {
        private float probability = 0.15F;
        private @Nullable HeightProvider y;
        private IntProvider count = IntProvider.constant(1);
        private FloatProvider thickness = FloatProvider.constant(1.0F);
        private boolean weirdThicknessBias;
        private FloatProvider roomVerticalRadiusMultiplier = FloatProvider.constant(1.0F);
        private FloatProvider horizontalRadiusMultiplier = FloatProvider.constant(1.0F);
        private FloatProvider verticalRadiusMultiplier = FloatProvider.constant(1.0F);
        private FloatProvider startVerticalRadiusMultiplier = FloatProvider.constant(1.0F);
        private FloatProvider floorLevel = FloatProvider.constant(-0.7F);

        public Builder() {}

        /**
         * Creates a builder containing the values of an existing cave carver.
         * @param carver the cave carver to copy
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder(CaveWorldCarver carver) {
            this.probability = carver.probability();
            this.y = carver.y();
            this.count = carver.count();
            this.thickness = carver.thickness();
            this.weirdThicknessBias = carver.weirdThicknessBias();
            this.roomVerticalRadiusMultiplier = carver.roomVerticalRadiusMultiplier();
            this.horizontalRadiusMultiplier = carver.horizontalRadiusMultiplier();
            this.verticalRadiusMultiplier = carver.verticalRadiusMultiplier();
            this.startVerticalRadiusMultiplier = carver.startVerticalRadiusMultiplier();
            this.floorLevel = carver.floorLevel();
        }

        /**
         * Sets the probability that the carver starts in an eligible chunk.
         * @param probability the carver start probability
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Sets the provider used to select the starting y-coordinate.
         * @param y the starting height provider
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder y(HeightProvider y) {
            this.y = y;
            return this;
        }

        /**

         * Sets the number of cave tunnels attempted by the carver.
         * @param count the cave tunnel count
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder count(IntProvider count) {
            this.count = count;
            return this;
        }

        /**

         * Sets the base thickness of each cave tunnel.
         * @param thickness the cave tunnel thickness
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder thickness(FloatProvider thickness) {
            this.thickness = thickness;
            return this;
        }

        /**

         * Sets whether Minecraft's biased thickness calculation is used.
         * @param weirdThicknessBias whether the thickness calculation is biased
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder weirdThicknessBias(boolean weirdThicknessBias) {
            this.weirdThicknessBias = weirdThicknessBias;
            return this;
        }

        /**

         * Sets the vertical radius multiplier used for cave rooms.
         * @param roomVerticalRadiusMultiplier the room vertical radius multiplier
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder roomVerticalRadiusMultiplier(FloatProvider roomVerticalRadiusMultiplier) {
            this.roomVerticalRadiusMultiplier = roomVerticalRadiusMultiplier;
            return this;
        }

        /**

         * Sets the horizontal radius multiplier used for cave tunnels.
         * @param horizontalRadiusMultiplier the horizontal radius multiplier
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder horizontalRadiusMultiplier(FloatProvider horizontalRadiusMultiplier) {
            this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
            return this;
        }

        /**

         * Sets the vertical radius multiplier used for cave tunnels.
         * @param verticalRadiusMultiplier the vertical radius multiplier
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder verticalRadiusMultiplier(FloatProvider verticalRadiusMultiplier) {
            this.verticalRadiusMultiplier = verticalRadiusMultiplier;
            return this;
        }

        /**

         * Sets the vertical radius multiplier used at the start of a cave tunnel.
         * @param startVerticalRadiusMultiplier the starting vertical radius multiplier
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder startVerticalRadiusMultiplier(FloatProvider startVerticalRadiusMultiplier) {
            this.startVerticalRadiusMultiplier = startVerticalRadiusMultiplier;
            return this;
        }

        /**

         * Sets the relative floor level below which the cave is not carved.
         * @param floorLevel the cave floor level
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder floorLevel(FloatProvider floorLevel) {
            this.floorLevel = floorLevel;
            return this;
        }

        /**
         * Builds the cave world carver.
         * @return the cave world carver
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public CaveWorldCarver build() {
            return CaveWorldCarver.of(
                probability,
                Preconditions.checkNotNull(y, "y must be set"),
                count,
                thickness,
                weirdThicknessBias,
                roomVerticalRadiusMultiplier,
                horizontalRadiusMultiplier,
                verticalRadiusMultiplier,
                startVerticalRadiusMultiplier,
                floorLevel
            );
        }
    }
}
