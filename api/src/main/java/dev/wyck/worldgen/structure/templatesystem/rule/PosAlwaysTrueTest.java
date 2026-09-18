package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Passes for every position. A rule without a position test uses this.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface PosAlwaysTrueTest extends PosRuleTest {

    /** The singleton instance of this test. */
    @AsOf("3.4.0")
    PosAlwaysTrueTest INSTANCE = of();

    private static PosAlwaysTrueTest of() {
        record Holder() {
            static final ConstructWireProvider<PosAlwaysTrueTest> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.structure.templatesystem.rule.PosAlwaysTrueTestImpl");
        }
        return Holder.WIRE.construct();
    }
}
