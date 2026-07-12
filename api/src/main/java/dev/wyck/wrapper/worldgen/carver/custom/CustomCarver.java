package dev.wyck.wrapper.worldgen.carver.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.worldgen.CustomCarverRegistry;
import dev.wyck.wrapper.internal.Registerable;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Base class for an authored world carver with its own carving algorithm.
 *
 * @param <C> the configuration type carried to {@link #carve(CarvingContext)}
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Experimental
public abstract class CustomCarver<C> implements Cloneable, Registerable<CustomCarver<C>> {

    private final Supplier<C> configSupplier;
    private @Nullable ResourceKey key;

    /**
     * Creates a new CustomCarver with the given configuration supplier.
     * @param configSupplier the configuration supplier
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    protected CustomCarver(Supplier<C> configSupplier) {
        this.configSupplier = configSupplier;
    }

    /**
     * Creates a new CustomCarver with the given configuration supplier and registry key.
     * @param configSupplier the configuration supplier
     * @param key the key to register this carver under
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    protected CustomCarver(Supplier<C> configSupplier, @Nullable ResourceKey key) {
        this.configSupplier = configSupplier;
        this.key = key;
    }

    /**
     * Carves the chunk described by the context.
     * <p>
     * <b>This should return true if anything was carved.</b>
     *
     * @param context the carving surface
     * @return whether carving occurred
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public abstract boolean carve(CarvingContext<C> context);

    /**
     * Whether the given chunk should originate a carve. Called once per chunk in
     * range before {@link #carve(CarvingContext)}.
     *
     * @param config a fresh configuration instance
     * @param random a random seeded for this chunk
     * @return whether to carve from this chunk
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public boolean isStartChunk(C config, Random random) {
        return random.nextFloat() <= this.probability();
    }

    /**
     * Whether {@link CarvingContext#carveEllipsoid} may replace the given state.
     * Ignored by carves that write blocks directly.
     *
     * @return whether the state is replaceable
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public boolean canReplaceBlock(C config, BlockData state) {
        return this.replaceable().contains(state.getMaterial());
    }

    /**
     * @return the chunk radius this carver may reach into
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public int range() {
        return 4;
    }

    /**
     * @return the per-chunk chance this carver originates a carve
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public float probability() {
        return 0.15F;
    }

    /**
     * The y-level below which {@link CarvingContext#carveEllipsoid} places lava
     * rather than consulting the aquifer.
     *
     * @return the lava level anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public VerticalAnchor lavaLevel() {
        return VerticalAnchor.aboveBottom(8);
    }

    /**
     * @return the materials {@link CarvingContext#carveEllipsoid} may replace
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public Set<Material> replaceable() {
        return Set.of(Material.STONE, Material.DEEPSLATE, Material.DIRT, Material.GRASS_BLOCK, Material.GRAVEL, Material.TUFF);
    }

    /**
     * The nominal y-band of this carver. Purely descriptive: it populates the
     * underlying configuration so vanilla tooling reports something sane, and is
     * never sampled by Wyck. Sample your own y from {@link CarvingContext#random()}.
     *
     * @return the height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public HeightProvider y() {
        return HeightProvider.uniform(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180));
    }

    /**
     * @return the nominal vertical scale of this carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public FloatProvider yScale() {
        return FloatProvider.constant(1.0F);
    }

    @AsOf("3.0.0")
    public ResourceKey key() {
        return Preconditions.checkNotNull(this.key, "key shouldn't be null at this point");
    }

    /**
     * Internal method to get this carver's key without failing on absence.
     * @return the key, or null if unregistered
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Internal
    public final @Nullable ResourceKey resourceKey() {
        return this.key;
    }

    /**
     * Internal method to get the configuration supplier.
     * @return the configuration supplier
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Internal
    public final Supplier<C> configSupplier() {
        return this.configSupplier;
    }

    /**
     * Internal method to create a new configuration instance.
     * @return a new instance of the configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Internal
    public final C newConfig() {
        return this.configSupplier.get();
    }

    /**
     * Registers this carver into the CARVER registry under the key of this carver.
     * @return this carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public final CustomCarver<C> register() {
        Preconditions.checkNotNull(this.key, "key must not be null when registering");
        CustomCarverRegistry.registry().register(this.key, this);
        return this;
    }

    /**
     * Registers this carver into the CARVER registry under the given key.
     * Must be called during the bootstrap window. After registration, compose
     * it into a biome with ConfiguredWorldCarver.custom(key, config).
     * @param key the registry key to register under
     * @return the registered carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    public final <T> T registerAs(ResourceKey key) {
        CustomCarver<C> cloned = this.clone();
        cloned.key = key;
        CustomCarverRegistry.registry().register(key, cloned);
        return (T) cloned;
    }

    /**
     * Convenience method for registering a carver.
     * @param key the registry key to register under
     * @param carver the carver to register
     * @return the registered carver
     * @param <C> the configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public static <C> CustomCarver<C> register(ResourceKey key, CustomCarver<C> carver) {
        return carver.registerAs(key);
    }

    /**
     * Clones this carver.
     * @return a clone of this carver
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    @SuppressWarnings({"unchecked", "CloneDoesntDeclareCloneNotSupportedException"})
    protected CustomCarver<C> clone() {
        try {
            return (CustomCarver<C>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}