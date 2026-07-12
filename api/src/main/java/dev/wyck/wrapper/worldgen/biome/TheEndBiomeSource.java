package dev.wyck.wrapper.worldgen.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * The biome source used for the End dimension.
 *
 * @see <a href="https://minecraft.wiki/w/Dimension_definition#end">Dimension definition (end)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TheEndBiomeSource extends BiomeSource {
    @ApiStatus.Internal
    ConstructWireProvider<TheEndBiomeSource> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.biome.TheEndBiomeSourceImpl");

    /** The singleton instance of {@link TheEndBiomeSource}. */
    @AsOf("3.0.0")
    TheEndBiomeSource INSTANCE = of();

    private static TheEndBiomeSource of() {
        return WIRE.construct();
    }
}
