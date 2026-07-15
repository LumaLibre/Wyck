//d196df75217d6b08b9f3bdacebd3536d
package dev.wyck.worldgen.material;

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
 * The vanilla fluids a block predicate can match against.
 * </p>
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-07-15T07:01:58.786929Z")
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

    /**
     * The vanilla registry path for this fluid.
     * @return the registry path for this fluid
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
    public RegisteredConstantTranslator<FluidType> translator() {
        return TRANSLATOR;
    }
}
