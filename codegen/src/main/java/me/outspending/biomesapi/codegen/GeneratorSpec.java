package me.outspending.biomesapi.codegen;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

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
    @Nullable String keyChain
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
        String since
    ) {
        this(outputPackage, outputClass, typeSimpleName, referenceCall, registryType, registryLookup, sourceClasses, javadoc, since, null);
    }
}