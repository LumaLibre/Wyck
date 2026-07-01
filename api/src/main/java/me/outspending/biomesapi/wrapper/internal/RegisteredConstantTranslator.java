package me.outspending.biomesapi.wrapper.internal;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.registry.internal.Referer;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import me.outspending.biomesapi.util.Lazy;
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
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface RegisteredConstantTranslator<W extends ConstantRepresentable> {

    @AsOf("2.4.0")
    <N> N toNms(W constant);

    @AsOf("2.4.0")
    <N> W fromNms(N nmsConstant);

    /**
     * A string codec over this wrapper type, using the same key mapping as the translator.
     */
    @AsOf("2.4.0")
    Codec<W> codec();

    @AsOf("2.4.0")
    static <W extends ConstantRepresentable> RegisteredConstantTranslator<W> of(String name, Function<W, ResourceKey> keyExtractor, W[] values) {
        return of(FrozenRegistry.lazy(ResourceKey.minecraft(name)), keyExtractor, values);
    }

    @AsOf("2.4.0")
    static <W extends ConstantRepresentable> RegisteredConstantTranslator<W> of(Referer registry, Function<W, ResourceKey> keyExtractor, W[] values) {
        return of(FrozenRegistry.lazy(registry), keyExtractor, values);
    }

    /**
     * Translator that resolves a wrapper against the given registry by the key extracted from it
     * and inverts through a lazily built identity map from the resolved registry value back to the wrapper.
     *
     * @param passedRegistry the registry to resolve against
     * @param keyExtractor extracts the registry key from a wrapper instance
     * @param values all values of the wrapper enum
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Contract(value = "_, _, _ -> new", pure = true)
    static <W extends ConstantRepresentable> RegisteredConstantTranslator<W> of(Lazy<FrozenRegistry> passedRegistry, Function<W, ResourceKey> keyExtractor, W[] values) {
        return new RegisteredConstantTranslator<>() {
            private final Lazy<FrozenRegistry> registry = passedRegistry;
            private final Lazy<Map<Object, W>> inverse = Lazy.of(() -> {
                Map<Object, W> map = new IdentityHashMap<>();
                FrozenRegistry frozen = this.registry.get();
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

            @Override
            public Codec<W> codec() {
                return ConstantRepresentable.codec(values);
            }
        };
    }
}