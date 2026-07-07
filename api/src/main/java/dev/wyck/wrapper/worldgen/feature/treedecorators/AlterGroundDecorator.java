package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Replaces blocks in the <code>#minecraft:dirt</code> tag around the tree with different blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AlterGroundDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<AlterGroundDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.AlterGroundDecoratorImpl");

    /**
     * A rule-based block state provider defining how to replace the ground.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider provider();

    /**
     * Creates a new alter ground decorator.
     * @param provider a rule-based block state provider defining how to replace the ground
     * @return a new alter ground decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AlterGroundDecorator of(BlockStateProvider provider) {
        return WIRE.construct(provider);
    }
}
