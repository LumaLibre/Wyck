package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Gives the block entity a loot table, as trail ruins do for suspicious gravel.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface AppendLootModifier extends BlockEntityModifier {

    /**
     * The loot table given to the block entity.
     * @return the key of the loot table
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    ResourceKey lootTable();

    /**
     * Creates a new append-loot modifier.
     * @param lootTable the key of the loot table
     * @return the modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AppendLootModifier of(ResourceKey lootTable) {
        record Holder() {
            static final ConstructWireProvider<AppendLootModifier> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.rule.AppendLootModifierImpl");
        }
        return Holder.WIRE.construct(lootTable);
    }
}
