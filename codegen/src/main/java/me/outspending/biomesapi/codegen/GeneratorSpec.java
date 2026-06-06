package me.outspending.biomesapi.codegen;

import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.Function;

// AsOf 2.3.0
public record GeneratorSpec(
    String outputPackage,
    String outputClass,
    String typeSimpleName,
    String referenceCall,
    Class<?> registryType,
    Function<Object, Identifier> registryLookup,
    List<Class<?>> sourceClasses,
    String javadoc
) {
}