package dev.wyck.biome.entity.data;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * Represents the cost of spawning an entity.
 * @param charge the amount of energy required to spawn the entity
 * @param energyBudget the maximum amount of energy that can be used to spawn the entity
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public record SpawnCost(double charge, double energyBudget) {
    @AsOf("2.3.0")
    public static SpawnCost of(double charge, double energyBudget) {
        return new SpawnCost(charge, energyBudget);
    }
}
