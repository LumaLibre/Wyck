package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the current position is a steep face on the north or east sides of a mountain.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SteepConditionSource extends ConditionSource {

    @ApiStatus.Internal
    ConstructWireProvider<SteepConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.SteepConditionSourceImpl");

    /** The steep condition source. */
    @AsOf("3.0.0")
    SteepConditionSource INSTANCE = of();

    private static SteepConditionSource of() {
        return WIRE.construct();
    }
}
