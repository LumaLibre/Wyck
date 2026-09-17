package dev.wyck.worldgen.carver.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.worldgen.CustomCarverRegistry;
import dev.wyck.wrapper.Registerable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

/**
 * A custom implementation of Minecraft 26.3's direct, mask-only carver contract.
 *
 * @param <C> the custom carver configuration type
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
@ApiStatus.Experimental
public abstract class CustomCarver<C> implements Cloneable, Registerable<CustomCarver<C>> {
    private final Supplier<C> configSupplier;
    private @Nullable ResourceKey key;

    /**
     * Creates an unregistered custom carver.
     * @param configSupplier a supplier of fresh configuration instances
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    protected CustomCarver(Supplier<C> configSupplier) {
        this(configSupplier, null);
    }

    /**
     * Creates a custom carver with an optional registry key.
     * @param configSupplier a supplier of fresh configuration instances
     * @param key the registry key, or null for an unregistered carver
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    protected CustomCarver(Supplier<C> configSupplier, @Nullable ResourceKey key) {
        this.configSupplier = configSupplier;
        this.key = key;
    }

    /**
     * Marks positions in the active target chunk as carved.
     * @param context the current carving context
     * @return whether this carver marked at least one position
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public abstract boolean carve(CarvingContext<C> context);

    /**
     * Determines whether this carver starts in a candidate chunk.
     * @param config the custom carver configuration
     * @param random the random source for the candidate chunk
     * @return whether the carver starts in the chunk
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public boolean isStartChunk(C config, Random random) {
        return random.nextFloat() <= probability();
    }

    /**
     * The horizontal chunk range this carver may reach from its source chunk.
     * @return the carver range in chunks
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public int range() {
        return 4;
    }

    /**
     * The default probability used by {@link #isStartChunk(Object, Random)}.
     * @return the carver start probability
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public float probability() {
        return 0.15F;
    }

    /**
     * The registry key assigned to this carver.
     * @return the carver registry key
     * @throws NullPointerException if this carver has no registry key
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public ResourceKey key() {
        return Preconditions.checkNotNull(key, "carver has no registry key");
    }

    /**
     * Returns the nullable registry key used by the runtime bridge.
     * @return the registry key, or null when unregistered
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Internal
    public final @Nullable ResourceKey resourceKey() {
        return key;
    }

    /**
     * Returns the configuration supplier used by the runtime bridge.
     * @return the configuration supplier
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Internal
    public final Supplier<C> configSupplier() {
        return configSupplier;
    }

    /**
     * Creates a fresh configuration instance for the runtime bridge.
     * @return a fresh configuration instance
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Internal
    public final C newConfig() {
        return configSupplier.get();
    }

    /**
     * Registers this carver using its assigned registry key.
     * @return this registered carver
     * @throws NullPointerException if this carver has no registry key
     * @since 4.0.0
     */
    @Override
    @AsOf("4.0.0")
    public final CustomCarver<C> register() {
        CustomCarverRegistry.registry().register(
            Preconditions.checkNotNull(key, "key must be set"),
            this
        );
        return this;
    }

    /**
     * Clones and registers this carver using the supplied registry key.
     * @param key the registry key
     * @param <T> the inferred registered carver type
     * @return the registered carver clone
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @SuppressWarnings("unchecked")
    public final <T> T registerAs(ResourceKey key) {
        CustomCarver<C> copy = clone();
        copy.key = key;
        CustomCarverRegistry.registry().register(key, copy);
        return (T) copy;
    }

    /**
     * Registers a custom carver using the supplied registry key.
     * @param key the registry key
     * @param carver the custom carver to register
     * @param <C> the custom carver configuration type
     * @return the registered carver clone
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    public static <C> CustomCarver<C> register(ResourceKey key, CustomCarver<C> carver) {
        return carver.registerAs(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CustomCarver<C> clone() {
        try {
            return (CustomCarver<C>) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }
}
