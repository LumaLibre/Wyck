package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Axis;
import org.jspecify.annotations.NullMarked;

/**
 * Passes with a chance that moves linearly with the block's distance from the template's origin, measured along one axis.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface AxisAlignedLinearPosTest extends PosRuleTest {

    /**
     * The chance at the minimum distance.
     * @return the minimum chance
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float minChance();

    /**
     * The chance at the maximum distance.
     * @return the maximum chance
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float maxChance();

    /**
     * The distance the minimum chance applies at.
     * @return the minimum distance
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int minDist();

    /**
     * The distance the maximum chance applies at.
     * @return the maximum distance
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int maxDist();

    /**
     * The axis distance is measured along.
     * @return the axis
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Axis axis();

    /**
     * Creates a new test.
     * @param minChance the chance at the minimum distance
     * @param maxChance the chance at the maximum distance
     * @param minDist the distance the minimum chance applies at
     * @param maxDist the distance the maximum chance applies at
     * @param axis the axis distance is measured along
     * @return the test
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AxisAlignedLinearPosTest of(float minChance, float maxChance, int minDist, int maxDist, Axis axis) {
        // No preconditions; minecraft accepts any values, including its own 0/0 defaults
        record Holder() {
            static final ConstructWireProvider<AxisAlignedLinearPosTest> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.rule.AxisAlignedLinearPosTestImpl");
        }
        return Holder.WIRE.construct(minChance, maxChance, minDist, maxDist, axis);
    }
}
