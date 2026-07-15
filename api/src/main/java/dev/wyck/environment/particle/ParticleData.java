package dev.wyck.environment.particle;

import dev.wyck.annotations.AsOf;
import dev.wyck.util.internal.FriendlyColorUtil;
import org.jspecify.annotations.NullMarked;

/**
 * An interface for particle data that can be applied to a specific particle type.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public interface ParticleData {

    /**
     * Parses a hexadecimal color string and returns its integer representation.
     * @param color The hexadecimal color string (e.g., "#FF5733" or "FF5733").
     * @return The integer representation of the color.
     */
    @AsOf("1.1.0")
    static int parseHex(String color) {
        if (color.isEmpty()) return 0;
        return FriendlyColorUtil.hex(color);
    }

    /**
     * Applies the particle data to the given particle type and returns the corresponding ParticleOptions.
     * @param particleType The particle type to which the data will be applied.
     * @return The ParticleOptions representing the applied particle data.
     */
    @AsOf("1.1.0")
    ParticleOptions apply(ParticleType particleType);

}
