package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.internal.RegisteredConstantTranslator;
import dev.wyck.wrapper.internal.WrappedConstant;
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
public enum WorldCarverType implements WrappedConstant<WorldCarverType> {

    CAVE("cave"),
    NETHER_CAVE("nether_cave"),
    CANYON("canyon");

    public static final RegisteredConstantTranslator<WorldCarverType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.CARVER, WorldCarverType::resourceKey, WorldCarverType.values());
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
    @AsOf("2.3.0")
    public String key() {
        return this.key;
    }

    /**
     * The resource key for this carver.
     * @return the resource key for this carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }

    @Override
    @AsOf("3.0.0")
    public RegisteredConstantTranslator<WorldCarverType> translator() {
        return TRANSLATOR;
    }
}