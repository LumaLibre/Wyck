package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Keeps the block entity data as it is. A rule without a modifier uses this.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface PassthroughModifier extends BlockEntityModifier {

    /** The singleton instance of this modifier. */
    @AsOf("3.4.0")
    PassthroughModifier INSTANCE = of();

    private static PassthroughModifier of() {
        record Holder() {
            static final ConstructWireProvider<PassthroughModifier> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.structure.templatesystem.rule.PassthroughModifierImpl");
        }
        return Holder.WIRE.construct();
    }
}
