package dev.wyck.wrapper.worldgen.synth;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing noise parameters.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedNoiseParameters extends NoiseParameters {

    @ApiStatus.Internal
    ConstructWireProvider<ReferencedNoiseParameters> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.synth.ReferencedNoiseParametersImpl");

    /**
     * Create a new reference to a noise parameters.
     * @param key the key of the noise parameters
     * @return the new reference
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedNoiseParameters of(ResourceKey key) {
        return WIRE.construct(key);
    }
}
