package me.outspending.biomesapi.wrapper.worldgen;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.Referer;
import me.outspending.biomesapi.wrapper.internal.RegisteredConstantTranslator;
import me.outspending.biomesapi.wrapper.internal.TranslatableRegistryConstant;
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
public enum FluidType implements TranslatableRegistryConstant<FluidType> {

    EMPTY("empty"),
    WATER("water"),
    FLOWING_WATER("flowing_water"),
    LAVA("lava"),
    FLOWING_LAVA("flowing_lava");

    public static final RegisteredConstantTranslator<FluidType> TRANSLATOR = RegisteredConstantTranslator.of(Referer.FLUID, FluidType::resourceKey, FluidType.values());
    public static final Codec<FluidType> CODEC = TRANSLATOR.codec();

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
    @Override
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    @AsOf("2.4.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }

    @Override
    @AsOf("2.4.0")
    public RegisteredConstantTranslator<FluidType> translator() {
        return TRANSLATOR;
    }
}