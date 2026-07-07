package dev.wyck.wrapper.worldgen.synth;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Wrapper for noise parameters.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface NoiseParameters extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<NoiseParameters> WIRE = WireProvider.construct("dev.wyck.wrapper.worldgen.synth.NoiseParametersImpl");

    /**
     * The first octave of the noise parameters.
     * @return the first octave of the noise parameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int firstOctave();

    /**
     * The amplitudes of the noise parameters.
     * @return the amplitudes of the noise parameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    List<Double> amplitudes();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new NoiseParameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new NoiseParameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseParameters of(int firstOctave, List<Double> amplitudes) {
        return WIRE.construct(firstOctave, amplitudes);
    }

    /**
     * Creates a new NoiseParameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new NoiseParameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseParameters of(int firstOctave, double... amplitudes) {
        List<Double> list = new ArrayList<>(amplitudes.length);
        for (double amplitude : amplitudes) {
            list.add(amplitude);
        }
        return WIRE.construct(firstOctave, list);
    }

    /**
     * Creates a new builder for NoiseParameters.
     * @return a new builder for NoiseParameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for NoiseParameters, which is a first octave plus amplitudes.
     *
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {
        private int firstOctave;
        private List<Double> amplitudes = new ArrayList<>();


        private Builder() {}

        private Builder(NoiseParameters parameters) {
            this.firstOctave = parameters.firstOctave();
            this.amplitudes = new ArrayList<>(parameters.amplitudes());
        }

        /**
         * Sets the first octave of the noise parameters.
         * @param firstOctave the first octave of the noise parameters
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder firstOctave(int firstOctave) {
            this.firstOctave = firstOctave;
            return this;
        }

        /**
         * Adds amplitude to the noise parameters.
         * @param amplitude the amplitude to add
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitude(double amplitude) {
            this.amplitudes.add(amplitude);
            return this;
        }

        /**
         * Sets the amplitudes of the noise parameters.
         * @param amplitudes the amplitudes of the noise parameters
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitudes(Collection<Double> amplitudes) {
            Preconditions.checkNotNull(amplitudes, "amplitudes cannot be null");
            this.amplitudes = new ArrayList<>(amplitudes);
            return this;
        }

        /**
         * Sets the amplitudes of the noise parameters.
         * @param amplitudes the amplitudes of the noise parameters
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitudes(double... amplitudes) {
            this.amplitudes = new ArrayList<>(amplitudes.length);
            for (double amplitude : amplitudes) {
                this.amplitudes.add(amplitude);
            }
            return this;
        }

        /**
         * Builds the NoiseParameters.
         * @return the NoiseParameters
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public NoiseParameters build() {
            return of(firstOctave, amplitudes);
        }
    }
}
