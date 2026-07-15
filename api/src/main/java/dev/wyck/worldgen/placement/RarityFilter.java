package dev.wyck.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Either returns the current position or empty. The chance of returning the position is
 * <code>1 / chance</code>.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RarityFilter extends PlacementFilter {

    @ApiStatus.Internal
    ConstructWireProvider<RarityFilter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.RarityFilterImpl");

    /**
     * The average chance denominator; the position is kept once on average every this many attempts.
     * @return the chance, a positive integer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int chance();

    /**
     * Creates a new rarity filter.
     * @param chance the average chance denominator, a positive integer
     * @return a new rarity filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RarityFilter of(int chance) {
        Preconditions.checkArgument(chance > 0, "chance must be greater than 0");
        return WIRE.construct(chance);
    }
}