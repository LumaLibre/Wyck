package me.outspending.biomesapi.codegen;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record GeneratorSpec(
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

    public GeneratorSpec(
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
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, null);
    }

    public GeneratorSpec(
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
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, null, null);
    }
}