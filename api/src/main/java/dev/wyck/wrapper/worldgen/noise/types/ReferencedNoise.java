package dev.wyck.wrapper.worldgen.noise.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.noise.Noise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a noise function.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface ReferencedNoise extends Noise {
    @ApiStatus.Internal
    ConstructWireProvider<ReferencedNoise> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.noise.types.ReferencedNoiseImpl");

    @AsOf("3.0.0")
    static ReferencedNoise of(ResourceKey resourceKey) {
        return WIRE.construct(resourceKey);
    }
}