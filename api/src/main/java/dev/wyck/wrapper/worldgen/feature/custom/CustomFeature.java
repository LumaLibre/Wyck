package dev.wyck.wrapper.worldgen.feature.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.worldgen.CustomFeatureRegistry;
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
public abstract class CustomFeature<C> implements Cloneable {

    private final Supplier<C> configSupplier;
    private @Nullable ResourceKey key;

    /**
     * Creates a new CustomFeature with the given configuration supplier.
     * @param configSupplier the configuration supplier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    protected CustomFeature(Supplier<C> configSupplier) {
        this.configSupplier = configSupplier;
    }

    /**
     * Creates a new CustomFeature with the given configuration supplier and registry key.
     * @param configSupplier the configuration supplier
     * @param key the key to register this feature under
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    protected CustomFeature(Supplier<C> configSupplier, @Nullable ResourceKey key) {
        this.configSupplier = configSupplier;
        this.key = key;
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

    @AsOf("3.0.0")
    public ResourceKey key() {
        return Preconditions.checkNotNull(this.key, "key shouldn't be null at this point");
    }

    @AsOf("3.0.0")
    @ApiStatus.Internal
    public final @Nullable ResourceKey resourceKey() {
        return this.key;
    }

    /**
     * Internal method to get the configuration supplier.
     * @return the configuration supplier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    public final Supplier<C> configSupplier() {
        return this.configSupplier;
    }

    /**
     * Internal method to create a new configuration instance.
     * @return a new instance of the configuration type
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Internal
    public final C newConfig() {
        return this.configSupplier.get();
    }

    /**
     * Registers this feature into the FEATURE registry under the key of this feature.
     * @return this feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public final CustomFeature<C> register() {
        Preconditions.checkNotNull(this.key, "key must not be null when registering");
        CustomFeatureRegistry.registry().register(this.key, this);
        return this;
    }

    /**
     * Registers this feature into the FEATURE registry under the given key.
     * Must be called during the bootstrap window. After registration, compose
     * it into a biome with ConfiguredFeature.custom(key, config).
     * @param key the registry key to register under
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public final CustomFeature<C> registerAs(ResourceKey key) {
        CustomFeature<C> cloned = this.clone();
        cloned.key = key;
        CustomFeatureRegistry.registry().register(key, cloned);
        return cloned;
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
        return feature.registerAs(key);
    }

    /**
     * Clones this feature.
     * @return a clone of this feature
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    @SuppressWarnings({"unchecked", "CloneDoesntDeclareCloneNotSupportedException"})
    protected CustomFeature<C> clone() {
        try {
            return (CustomFeature<C>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}