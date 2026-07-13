package dev.wyck.codegen;

import java.util.List;
import java.util.function.Function;

/**
 * Codegen target that mirrors a vanilla enum as a Wyck
 * {@code WrappedEnumerator} enum with a {@code KeyedEnumTranslator}.
 *
 * @param sourceEnum   the vanilla enum to mirror
 * @param keyExtractor produces the key string emitted for each constant.
 *                     Use {@link Generators#enumName(Enum)} to key by the
 *                     vanilla constant name (translator resolves via
 *                     {@code Enum.valueOf}), or
 *                     {@link Generators#serializedName(Enum)} to key by
 *                     {@code StringRepresentable#getSerializedName()}.
 */
public record EnumSpec(
    String outputPackage,
    String outputClass,
    Class<? extends Enum<?>> sourceEnum,
    Function<Enum<?>, String> keyExtractor,
    String javadoc,
    String since
) implements GeneratorSpec {

    public EnumSpec(
        String outputPackage,
        String outputClass,
        Class<? extends Enum<?>> sourceEnum,
        String javadoc,
        String since
    ) {
        this(outputPackage, outputClass, sourceEnum, Generators::enumName, javadoc, since);
    }

    /**
     * Multi-line javadoc variant. Lines are emitted verbatim into the class
     * javadoc; lines starting with a block tag (e.g. {@code @see}) are placed
     * after the closing {@code </p>}.
     */
    public EnumSpec(
        String outputPackage,
        String outputClass,
        Class<? extends Enum<?>> sourceEnum,
        List<String> javadocLines,
        String since
    ) {
        this(outputPackage, outputClass, sourceEnum, Generators::enumName, String.join("\n", javadocLines), since);
    }

    public EnumSpec(
        String outputPackage,
        String outputClass,
        Class<? extends Enum<?>> sourceEnum,
        Function<Enum<?>, String> keyExtractor,
        List<String> javadocLines,
        String since
    ) {
        this(outputPackage, outputClass, sourceEnum, keyExtractor, String.join("\n", javadocLines), since);
    }
}