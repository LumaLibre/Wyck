package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A feature that places sculk catalysts and sculk shriekers in
 * randomly spreading patches.
 *
 * @see <a href="https://minecraft.wiki/w/Sculk_Patch">Sculk Patch</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SculkPatchFeature extends FeatureConfiguration {

    /**
     * The number of charges between 1 and 32.
     * @return the charge count
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int chargeCount();

    /**
     * The initial value of each charge between 1 and 500.
     * @return the amount per charge
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int amountPerCharge();

    /**
     * The number of attempts to spread between 1 and 64.
     * @return the spread attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadAttempts();

    /**
     * The number of times to generate between 0 and 8.
     * @return the growth rounds
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int growthRounds();

    /**
     * The number of times to spread between 0 and 8.
     * @return the spread rounds
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadRounds();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new sculk patch configuration.
     * @param chargeCount the charge count (1-32)
     * @param amountPerCharge the amount per charge (1-500)
     * @param spreadAttempts the spread attempts (1-64)
     * @param growthRounds the growth rounds (0-8)
     * @param spreadRounds the spread rounds (0-8)
     * @return a new sculk patch configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SculkPatchFeature of(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds) {
        record Holder() {
            static final ConstructWireProvider<SculkPatchFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.SculkPatchFeatureImpl");
        }
        return Holder.WIRE.construct(chargeCount, amountPerCharge, spreadAttempts, growthRounds, spreadRounds);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SculkPatchFeature}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int chargeCount = 1;
        private int amountPerCharge = 1;
        private int spreadAttempts = 1;
        private int growthRounds = 0;
        private int spreadRounds = 0;

        public Builder() {}

        public Builder(SculkPatchFeature configuration) {
            this.chargeCount = configuration.chargeCount();
            this.amountPerCharge = configuration.amountPerCharge();
            this.spreadAttempts = configuration.spreadAttempts();
            this.growthRounds = configuration.growthRounds();
            this.spreadRounds = configuration.spreadRounds();
        }

        /**
         * Sets the charge count.
         * @param chargeCount the charge count
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chargeCount(int chargeCount) {
            this.chargeCount = chargeCount;
            return this;
        }

        /**
         * Sets the amount per charge.
         * @param amountPerCharge the amount per charge
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder amountPerCharge(int amountPerCharge) {
            this.amountPerCharge = amountPerCharge;
            return this;
        }

        /**
         * Sets the spread attempts.
         * @param spreadAttempts the spread attempts
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadAttempts(int spreadAttempts) {
            this.spreadAttempts = spreadAttempts;
            return this;
        }

        /**
         * Sets the growth rounds.
         * @param growthRounds the growth rounds
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder growthRounds(int growthRounds) {
            this.growthRounds = growthRounds;
            return this;
        }

        /**
         * Sets the spread rounds.
         * @param spreadRounds the spread rounds
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadRounds(int spreadRounds) {
            this.spreadRounds = spreadRounds;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SculkPatchFeature build() {
            Preconditions.checkArgument(chargeCount >= 1 && chargeCount <= 32, "chargeCount must be between 1 and 32");
            Preconditions.checkArgument(amountPerCharge >= 1 && amountPerCharge <= 500, "amountPerCharge must be between 1 and 500");
            Preconditions.checkArgument(spreadAttempts >= 1 && spreadAttempts <= 64, "spreadAttempts must be between 1 and 64");
            Preconditions.checkArgument(growthRounds >= 0 && growthRounds <= 8, "growthRounds must be between 0 and 8");
            Preconditions.checkArgument(spreadRounds >= 0 && spreadRounds <= 8, "spreadRounds must be between 0 and 8");
            return of(chargeCount, amountPerCharge, spreadAttempts, growthRounds, spreadRounds);
        }
    }
}
