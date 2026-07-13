package dev.wyck.codegen;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Codegen target that mirrors a vanilla registry holder class (e.g.
 * {@code Fluids}) as a Wyck {@code WrappedConstant} enum backed by a
 * {@code RegisteredConstantTranslator}.
 *
 * <p>Static fields on the source classes become enum constants; each is
 * keyed by its registry path (resolved via {@code registryLookup}).</p>
 *
 * @param registryType   only static fields assignable to this type are
 *                       emitted (unless {@code fieldFilter} overrides)
 * @param registryLookup resolves a field value to its registry
 *                       {@link Identifier}; the path becomes the key
 * @param registryId     the {@code RegistryId} constant name passed to
 *                       {@code RegisteredConstantTranslator.of}, e.g.
 *                       {@code "FLUID"}
 */
public record ConstantSpec(
    String outputPackage,
    String outputClass,
    Class<?> registryType,
    Function<Object, Identifier> registryLookup,
    List<Class<?>> sourceClasses,
    String registryId,
    String javadoc,
    String since,
    @Nullable Predicate<Field> fieldFilter
) implements GeneratorSpec {

    public ConstantSpec(
        String outputPackage,
        String outputClass,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String registryId,
        String javadoc,
        String since
    ) {
        this(outputPackage, outputClass, registryType, registryLookup, sourceClasses, registryId, javadoc, since, null);
    }

    /**
     * Multi-line javadoc variant. Lines are emitted verbatim into the class
     * javadoc; lines starting with a block tag (e.g. {@code @see}) are placed
     * after the closing {@code </p>}.
     */
    public ConstantSpec(
        String outputPackage,
        String outputClass,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String registryId,
        List<String> javadocLines,
        String since
    ) {
        this(outputPackage, outputClass, registryType, registryLookup, sourceClasses, registryId, String.join("\n", javadocLines), since, null);
    }
}