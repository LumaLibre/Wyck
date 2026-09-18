package dev.wyck.biome.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * A mob's biome spawn cost.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface MobSpawnCost extends Wrapper {

    /**
     * The maximum local energy budget available to the mob.
     * @return the mob's energy budget
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    double energyBudget();

    /**
     * The energy charged for each nearby mob of this type.
     * @return the per-mob charge
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    double charge();

    /**
     * Creates a mob spawn cost.
     * @param energyBudget the maximum local energy budget available to the mob
     * @param charge the energy charged for each nearby mob of this type
     * @return a new mob spawn cost
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static MobSpawnCost of(double energyBudget, double charge) {
        record Holder() {
            static final ConstructWireProvider<MobSpawnCost> WIRE = ConstructWireProvider.create("dev.wyck.biome.entity.MobSpawnCostImpl");
        }
        return Holder.WIRE.construct(energyBudget, charge);
    }

    /**
     * Reads a Minecraft mob spawn cost.
     * @param minecraftMobSpawnCost the Minecraft mob spawn cost to read
     * @return the wrapped mob spawn cost
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static MobSpawnCost decode(Object minecraftMobSpawnCost) {
        record Holder() {
            static final Decoder<MobSpawnCost> DECODER = Decoder.create("dev.wyck.decode.biome.entity.MobSpawnCostDecoder");
        }
        return Holder.DECODER.decode(minecraftMobSpawnCost);
    }
}
