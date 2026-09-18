package dev.wyck.codegen;

import com.palantir.javapoet.ClassName;

/**
 * A single codegen target. Either a keyed-reference constants class
 * ({@link ReferenceSpec}), a wrapped vanilla enum ({@link EnumSpec}),
 * a wrapped-constant enum minted from a vanilla registry holder class
 * ({@link ConstantSpec}), or a wrapped-constant enum minted directly
 * from a registry ({@link RegistryConstantSpec}).
 */
public sealed interface GeneratorSpec permits ReferenceSpec, EnumSpec, ConstantSpec, RegistryConstantSpec {

    ClassName outputClass();

    String javadoc();

    String since();
}
