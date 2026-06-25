package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's ConfiguredWorldCarver, a carver paired with its configuration.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface ConfiguredWorldCarver extends NmsHandle permits ConfiguredWorldCarver.Reference, ConfiguredWorldCarver.Custom {

    Codec<ConfiguredWorldCarver> CODEC = Codec.STRING.dispatch(
        "type",
        carver -> carver instanceof Reference ? "reference" : "custom",
        type -> switch (type) {
            case "reference" -> ResourceKey.CODEC.fieldOf("key")
                .xmap(ConfiguredWorldCarver::reference, c -> ((Reference) c).key());
            case "custom" -> RecordCodecBuilder.mapCodec(i -> i.group(
                WorldCarverType.CODEC.fieldOf("carver").forGetter(c -> ((Custom) c).type()),
                CarverConfiguration.CODEC.fieldOf("config").forGetter(c -> ((Custom) c).config())
            ).apply(i, Custom::new));
            default -> throw new IllegalStateException("unknown configured carver: " + type);
        }
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarverFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(ConfiguredWorldCarver carver);
    }

    /**
     * References a configured carver already registered under the given key.
     * @param key the registry key of the configured carver
     * @return a reference to the registered configured carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver reference(ResourceKey key) {
        return new Reference(key);
    }

    /**
     * Authors a configured carver on the vanilla CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver cave(CaveCarverConfiguration configuration) {
        return new Custom(WorldCarverType.CAVE, configuration);
    }

    /**
     * Authors a configured carver on the vanilla NETHER_CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured nether-cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver netherCave(CaveCarverConfiguration configuration) {
        return new Custom(WorldCarverType.NETHER_CAVE, configuration);
    }

    /**
     * Authors a configured carver on the vanilla CANYON algorithm.
     * @param configuration the canyon carver configuration
     * @return a configured canyon carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver canyon(CanyonCarverConfiguration configuration) {
        return new Custom(WorldCarverType.CANYON, configuration);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A reference to an already-registered configured carver.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(ResourceKey key) implements ConfiguredWorldCarver {}

    /**
     * A configured carver authored from a vanilla algorithm and a custom config.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Custom(WorldCarverType type, CarverConfiguration config) implements ConfiguredWorldCarver {}
}