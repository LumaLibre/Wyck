package dev.wyck.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagKey;
import dev.wyck.tags.TagSet;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Wraps an {@code Infiniburn} by referencing a tag (blocks that can burn infinitely in this dimension).
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
public interface Infiniburn extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<Infiniburn> WIRE = ConstructWireProvider.create("dev.wyck.level.dimension.InfiniburnImpl");

    Infiniburn OVERWORLD = of(ResourceKey.minecraft("infiniburn_overworld"));
    Infiniburn NETHER = of(ResourceKey.minecraft("infiniburn_nether"));
    Infiniburn END = of(ResourceKey.minecraft("infiniburn_end"));

    /**
     * The tag or set of blocks that can burn infinitely in this
     * dimension.
     * @return the tag or set of blocks that can burn in this dimension
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    TagSet<Material> blocks();

    /**
     * Creates a new builder for this infiniburn.
     * @return a new builder for this infiniburn
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a reference to a registered infiniburn.
     * @param blocks the blocks that can burn infinitely in this dimension
     * @param tag the key of an entry in the infiniburn registry
     * @return a reference to that infiniburn
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Infiniburn of(TagSet<Material> blocks) {
        return WIRE.construct(blocks);
    }

    /**
     * Blocks that can burn infinitely in this dimension.
     * @param blocks the blocks that can burn infinitely in this dimension
     * @return a reference to that infiniburn
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Infiniburn of(Set<Material> blocks) {
        return of(TagSet.ofBlocks(blocks));
    }

    /**
     * Blocks that can burn infinitely in this dimension.
     * @param key the key of an entry in the infiniburn registry
     * @return a reference to that infiniburn
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Infiniburn of(ResourceKey key) {
        return of(TagSet.ofBlockTag(key));
    }

    /**
     * Creates a reference to a registered infiniburn.
     * @param tag the tag of an entry in the infiniburn registry
     * @return a reference to that infiniburn
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Infiniburn of(Tag<Material> tag) {
        Preconditions.checkNotNull(tag, "tag cannot be null");
        return of(ResourceKey.of(tag.getKey().namespace(), tag.getKey().value()));
    }

    /**
     * Creates a new builder for this infiniburn.
     * @return a new builder for this infiniburn
     * @since 2.4.0
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Infiniburn}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private Set<Material> blocks = new HashSet<>();
        private @Nullable TagKey tag;

        private Builder() {}

        public Builder(Infiniburn infiniburn) {
            infiniburn.blocks()
                .value()
                .fold(
                    set -> blocks.addAll(set),
                    tag -> this.tag = tag
                );
        }

        /**
         * Sets the blocks that can burn infinitely in this dimension.
         * @param blocks the blocks that can burn infinitely in this dimension
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder blocks(Set<Material> blocks) {
            this.blocks = blocks;
            return this;
        }

        /**
         * Sets the tagKey of blocks that can burn infinitely in this dimension.
         * @param tagKey the tagKey key of blocks that can burn infinitely in this dimension
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder tag(@Nullable TagKey tagKey) {
            this.tag = tagKey;
            return this;
        }

        // Friendly

        /**
         * Adds a block to the list of blocks that can burn infinitely in this dimension.
         * @param block the block to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder block(Material block) {
            this.blocks.add(block);
            return this;
        }

        /**
         * Sets the tag of blocks that can burn infinitely in this dimension.
         * @param tag the tag of blocks that can burn infinitely in this dimension
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder tag(Tag<Material> tag) {
            this.tag = TagKey.blocks(tag);
            return this;
        }

        /**
         * Sets the tag of blocks that can burn infinitely in this dimension.
         * @param tag the tag of blocks that can burn infinitely in this dimension
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder tag(ResourceKey tag) {
            this.tag = TagKey.blocks(tag);
            return this;
        }

        /**
         * Builds the infiniburn.
         * @return the infiniburn
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Infiniburn build() {
            boolean hasBlocks = !blocks.isEmpty();
            boolean hasTag = tag != null;

            Preconditions.checkArgument(hasBlocks || hasTag, "blocks or tag must be set");
            Preconditions.checkArgument(!(hasBlocks && hasTag), "blocks and tag cannot be set at the same time");

            return of(hasTag ? TagSet.ofBlockTag(tag) : TagSet.ofBlocks(blocks));
        }
    }
}
