package dev.wyck.wrapper.worldgen.chunk;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * CraftBukkit's implementation of a {@link ChunkGenerator}.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CraftBukkitChunkGenerator extends ChunkGenerator {

    @ApiStatus.Internal
    ConstructWireProvider<CraftBukkitChunkGenerator> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.chunk.CraftBukkitChunkGeneratorImpl");

    /**
     * The world this chunk generator is for.
     * @return the world
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    World world();

    /**
     * The delegate Minecraft chunk generator for everything Bukkit's chunk generator can't do.
     * @return the delegate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ChunkGenerator delegate();

    /**
     * The Bukkit chunk generator.
     * @return the generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    org.bukkit.generator.ChunkGenerator generator();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new CraftBukkitChunkGenerator.
     * @param world the world
     * @param delegate the delegate
     * @param generator the generator
     * @return the CraftBukkitChunkGenerator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CraftBukkitChunkGenerator of(World world, ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator) {
        return WIRE.construct(world, delegate, generator);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CraftBukkitChunkGenerator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable World world;
        private @Nullable ChunkGenerator delegate;
        private org.bukkit.generator.@Nullable ChunkGenerator generator;

        public Builder() {}

        public Builder(CraftBukkitChunkGenerator other) {
            this.world = other.world();
            this.delegate = other.delegate();
            this.generator = other.generator();
        }

        /**
         * Sets the world.
         * @param world the world
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder world(World world) {
            this.world = world;
            return this;
        }

        /**
         * Sets the delegate.
         * @param delegate the delegate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder delegate(ChunkGenerator delegate) {
            this.delegate = delegate;
            return this;
        }

        /**
         * Sets the Bukkit generator.
         * @param generator the Bukkit generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder generator(org.bukkit.generator.ChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        // Friendly

        /**
         * Sets the Bukkit generator.
         * @param generator the Bukkit generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder bukkitGenerator(org.bukkit.generator.ChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        /**
         * Builds the CraftBukkitChunkGenerator.
         * @return the CraftBukkitChunkGenerator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CraftBukkitChunkGenerator build() {
            Preconditions.checkNotNull(world, "world must be set");
            Preconditions.checkNotNull(delegate, "delegate must be set");
            Preconditions.checkNotNull(generator, "generator must be set");
            return of(world, delegate, generator);
        }
    }
}
