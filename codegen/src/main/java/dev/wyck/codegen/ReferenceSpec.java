package dev.wyck.codegen;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record ReferenceSpec(
    String outputPackage,
    String outputClass,
    String typeSimpleName,
    String referenceCall,
    Class<?> registryType,
    Function<Object, Identifier> registryLookup,
    List<Class<?>> sourceClasses,
    String javadoc,
    String since,
    @Nullable String keyChain,
    @Nullable Predicate<Field> fieldFilter,
    boolean asInterface
) implements GeneratorSpec {

    public ReferenceSpec(
        String outputPackage,
        String outputClass,
        String typeSimpleName,
        String referenceCall,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String javadoc,
        String since,
        @Nullable String keyChain,
        @Nullable Predicate<Field> fieldFilter
    ) {
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, fieldFilter, false);
    }

    public ReferenceSpec(
        String outputPackage,
        String outputClass,
        String typeSimpleName,
        String referenceCall,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String javadoc,
        String since,
        @Nullable String keyChain
    ) {
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, null, false);
    }

    public ReferenceSpec(
        String outputPackage,
        String outputClass,
        String typeSimpleName,
        String referenceCall,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String javadoc,
        String since
    ) {
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, null, null, false);
    }

    /**
     * Emits {@code interface} instead of {@code final class}, for constants meant to be
     * inherited by a hand-written interface (e.g. {@code Easing extends Easings}).
     */
    public ReferenceSpec asConstantsInterface() {
        return new ReferenceSpec(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, fieldFilter, true);
    }
}