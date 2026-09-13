package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's PosRuleTest: a test on where a block sits within a template, used by a
 * processor rule to vary its chance of applying with distance from the template's origin.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface PosRuleTest extends Wrapper {

    /**
     * Passes for every position. A rule without a position test uses this.
     * @return the always-true test
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static PosAlwaysTrueTest alwaysTrue() {
        return PosAlwaysTrueTest.INSTANCE;
    }

    /**
     * Passes with a chance that moves from {@code minChance} to {@code maxChance} as the block's
     * distance from the template's origin moves from {@code minDist} to {@code maxDist}.
     * @param minChance the chance at the minimum distance
     * @param maxChance the chance at the maximum distance
     * @param minDist the distance the minimum chance applies at
     * @param maxDist the distance the maximum chance applies at
     * @return a linear position test
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static LinearPosTest linearPos(float minChance, float maxChance, int minDist, int maxDist) {
        return LinearPosTest.of(minChance, maxChance, minDist, maxDist);
    }

    /**
     * As {@link #linearPos}, measuring distance along one axis only.
     * @param minChance the chance at the minimum distance
     * @param maxChance the chance at the maximum distance
     * @param minDist the distance the minimum chance applies at
     * @param maxDist the distance the maximum chance applies at
     * @param axis the axis distance is measured along
     * @return an axis-aligned linear position test
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AxisAlignedLinearPosTest axisAlignedLinearPos(float minChance, float maxChance, int minDist, int maxDist, Axis axis) {
        return AxisAlignedLinearPosTest.of(minChance, maxChance, minDist, maxDist, axis);
    }

    /**
     * Reads a Minecraft position rule test.
     * @param minecraftTest the test to read
     * @return the decoded test
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static PosRuleTest decode(Object minecraftTest) {
        record Holder() {
            static final Decoder<PosRuleTest> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.templatesystem.PosRuleTestDecoders");
        }
        return Holder.DECODER.decode(minecraftTest);
    }
}
