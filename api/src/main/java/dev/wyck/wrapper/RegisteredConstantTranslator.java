package dev.wyck.wrapper;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Translates wrapper constants to and from the entries of a Minecraft registry,
 * the registry counterpart to {@link KeyedEnumTranslator}.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Internal
public interface RegisteredConstantTranslator<W> {

    @AsOf("3.0.0")
    <N> N toNms(W constant);

    @AsOf("3.0.0")
    <N> W fromNms(N nmsConstant);
    

    @AsOf("3.0.0")
    static <W> RegisteredConstantTranslator<W> of(String name, Function<W, ResourceKey> keyExtractor, W[] values) {
        return of(WyckRegistry.lazy(ResourceKey.minecraft(name)), keyExtractor, values);
    }

    @AsOf("3.0.0")
    static <W> RegisteredConstantTranslator<W> of(RegistryId registry, Function<W, ResourceKey> keyExtractor, W[] values) {
        return of(WyckRegistry.lazy(registry), keyExtractor, values);
    }

    /**
     * Translator that resolves a wrapper against the given registry by the key extracted from it
     * and inverts through a lazily built identity map from the resolved registry value back to the wrapper.
     *
     * @param passedRegistry the registry to resolve against
     * @param keyExtractor extracts the registry key from a wrapper instance
     * @param values all values of the wrapper enum
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @Contract(value = "_, _, _ -> new", pure = true)
    static <W> RegisteredConstantTranslator<W> of(Lazy<WyckRegistry> passedRegistry, Function<W, ResourceKey> keyExtractor, W[] values) {
        return new RegisteredConstantTranslator<>() {
            private final Lazy<WyckRegistry> registry = passedRegistry;
            private final Lazy<Map<Object, W>> inverse = Lazy.of(() -> {
                Map<Object, W> map = new IdentityHashMap<>();
                WyckRegistry frozen = this.registry.get();
                for (W wrapper : values) {
                    ResourceKey key = keyExtractor.apply(wrapper);
                    map.put(Preconditions.checkNotNull(frozen.retrieve(key), "Registry value for %s is null", key), wrapper);
                }
                return map;
            });

            @Override
            public <N> N toNms(W constant) {
                ResourceKey key = keyExtractor.apply(constant);
                return Preconditions.checkNotNull(this.registry.get().retrieve(key), "Registry value for %s is null", key);
            }

            @Override
            public <N> W fromNms(N nmsConstant) {
                W wrapper = this.inverse.get().get(nmsConstant);
                if (wrapper == null) {
                    throw new IllegalArgumentException("No wrapper constant maps to registry value '" + nmsConstant + "'");
                }
                return wrapper;
            }
            
        };
    }
}