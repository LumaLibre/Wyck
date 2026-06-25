package me.outspending.biomesapi.wrapper.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.worldgen.CustomFeatureRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Base class for an authored world generation feature with its own placement
 * algorithm.
 *
 * @param <C> the configuration type carried to {@link #place(PlacementContext)}
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public abstract class CustomFeature<C> {

    private final Supplier<C> configSupplier;
    private final @Nullable Codec<C> configCodec;

    @AsOf("2.3.0")
    protected CustomFeature(Supplier<C> configSupplier) {
        this(configSupplier, null);
    }

    @AsOf("2.4.0")
    protected CustomFeature(Supplier<C> configSupplier, @Nullable Codec<C> configCodec) {
        this.configSupplier = configSupplier;
        this.configCodec = configCodec;
    }

    /**
     * Places this feature.
     * <p>
     * <b>This should return true if anything was placed.</b>
     *
     * @param context the placement surface
     * @return whether placement occurred
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @Contract(value = "null -> fail", pure = false)
    public abstract boolean place(PlacementContext<C> context);

    /**
     * Internal method to get the configuration supplier.
     * @return the configuration supplier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    public Supplier<C> configSupplier() {
        return this.configSupplier;
    }

    /**
     * Internal method to get the configuration codec.
     * @return the configuration codec
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @ApiStatus.Internal
    public @Nullable Codec<C> configCodec() {
        return this.configCodec;
    }

    /**
     * Internal method to create a new configuration instance.
     * @return a new instance of the configuration type
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    public C newConfig() {
        return this.configSupplier.get();
    }

    /**
     * Registers this feature into the FEATURE registry under the given key.
     * Must be called during the bootstrap window. After registration, compose
     * it into a biome with ConfiguredFeature.custom(key, config).
     * @param key the registry key to register under
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public CustomFeature<C> register(ResourceKey key) {
        CustomFeatureRegistry.registry().register(key, this);
        return this;
    }

    /**
     * Convenience method for registering a feature.
     * @param key the registry key to register under
     * @param feature the feature to register
     * @return the registered feature
     * @param <C> the configuration type
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static <C> CustomFeature<C> register(ResourceKey key, CustomFeature<C> feature) {
        return feature.register(key);
    }
}