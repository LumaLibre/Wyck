package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for particle data that can be applied to a specific particle type.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @author Jsinco
 * @param <T> The type of ParticleOptions associated with the particle data.
 */
public interface ParticleData<T> {

    /**
     * Parses a hexadecimal color string and returns its integer representation.
     * @param color The hexadecimal color string (e.g., "#FF5733" or "FF5733").
     * @return The integer representation of the color.
     */
    @AsOf("1.1.0")
    static int parseHex(@NotNull String color) {
        if (color.isEmpty()) {
            return 0;
        } else if (color.startsWith("#")) {
            color = color.substring(1);
        }
        return Integer.parseInt(color, 16);
    }

    /**
     * Applies the particle data to the given particle type and returns the corresponding ParticleOptions.
     * @param particleType The particle type to which the data will be applied.
     * @return The ParticleOptions representing the applied particle data.
     */
    @AsOf("1.1.0")
    @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<@NotNull T> particleType);

}
