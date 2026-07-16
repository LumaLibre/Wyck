package dev.wyck.tags;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.util.Either;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

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

    @ApiStatus.Internal
    ConstructWireProvider<TagSet<?>> WIRE = ConstructWireProvider.create("dev.wyck.tags.TagSetImpl");

    /**
     * The key of the tag set.
     * @return the tag set key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The contents of this tag set: either an explicit set of elements, or a reference to a named tag.
     * @return the set of values contained in this tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    Either<Set<T>, TagKey> value();

    /**
     * The registry the elements of this tag set resolve against.
     * @return the registry discriminator
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    @ApiStatus.Internal
    RegistryId registryId();

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
     * Creates a tag set referencing a Bukkit block {@link Tag}.
     * @param tag the Bukkit block tag
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlockTag(Tag<Material> tag) {
        return ofTag(TagKey.blocks(tag));
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
        return (TagSet<T>) WIRE.construct(null, tag.registry(), Either.right(tag));
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
     * Creates a block tag set from an {@link Either} of explicit materials or a named tag.
     * @param resourceKey the resource key of the tag set
     * @param value either the explicit block materials, or a named block tag
     * @return a new block tag set
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagSet<Material> ofBlocks(@Nullable ResourceKey resourceKey, Either<Set<Material>, TagKey> value) {
        return of(resourceKey, RegistryId.BLOCK, value);
    }

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends Keyed> TagSet<T> of(@Nullable ResourceKey resourceKey, RegistryId registry, Set<T> elements) {
        return (TagSet<T>) WIRE.construct(resourceKey, registry, Either.left(elements));
    }

    /**
     * Creates a tag set from an {@link Either} of explicit elements or a named tag.
     * @param resourceKey the resource key of the tag set, or {@code null}
     * @param registry the registry the elements resolve against
     * @param value either the explicit elements or a named tag
     * @return a new tag set
     * @param <T> the Bukkit type of the elements
     * @since 3.1.0
     */
    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    static <T extends Keyed> TagSet<T> of(@Nullable ResourceKey resourceKey, RegistryId registry, Either<Set<T>, TagKey> value) {
        return (TagSet<T>) WIRE.construct(resourceKey, registry, value);
    }
}
