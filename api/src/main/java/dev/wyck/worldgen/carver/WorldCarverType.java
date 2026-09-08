package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import java.lang.Override;
import java.lang.String;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * The vanilla world-carver algorithms that a configured carver can be built on.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-08T15:00:53.416573700Z")
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

    @AsOf("2.3.0")
    @Override
    public RegisteredConstantTranslator<WorldCarverType> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla registry path for this activity.
     * @return the registry path for this activity
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
}
