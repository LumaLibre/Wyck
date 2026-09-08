package dev.wyck.worldgen.material;

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
 * The vanilla fluids a block predicate can match against.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-08T15:00:53.410115700Z")
public enum FluidType implements WrappedConstant<FluidType> {
    EMPTY("empty"),

    FLOWING_WATER("flowing_water"),

    WATER("water"),

    FLOWING_LAVA("flowing_lava"),

    LAVA("lava");

    public static final RegisteredConstantTranslator<FluidType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.FLUID, FluidType::resourceKey, FluidType.values());

    private final String key;

    @AsOf("2.3.0")
    FluidType(String key) {
        this.key = key;
    }

    @AsOf("2.3.0")
    @Override
    public RegisteredConstantTranslator<FluidType> translator() {
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
