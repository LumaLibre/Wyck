package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Removes the block entity data.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ClearModifier extends BlockEntityModifier {

    /** The singleton instance of this modifier. */
    @AsOf("3.4.0")
    ClearModifier INSTANCE = of();

    private static ClearModifier of() {
        record Holder() {
            static final ConstructWireProvider<ClearModifier> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.structure.templatesystem.rule.ClearModifierImpl");
        }
        return Holder.WIRE.construct();
    }
}
