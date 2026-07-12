package dev.wyck.wrapper.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Tests the block is the specified block. Then only matches with a given probability.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#block_match">Processor list (block match)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomBlockMatchTest extends RuleTest {

    @ApiStatus.Internal
    ConstructWireProvider<RandomBlockMatchTest> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.ruletest.RandomBlockMatchTestImpl");

    /**
     * The block to match.
     * @return the block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material block();

    /**
     * The probability of matching between 0.0 and 1.0.
     * @return the probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * Creates a new random block match test.
     * @param block the block to match
     * @param probability the probability of matching
     * @return the random block match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBlockMatchTest of(Material block, float probability) {
        // No preconditions; minecraft just clamps values
        return WIRE.construct(block, probability);
    }
}
