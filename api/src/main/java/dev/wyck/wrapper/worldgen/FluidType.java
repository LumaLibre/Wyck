package dev.wyck.wrapper.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.internal.RegisteredConstantTranslator;
import dev.wyck.wrapper.internal.WrappedConstant;
import org.jspecify.annotations.NullMarked;

/**
 * The vanilla fluids a block predicate can match against.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum FluidType implements WrappedConstant<FluidType> {

    EMPTY("empty"),
    WATER("water"),
    FLOWING_WATER("flowing_water"),
    LAVA("lava"),
    FLOWING_LAVA("flowing_lava");

    public static final RegisteredConstantTranslator<FluidType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.FLUID, FluidType::resourceKey, FluidType.values());
    private final String key;

    @AsOf("2.3.0")
    FluidType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry path for this fluid (e.g. "water", "lava").
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