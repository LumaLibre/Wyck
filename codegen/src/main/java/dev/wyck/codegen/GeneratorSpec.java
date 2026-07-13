package dev.wyck.codegen;

/**
 * A single codegen target. Either a keyed-reference constants class
 * ({@link ReferenceSpec}), a wrapped vanilla enum ({@link EnumSpec}),
 * or a wrapped-constant enum minted from a vanilla registry holder
 * class ({@link ConstantSpec}).
 */
public sealed interface GeneratorSpec permits ReferenceSpec, EnumSpec, ConstantSpec {

    String outputPackage();

    String outputClass();

    String javadoc();

    String since();
}