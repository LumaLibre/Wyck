package dev.wyck.worldgen.structure.pools.alias;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Swaps which template pool a jigsaw structure's pieces draw from, chosen once per structure.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public sealed interface PoolAliasBinding permits PoolAliasBinding.Direct, PoolAliasBinding.Random, PoolAliasBinding.RandomGroup {

    /**
     * Always resolves the alias to the same target.
     * @param alias the pool pieces name
     * @param target the pool they are generated from
     * @return a direct binding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Direct direct(TemplatePool alias, TemplatePool target) {
        return new Direct(alias, target);
    }

    /**
     * Resolves the alias to one weighted target.
     * @param alias the pool pieces name
     * @param targets the pools it may resolve to
     * @return a random binding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Random random(TemplatePool alias, WeightedList<TemplatePool> targets) {
        return new Random(alias, targets);
    }

    /**
     * Picks one weighted group of bindings and applies all of them.
     * @param groups the groups of bindings to choose between
     * @return a random-group binding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RandomGroup randomGroup(WeightedList<List<PoolAliasBinding>> groups) {
        return new RandomGroup(groups);
    }

    /**
     * Reads a Minecraft pool alias binding.
     * @param minecraftBinding the binding to read
     * @return the decoded binding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static PoolAliasBinding decode(Object minecraftBinding) {
        record Holder() {
            static final Decoder<PoolAliasBinding> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.pools.PoolAliasBindingDecoders");
        }
        return Holder.DECODER.decode(minecraftBinding);
    }

    /**
     * A binding that always resolves the alias to the same target.
     * @param alias the pool pieces name
     * @param target the pool they are generated from
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record Direct(TemplatePool alias, TemplatePool target) implements PoolAliasBinding {}

    /**
     * A binding that resolves the alias to one weighted target.
     * @param alias the pool pieces name
     * @param targets the pools it may resolve to
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record Random(TemplatePool alias, WeightedList<TemplatePool> targets) implements PoolAliasBinding {
        public Random {
            Preconditions.checkArgument(!targets.isEmpty(), "targets must not be empty");
        }
    }

    /**
     * A binding that picks one weighted group of bindings and applies all of them.
     * @param groups the groups of bindings to choose between
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record RandomGroup(WeightedList<List<PoolAliasBinding>> groups) implements PoolAliasBinding {
        public RandomGroup {
            Preconditions.checkArgument(!groups.isEmpty(), "groups must not be empty");
        }
    }
}
