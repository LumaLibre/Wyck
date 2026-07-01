package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.Referer;
import me.outspending.biomesapi.wrapper.internal.RegisteredConstantTranslator;
import me.outspending.biomesapi.wrapper.internal.TranslatableRegistryConstant;
import org.jspecify.annotations.NullMarked;

/**
 * The vanilla world-carver algorithms that a configured carver can be built on.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum WorldCarverType implements TranslatableRegistryConstant<WorldCarverType> {

    CAVE("cave"),
    NETHER_CAVE("nether_cave"),
    CANYON("canyon");

    public static final RegisteredConstantTranslator<WorldCarverType> TRANSLATOR = RegisteredConstantTranslator.of(Referer.CONFIGURED_CARVER, WorldCarverType::resourceKey, WorldCarverType.values());
    public static final Codec<WorldCarverType> CODEC = TRANSLATOR.codec();

    private final String key;

    @AsOf("2.3.0")
    WorldCarverType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry key for this carver (e.g. "cave", "canyon").
     * @return the vanilla key for this carver
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }

    @Override
    @AsOf("2.4.0")
    public RegisteredConstantTranslator<WorldCarverType> translator() {
        return TRANSLATOR;
    }
}