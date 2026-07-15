//97df77a7acdc201f413218f223109fb8
package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * The vanilla world-carver algorithms that a configured carver can be built on.
 * </p>
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-07-15T07:01:58.789074Z")
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
     * The vanilla registry path for this worldcarver.
     * @return the registry path for this worldcarver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String key() {
        return this.key;
    }

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
