package dev.wyck.wrapper;

import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.function.Function;

@NullMarked
@AsOf("2.0.0")
@ApiStatus.Internal
public interface KeyedEnumTranslator<W> {

    @AsOf("2.0.0")
    <N extends Enum<N>> N toNms(W wrapped, Class<N> nmsEnumClass);

    @AsOf("2.1.0")
    <N extends Enum<N>> W fromNms(N nmsEnum);

    /**
     * Translator that maps wrapper to NMS by uppercasing a key extracted from the wrapper,
     * and inverts by walking all wrapper constants and finding the one whose key matches
     * (case-insensitively) the NMS enum's name.
     *
     * @param keyExtractor extracts the key from a wrapper instance (typically lowercase)
     * @param wrapperValues all values of the wrapper enum (typically {@code MyEnum.values()})
     * @since 2.0.0
     */
    @AsOf("2.1.0")
    @Contract(value = "_, _ -> new", pure = true)
    static <W> KeyedEnumTranslator<W> byKey(Function<W, String> keyExtractor, W[] wrapperValues) {
        return new KeyedEnumTranslator<>() {
            @Override
            public <N extends Enum<N>> N toNms(W wrapped, Class<N> nmsEnumClass) {
                String key = keyExtractor.apply(wrapped);
                try {
                    return Enum.valueOf(nmsEnumClass, key.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("No constant '" + key + "' on " + nmsEnumClass.getName(), e);
                }
            }

            @Override
            public <N extends Enum<N>> W fromNms(N nmsEnum) {
                String nmsName = nmsEnum.name();
                for (W wrapper : wrapperValues) {
                    if (keyExtractor.apply(wrapper).equalsIgnoreCase(nmsName)) {
                        return wrapper;
                    }
                }
                throw new IllegalArgumentException("No wrapper constant maps to NMS enum '" + nmsName + "'");
            }
        };
    }

    /**
     * Translator that uses the wrapper enum's own {@code name()} as the key.
     * Inverse lookup uses {@code Enum.valueOf} on the wrapper class.
     *
     * @param wrapperClass the wrapper enum class, used for inverse lookup
     * @since 2.0.0
     */
    @AsOf("2.1.0")
    @Contract(value = "_ -> new", pure = true)
    static <W extends Enum<W>> KeyedEnumTranslator<W> byName(Class<W> wrapperClass) {
        return new KeyedEnumTranslator<>() {
            @Override
            public <N extends Enum<N>> N toNms(W wrapped, Class<N> nmsEnumClass) {
                try {
                    return Enum.valueOf(nmsEnumClass, wrapped.name());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("No constant '" + wrapped.name() + "' on " + nmsEnumClass.getName(), e);
                }
            }

            @Override
            public <N extends Enum<N>> W fromNms(N nmsEnum) {
                try {
                    return Enum.valueOf(wrapperClass, nmsEnum.name());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("No wrapper constant '" + nmsEnum.name() + "' on " + wrapperClass.getName(), e);
                }
            }
        };
    }
}