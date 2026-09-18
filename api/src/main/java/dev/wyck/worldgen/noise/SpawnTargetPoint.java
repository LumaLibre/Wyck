package dev.wyck.worldgen.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

/**
 * A player-spawn fitness target defined by density-function parameter ranges.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface SpawnTargetPoint extends Wrapper {

    /**
     * The acceptable climate parameter for each density function sampled by this target.
     * @return an immutable map of density functions to climate parameters
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Map<DensityFunction, ClimateParameter> parameters();

    /**
     * Creates a player-spawn target point.
     * @param parameters the acceptable climate parameter for each density function
     * @return a new player-spawn target point
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static SpawnTargetPoint of(Map<DensityFunction, ClimateParameter> parameters) {
        record Holder() {
            static final ConstructWireProvider<SpawnTargetPoint> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.SpawnTargetPointImpl");
        }
        return Holder.WIRE.construct(Map.copyOf(parameters));
    }
}
