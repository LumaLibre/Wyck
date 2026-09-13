package dev.wyck.worldgen.structure.pools;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.structure.pools.types.ComposedTemplatePool;
import dev.wyck.worldgen.structure.pools.types.ReferencedTemplatePool;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * The weighted set of pieces a jigsaw block draws from.
 * A jigsaw structure starts from one pool, and every jigsaw block in a placed piece names the pool
 * its neighbor comes from, which is how a village grows streets and houses outward from its
 * center.
 *
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface TemplatePool extends Wrapper, Keyed {

    TemplatePool EMPTY = reference(ResourceKey.minecraft("empty"));

    /**
     * References a template pool already registered under the given key.
     * @param key the registry key of the template pool
     * @return a reference to the registered template pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedTemplatePool reference(ResourceKey key) {
        return ReferencedTemplatePool.of(key);
    }

    /**
     * Resolves this pool's key in Minecraft's template-pool registry and decodes the registered
     * value, keeping the key on the result.
     * @return the decoded template pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default TemplatePool wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.TEMPLATE_POOL).retrieveOrThrow(key);
        TemplatePool decoded = decode(minecraft);
        if (decoded instanceof ComposedTemplatePool composed && composed.resourceKey().isEmpty()) {
            return composed.toBuilder().resourceKey(key).build();
        }
        return decoded;
    }

    /**
     * Creates a new builder for an authored template pool.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedTemplatePool.Builder builder() {
        return ComposedTemplatePool.builder();
    }

    /**
     * Reads a Minecraft template pool, or a keyed template-pool holder, into a wrapper.
     * @param minecraftTemplatePool the template pool or template-pool holder to read
     * @return a reference to the template pool, or the decoded pool when it carries no key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static TemplatePool decode(Object minecraftTemplatePool) {
        record Holder() {
            static final Decoder<TemplatePool> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.pools.TemplatePoolDecoder");
        }
        return Holder.DECODER.decode(minecraftTemplatePool);
    }
}
