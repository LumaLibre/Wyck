package dev.wyck.codegen;

import com.palantir.javapoet.ClassName;
import net.minecraft.core.Registry;

/**
 * Codegen target that mirrors every entry of a Minecraft registry as a Wyck
 * {@code WrappedConstant} enum.
 *
 * @param outputPackage the package of the generated enum
 * @param outputSimpleClassName the simple name of the generated enum
 * @param sourceRegistry the Minecraft registry to enumerate
 * @param registryId the {@code RegistryId} constant used by the generated translator
 * @param javadoc the generated enum's type documentation
 * @param since the Wyck version that introduced the enum
 */
public record RegistryConstantSpec(
    String outputPackage,
    String outputSimpleClassName,
    Registry<?> sourceRegistry,
    String registryId,
    String javadoc,
    String since
) implements GeneratorSpec {

    @Override
    public ClassName outputClass() {
        return ClassName.get(outputPackage, outputSimpleClassName);
    }
}
