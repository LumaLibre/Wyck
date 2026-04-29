package me.outspending.biomesapi.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@FunctionalInterface
public interface KeyedEnumTranslator<W> {

    <N extends Enum<N>> N toNms(W wrapped, Class<N> nmsEnumClass);

    /**
     * Default translator that uppercases the wrapper's key and looks up the
     * matching constant on the NMS enum class.
     */
    @Contract(value = "_ -> new", pure = true)
    static <W> @NotNull KeyedEnumTranslator<W> byKey(java.util.function.Function<W, String> keyExtractor) {
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
        };
    }

    /**
     * Translator that uses the wrapper enum's own {@code name()} (already uppercase).
     */
    @Contract(value = " -> new", pure = true)
    static <W extends Enum<W>> @NotNull KeyedEnumTranslator<W> byName() {
        return new KeyedEnumTranslator<>() {
            @Override
            public <N extends Enum<N>> N toNms(W wrapped, Class<N> nmsEnumClass) {
                try {
                    return Enum.valueOf(nmsEnumClass, wrapped.name());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "No constant '" + wrapped.name() + "' on " + nmsEnumClass.getName(), e);
                }
            }
        };
    }
}