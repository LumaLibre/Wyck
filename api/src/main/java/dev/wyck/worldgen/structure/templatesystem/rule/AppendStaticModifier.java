package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Merges fixed data into the block entity. Wyck has no NBT model, so the data is carried as SNBT and
 * parsed when the modifier reaches Minecraft.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface AppendStaticModifier extends BlockEntityModifier {

    /**
     * The data merged into the block entity.
     * @return the data, as SNBT
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    String snbt();

    /**
     * Creates a new append-static modifier.
     * @param snbt the data, as SNBT, for example {@code {CustomName:'"Tomb"'}}
     * @return the modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AppendStaticModifier of(String snbt) {
        record Holder() {
            static final ConstructWireProvider<AppendStaticModifier> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.rule.AppendStaticModifierImpl");
        }
        // a lone String would bind to the static construct(String) factory instead of the varargs call
        return Holder.WIRE.construct((Object) snbt);
    }
}
