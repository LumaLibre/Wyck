package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import dev.wyck.worldgen.carver.types.CustomComposedCarver;
import dev.wyck.worldgen.carver.types.ReferencedCarver;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A direct Minecraft world carver or a reference to one already registered.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface WorldCarver extends Wrapper, Keyed {

    /**
     * References a carver already registered under the given key.
     * @param key the registry key of the carver
     * @return a reference to the registered carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ReferencedCarver reference(ResourceKey key) {
        return ReferencedCarver.of(key);
    }

    /**
     * Resolves this carver's key in Minecraft's direct carver registry and decodes the registered value.
     * @return the decoded carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Experimental
    default WorldCarver wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.CARVER).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Authors a carver using the vanilla cave implementation.
     * @return a cave carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ComposedCarver.Builder cave() {
        return ComposedCarver.cave();
    }

    /**
     * Authors a carver using the vanilla canyon implementation.
     * @return a canyon carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ComposedCarver.Builder canyon() {
        return ComposedCarver.canyon();
    }

    /**
     * Composes a registered custom carver with a config instance.
     * @param customCarver the custom carver to compose
     * @param config the config instance to carve with
     * @return an authored custom carver
     * @param <C> the config type
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static <C> CustomComposedCarver<C> custom(CustomCarver<C> customCarver, C config) {
        return CustomComposedCarver.of(customCarver, config);
    }

    /**
     * Creates a new builder for a custom carver.
     * @return a new custom carver builder
     * @param <C> the config type
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static <C> CustomComposedCarver.Builder<C> custom() {
        return CustomComposedCarver.builder();
    }

    /**
     * Reads a keyed Minecraft carver holder into a reference wrapper.
     * @param minecraftWorldCarver the carver holder to read
     * @return a reference to the carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static WorldCarver decode(Object minecraftWorldCarver) {
        record Holder() {
            static final Decoder<WorldCarver> DECODER = Decoder.create("dev.wyck.decode.worldgen.carver.WorldCarverDecoder");
        }
        return Holder.DECODER.decode(minecraftWorldCarver);
    }
}
