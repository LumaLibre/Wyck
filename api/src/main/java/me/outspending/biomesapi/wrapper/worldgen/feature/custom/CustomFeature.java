package me.outspending.biomesapi.wrapper.worldgen.feature.custom;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.worldgen.CustomFeatureRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

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

    @ApiStatus.Internal
    static final WireProvider<CustomFeatureRegistry> WIRE = WireProvider.create("me.outspending.biomesapi.registry.worldgen.CustomFeatureRegistryImpl");

    private final Supplier<C> configSupplier;

    @AsOf("2.3.0")
    protected CustomFeature(Supplier<C> configSupplier) {
        this.configSupplier = configSupplier;
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
    public CustomFeature<C> register(BiomeResourceKey key) {
        WIRE.get().register(key, this);
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
    public static <C> CustomFeature<C> register(BiomeResourceKey key, CustomFeature<C> feature) {
        return feature.register(key);
    }
}