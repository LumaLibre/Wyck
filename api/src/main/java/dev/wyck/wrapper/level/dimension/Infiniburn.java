package dev.wyck.wrapper.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.dimension.InfiniburnFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Infiniburn create(ResourceKey tag);
    }

    Infiniburn OVERWORLD = of(ResourceKey.minecraft("infiniburn_overworld"));
    Infiniburn NETHER = of(ResourceKey.minecraft("infiniburn_nether"));
    Infiniburn END = of(ResourceKey.minecraft("infiniburn_end"));

    /**
     * The tag of blocks that can burn infinitely in this dimension. This should point at a tag in the {@code tags/blocks} registry.
     * @return the tag of blocks that can burn in this dimension
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey tag();

    /**
     * Creates a reference to a registered infiniburn.
     * @param tag the key of an entry in the infiniburn registry
     * @return a reference to that infiniburn
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Infiniburn of(ResourceKey tag) {
        return WIRE.get().create(tag);
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
}
