package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * Supplies block states from another provider and rotates their directional properties.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#rotated_block_provider">Block state provider (rotated block provider)</a>
 * @since 3.0.0
 * @version 4.0.0
 */
@NullMarked
@AsOf("3.0.0")
public interface RotatedBlockProvider extends BlockStateProvider {

    /**
     * The provider supplying the state to rotate.
     * @return the nested block state provider
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    BlockStateProvider state();

    /**
     * The fixed direction used for rotation, or an empty value to choose a random direction.
     * @return the optional fixed direction
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Optional<BlockFace> direction();

    /**
     * Creates a new rotated block provider.
     * @param state the provider supplying the state to rotate
     * @param direction the fixed direction, or an empty value to choose one randomly
     * @return a new rotated block provider
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static RotatedBlockProvider of(BlockStateProvider state, Optional<BlockFace> direction) {
        record Holder() {
            static final ConstructWireProvider<RotatedBlockProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.RotatedBlockProviderImpl");
        }
        return Holder.WIRE.construct(state, direction);
    }

    /**
     * Creates a provider that randomly rotates the supplied material.
     * @param state the material to supply and rotate
     * @return a new rotated block provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RotatedBlockProvider of(Material state) {
        return of(BlockStateProvider.simple(state), Optional.empty());
    }

}
