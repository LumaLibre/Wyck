package dev.wyck.worldgen.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a hardcoded parameter list preset.
 * The available presets are <code>overworld</code> and <code>nether</code>.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MultiNoisePresetBiomeSource extends BiomeSource, Keyed {

    @ApiStatus.Internal
    ConstructWireProvider<MultiNoisePresetBiomeSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.biome.MultiNoisePresetBiomeSourceImpl");

    MultiNoisePresetBiomeSource OVERWORLD = MultiNoisePresetBiomeSource.of(ResourceKey.of("overworld"));
    MultiNoisePresetBiomeSource NETHER = MultiNoisePresetBiomeSource.of(ResourceKey.of("nether"));

    /**
     * A reference to a hardcoded parameter list preset.
     * @return a reference to a hardcoded parameter list preset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey preset();

    /**
     * Creates a new multi-noise preset biome source.
     * @param preset the preset to use
     * @return the multi-noise preset biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultiNoisePresetBiomeSource of(ResourceKey preset) {
        return WIRE.construct(preset);
    }
}
