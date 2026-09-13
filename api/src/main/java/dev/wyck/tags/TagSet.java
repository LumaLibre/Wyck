package dev.wyck.tags;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.util.Either;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An abstraction over a set of registry entries that is either an explicit {@link Set} of elements or a
 * reference to a named tag ({@link ResourceKey}). It mirrors Minecraft's {@code HolderSet}, which is likewise
 * an inline list, or a tag. When this is a set of elements, this can be registered to a registry.
 *
 * @param <T> the Bukkit type of the elements
 * @since 3.1.0
 * @version 3.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.1.0")
public interface TagSet<T extends Keyed> extends Wrapper, Keyed, Registerable<TagSet<T>> {

    /**
     * Binds this tag set under its own key, which nothing else may have claimed.
     * @return this tag set
     * @throws IllegalArgumentException if a tag is already bound under this key; use {@link #append()}
     *         to add to one instead
     * @since 3.1.0
     */
    @Override
    @AsOf("3.1.0")
    TagSet<T> register();

    /**
     * The key of the tag set.
     * @return the tag set key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The registry the elements of this tag set resolve against.
     * @return the registry discriminator
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    RegistryId registryId();

    /**
     * The contents of this tag set: either an explicit set of elements, or a reference to a named tag.
     * @return the set of values contained in this tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    Either<Set<T>, TagKey> value();

    /**
     * If this is a holder set, returns the underlying set.
     * If this is a tag key, returns the underlying set for that key.
     * @return the underlying set, if present
     * @param <U> the type of the underlying set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    <U> U asHolderSet();

    /**
     * If this is a tag key, returns the underlying tag key.
     * If this is a holder set, tries to find a tag key which matches the items in this set, otherwise throws.
     * @return the underlying tag key, if present
     * @throws IllegalStateException if this is a holder set, and no matching tag key can be found
     * @param <U> the type of the underlying tag key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    <U> U asTagKey();

    /**
     * Binds this tag set's elements into the tag already bound under its key, keeping everything
     * that tag already held, and creating the tag if nothing is bound under that key yet.
     *
     * <p>This is the additive counterpart to {@link #register()}, which insists on a key nothing has
     * claimed. Use it to add to a tag Minecraft or another plugin owns — putting a custom biome into
     * {@code minecraft:has_structure/village_plains} so villages generate there, for instance.</p>
     *
     * <p>A tag the registry has handed out before is rebound in place, so whatever holds it already
     * sees the new members straight away: a structure loaded from a data pack resolved its biome tag
     * through the registry, so appending to that tag retargets the structure in a world that is
     * already running, with nothing reloaded. A tag that exists only because Wyck created it, and
     * that nothing has looked up, is replaced rather than rebound — re-read it after appending
     * instead of holding the set across calls.</p>
     *
     * <p>Additive membership in a {@code minecraft:} tag is not an override: it only ever changes
     * behavior where the elements it adds are present, so adding your own biomes to a vanilla biome
     * tag cannot affect a world those biomes do not occur in.</p>
     *
     * @return this tag set
     * @throws IllegalStateException if this tag set has no resource key, or references a named tag
     *         instead of holding explicit elements
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TagSet<T> append();

    /**
     * Checks if this is a block tag set.
     * @return true if this is a block tag set, false otherwise
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default boolean isBlockSet() {
        return value().left().isPresent();
    }

    /**
     * Checks if this is a tag set.
     * @return true if this is a tag set, false otherwise
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default boolean isTag() {
        return value().right().isPresent();
    }

    /**
     * Creates a tag set of explicit block materials.
     * @param resourceKey the resource key of the tag set
     * @param blocks the block materials
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlocks(@Nullable ResourceKey resourceKey, Material... blocks) {
        return of(resourceKey, RegistryId.BLOCK, Set.of(blocks));
    }

    /**
     * Creates a tag set of explicit block materials.
     * @param resourceKey the resource key of the tag set
     * @param blocks the block materials
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlocks(@Nullable ResourceKey resourceKey, Set<Material> blocks) {
        return of(resourceKey, RegistryId.BLOCK, blocks);
    }

    /**
     * Creates a tag set of explicit block materials.
     * @param blocks the block materials
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlocks(Material... blocks) {
        return of(null, RegistryId.BLOCK, Set.of(blocks));
    }

    /**
     * Creates a tag set of explicit block materials.
     * @param blocks the block materials
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlocks(Set<Material> blocks) {
        return of(null, RegistryId.BLOCK, blocks);
    }

    /**
     * Creates a tag set referencing a named block tag.
     * @param tag the block tag key
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlockTag(TagKey tag) {
        return ofTag(tag);
    }

    /**
     * Creates a key set referencing a named block key.
     * @param key the block tag key
     * @return a new block key set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlockTag(ResourceKey key) {
        return ofTag(TagKey.blocks(key));
    }

    /**
     * Creates a tag set of explicit biomes.
     * @param resourceKey the resource key of the tag set
     * @param biomes the biomes
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomes(@Nullable ResourceKey resourceKey, Biome... biomes) {
        return of(resourceKey, RegistryId.BIOME, Set.of(biomes));
    }

    /**
     * Creates a tag set of explicit biomes.
     * @param resourceKey the resource key of the tag set
     * @param biomes the biomes
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomes(@Nullable ResourceKey resourceKey, Set<Biome> biomes) {
        return of(resourceKey, RegistryId.BIOME, biomes);
    }

    /**
     * Creates a tag set of explicit biomes.
     * @param biomes the biomes
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomes(Set<Biome> biomes) {
        return of(null, RegistryId.BIOME, biomes);
    }

    /**
     * Creates a tag set of explicit biomes.
     * @param biomes the biomes
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomes(Biome... biomes) {
        return of(null, RegistryId.BIOME, Set.of(biomes));
    }

    /**
     * Creates a tag set referencing a named biome tag.
     * @param tag the biome tag key
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomeTag(TagKey tag) {
        return ofTag(tag);
    }

    /**
     * Creates a tag set referencing a named biome tag.
     * @param key the biome tag identifier
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> ofBiomeTag(ResourceKey key) {
        return ofTag(TagKey.biomes(key));
    }

    /**
     * Creates a tag set referencing the given named tag.
     * @param tag the tag key
     * @return a new tag set
     * @param <T> the Bukkit type of the elements
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    @SuppressWarnings("unchecked")
    static <T extends Keyed> TagSet<T> ofTag(TagKey tag) {
        return of(null, tag.registryId(), Either.right(tag));
    }

    /**
     * Creates a block tag set from an {@link Either} of explicit materials or a named tag.
     * @param value either the explicit block materials, or a named block tag
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> blocks(Either<Set<Material>, TagKey> value) {
        return of(null, RegistryId.BLOCK, value);
    }

    /**
     * Creates a tag set from an {@link Either} of explicit tags or a named tag.
     * @param value either the explicit timelines, or a named timeline tag
     * @return a new tag set
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static TagSet<Timeline> timelines(Either<Set<Timeline>, TagKey> value) {
        return of(null, RegistryId.TIMELINE, value);
    }

    /**
     * Creates a biome tag set from an {@link Either} of explicit biomes or a named tag.
     * @param value either the explicit biomes, or a named biome tag
     * @return a new biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> biomes(Either<Set<Biome>, TagKey> value) {
        return of(null, RegistryId.BIOME, value);
    }

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends Keyed> TagSet<T> of(@Nullable ResourceKey resourceKey, RegistryId registry, Set<T> elements) {
        return of(resourceKey, registry, Either.left(elements));
    }

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends Keyed> TagSet<T> of(@Nullable ResourceKey resourceKey, RegistryId registry, Either<Set<T>, TagKey> value) {
        record Holder() {
            static final ConstructWireProvider<TagSet<?>> WIRE = ConstructWireProvider.create("dev.wyck.tags.TagSetImpl");
        }
        return (TagSet<T>) Holder.WIRE.construct(resourceKey, registry, value);
    }

    /**
     * Reads a Minecraft block holder set, preserving a named tag when present.
     * @param minecraftHolderSet the Minecraft block holder set
     * @return the decoded block tag set
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static TagSet<Material> decodeBlocks(Object minecraftHolderSet) {
        record Holder() {
            static final Decoder<TagSet<Material>> DECODER = Decoder.create("dev.wyck.decode.tags.BlockTagSetDecoder");
        }
        return Holder.DECODER.decode(minecraftHolderSet);
    }

    /**
     * Reads a Minecraft biome holder set, preserving a named tag when present.
     * @param minecraftHolderSet the Minecraft biome holder set
     * @return the decoded biome tag set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TagSet<Biome> decodeBiomes(Object minecraftHolderSet) {
        record Holder() {
            static final Decoder<TagSet<Biome>> DECODER = Decoder.create("dev.wyck.decode.tags.BiomeTagSetDecoder");
        }
        return Holder.DECODER.decode(minecraftHolderSet);
    }

    /**
     * Creates a new builder from this tag set.
     * @return a new builder
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Builder<T> toBuilder() {
        return new Builder<>(this);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @param <T> the Bukkit type of the elements
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static <T extends Keyed> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Creates a new builder for block tag sets.
     * @return a new builder
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static Builder<Material> blocks() {
        return new Builder<>(RegistryId.BLOCK);
    }

    /**
     * Creates a new builder for timeline tag sets.
     * @return a new builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Builder<Timeline> timelines() {
        return new Builder<>(RegistryId.TIMELINE);
    }

    /**
     * Creates a new builder for biome tag sets.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder<Biome> biomes() {
        return new Builder<>(RegistryId.BIOME);
    }

    /**
     * Creates a new builder.
     * @param <T> the Bukkit type of the elements
     * @since 3.1.0
     * @version 3.1.0
     * @author Jsinco
     */
    @AsOf("3.1.0")
    final class Builder<T extends Keyed> {
        private @Nullable ResourceKey resourceKey = null;
        private @Nullable RegistryId registry = null;
        private Either<Set<T>, TagKey> value = Either.left(new HashSet<>());

        public Builder() {}

        public Builder(RegistryId registry) {
            this.registry = registry;
        }

        public Builder(TagSet<T> tagSet) {
            this.resourceKey = tagSet.resourceKey().orElse(null);
            this.registry = tagSet.registryId();
            this.value = tagSet.value();
        }

        /**
         * Sets the resource key of the tag set.
         * @param resourceKey the resource key of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the registry discriminator of the tag set.
         * @param registry the registry discriminator of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> registry(RegistryId registry) {
            this.registry = registry;
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param value the value of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> values(Set<T> value) {
            this.value = Either.left(value);
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param value the value of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> values(TagKey value) {
            this.value = Either.right(value);
            return this;
        }

        // Friendly

        /**
         * Sets the value of the tag set.
         * @param value the value of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> values(T... value) {
            this.value = Either.left(new HashSet<>(Set.of(value)));
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param value the value of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> value(T value) {
            this.value = this.value.leftOrElse(new HashSet<>())
                .consumeLeft(set -> set.add(value));
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param tagKey the key of the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> value(ResourceKey tagKey) {
            Preconditions.checkState(registry != null, "Registry must be set before setting a tag key using this override!");
            this.value = Either.right(TagKey.of(registry, tagKey));
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param tag the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> value(Tag<Material> tag) {
            Preconditions.checkState(registry != null, "Registry must be set before setting a tag key using this override!");
            this.value = Either.right(TagKey.of(registry, tag));
            return this;
        }

        /**
         * Sets the value of the tag set.
         * @param tagSet the tag set
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder<T> value(TagSet<T> tagSet) {
            Preconditions.checkState(registry != null, "Registry must be set before setting a tag key using this override!");
            this.value = Either.right(TagKey.of(registry, tagSet.resourceKey().orElseThrow()));
            return this;
        }

        /**
         * Builds a new tag set.
         * @return the new tag set
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public TagSet<T> build() {
            Preconditions.checkState(registry != null, "Registry must be set before building a tag set!");
            Preconditions.checkState(!value.empty(), "Tag set must have a value!");
            return of(resourceKey, registry, value);
        }

        /**
         * Registers the tag set to the registry.
         * @return the registered tag set
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public TagSet<T> register() {
            return build().register();
        }
    }
}
