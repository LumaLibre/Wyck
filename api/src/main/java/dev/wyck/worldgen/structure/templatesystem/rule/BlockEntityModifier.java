package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's RuleBlockEntityModifier: what a processor rule does to the block entity data of
 * the block it replaces, such as filling a suspicious sand block with loot.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface BlockEntityModifier extends Wrapper {

    /**
     * Removes the block entity data.
     * @return the clearing modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ClearModifier clear() {
        return ClearModifier.INSTANCE;
    }

    /**
     * Keeps the block entity data as it is. A rule without a modifier uses this.
     * @return the pass-through modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static PassthroughModifier passthrough() {
        return PassthroughModifier.INSTANCE;
    }

    /**
     * Merges fixed data into the block entity.
     * @param snbt the data, as SNBT, for example {@code {CustomName:'"Tomb"'}}
     * @return an append-static modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AppendStaticModifier appendStatic(String snbt) {
        return AppendStaticModifier.of(snbt);
    }

    /**
     * Gives the block entity a loot table, as trail ruins do for suspicious gravel.
     * @param lootTable the key of the loot table
     * @return an append-loot modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static AppendLootModifier appendLoot(ResourceKey lootTable) {
        return AppendLootModifier.of(lootTable);
    }

    /**
     * Reads a Minecraft block entity modifier.
     * @param minecraftModifier the modifier to read
     * @return the decoded modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockEntityModifier decode(Object minecraftModifier) {
        record Holder() {
            static final Decoder<BlockEntityModifier> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.templatesystem.BlockEntityModifierDecoders");
        }
        return Holder.DECODER.decode(minecraftModifier);
    }
}
