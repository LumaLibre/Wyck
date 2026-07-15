package dev.wyck.worldgen.synth;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Wrapper for noise parameters.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface NoiseParameters extends Wrapper, Keyed {

    /**
     * The resource key of the noise parameters.
     * @return the resource key of the noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Creates a reference to a noise parameters.
     * @param key the resource key of the noise parameters
     * @return a reference to the noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedNoiseParameters reference(ResourceKey key) {
        return ReferencedNoiseParameters.of(key);
    }

    /**
     * Creates a new composed noise parameters builder.
     * @return a new composed noise parameters builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters.Builder composed() {
        return ComposedNoiseParameters.builder();
    }


    // Friendly

    /**
     * Creates a reference to a noise parameters.
     * @param key the resource key of the noise parameters
     * @return a reference to the noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedNoiseParameters of(ResourceKey key) {
        return ReferencedNoiseParameters.of(key);
    }

    /**
     * Creates a new composed noise parameters builder.
     * @return a new composed noise parameters builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Obsolete
    static ComposedNoiseParameters.Builder builder() {
        return ComposedNoiseParameters.builder();
    }

    /**
     * Creates a new composed noise parameters.
     * @param resourceKey the resource key of the noise parameters
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new composed noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(@Nullable ResourceKey resourceKey, int firstOctave, List<Double> amplitudes) {
        return ComposedNoiseParameters.of(resourceKey, firstOctave, amplitudes);
    }

    /**
     * Creates a new composed noise parameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new composed noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(int firstOctave, List<Double> amplitudes) {
        return ComposedNoiseParameters.of(null, firstOctave, amplitudes);
    }

    /**
     * Creates a new composed noise parameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new composed noise parameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(int firstOctave, double... amplitudes) {
        return ComposedNoiseParameters.of(firstOctave, amplitudes);
    }
}
