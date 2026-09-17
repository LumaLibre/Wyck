package dev.wyck.worldgen.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * The density functions used by Minecraft's optional aquifer system.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface AquiferSettings extends Wrapper {

    /**
     * The density function used to create barriers between neighboring aquifers.
     * @return the aquifer barrier density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction barrier();

    /**
     * The density function controlling how readily an area becomes flooded.
     * @return the fluid-level floodedness density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction fluidLevelFloodedness();

    /**
     * The density function controlling variation in aquifer fluid levels.
     * @return the fluid-level spread density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction fluidLevelSpread();

    /**
     * The density function controlling where aquifers contain lava.
     * @return the lava density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction lava();

    /**
     * The density function controlling areas excluded from aquifer generation.
     * @return the aquifer exclusion density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction exclusion();

    /**
     * The density function used to determine aquifer surface levels.
     * @return the aquifer surface-level density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    DensityFunction surfaceLevel();

    /**
     * Creates aquifer settings.
     * @param barrier the barrier density function
     * @param fluidLevelFloodedness the fluid-level floodedness density function
     * @param fluidLevelSpread the fluid-level spread density function
     * @param lava the lava density function
     * @param exclusion the aquifer exclusion density function
     * @param surfaceLevel the aquifer surface-level density function
     * @return new aquifer settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static AquiferSettings of(
        DensityFunction barrier,
        DensityFunction fluidLevelFloodedness,
        DensityFunction fluidLevelSpread,
        DensityFunction lava,
        DensityFunction exclusion,
        DensityFunction surfaceLevel
    ) {
        record Holder() {
            static final ConstructWireProvider<AquiferSettings> WIRE = ConstructWireProvider.create(
                "dev.wyck.*?.worldgen.noise.AquiferSettingsImpl"
            );
        }
        return Holder.WIRE.construct(
            barrier,
            fluidLevelFloodedness,
            fluidLevelSpread,
            lava,
            exclusion,
            surfaceLevel
        );
    }
}
