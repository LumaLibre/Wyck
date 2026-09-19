package dev.wyck.test.bootstrap;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class DensityFunctionTest {

    private static final double EXACT = 0.0;

    private static double compute(DensityFunction function) {
        return DensityTestSupport.sample(function.asHandle());
    }

    @Test
    void aConstantComputesItsValue() {
        assertEquals(7.5, compute(ConstantSimpleFunction.of(7.5)), EXACT);
    }

    @Test
    void binaryOperationsComputeTheirValues() {
        DensityFunction two = ConstantSimpleFunction.of(2.0);
        DensityFunction four = ConstantSimpleFunction.of(4.0);
        assertEquals(6.0, compute(TwoArgumentSimpleFunction.add(two, four)), EXACT);
        assertEquals(-2.0, compute(TwoArgumentSimpleFunction.sub(two, four)), EXACT);
        assertEquals(8.0, compute(TwoArgumentSimpleFunction.mul(two, four)), EXACT);
        assertEquals(0.5, compute(TwoArgumentSimpleFunction.div(two, four)), EXACT);
        assertEquals(2.0, compute(TwoArgumentSimpleFunction.min(two, four)), EXACT);
        assertEquals(4.0, compute(TwoArgumentSimpleFunction.max(two, four)), EXACT);
    }

    @Test
    void clampHoldsValuesInsideItsRange() {
        assertEquals(1.0, compute(ClampedTransformer.of(ConstantSimpleFunction.of(5.0), -1.0, 1.0)), EXACT);
        assertEquals(-1.0, compute(ClampedTransformer.of(ConstantSimpleFunction.of(-5.0), -1.0, 1.0)), EXACT);
        assertEquals(0.25, compute(ClampedTransformer.of(ConstantSimpleFunction.of(0.25), -1.0, 1.0)), EXACT);
    }

    @Test
    void nestedFunctionsComposeInTheRightOrder() {
        DensityFunction nested = ClampedTransformer.of(
            TwoArgumentSimpleFunction.add(
                TwoArgumentSimpleFunction.mul(ConstantSimpleFunction.of(2.0), ConstantSimpleFunction.of(4.0)),
                ConstantSimpleFunction.of(1.0)
            ),
            0.0,
            5.0
        );
        assertEquals(5.0, compute(nested), EXACT);
    }

    @Test
    void aClampReportsItsRangeToVanilla() {
        net.minecraft.world.level.levelgen.densityfunction.DensityFunction clamped =
            ClampedTransformer.of(ConstantSimpleFunction.of(0.5), -2.0, 3.0).asHandle();
        assertEquals(0.5, clamped.range().min(), EXACT);
        assertEquals(0.5, clamped.range().max(), EXACT);
    }

    @Test
    void densityFunctionBindsInTheRegistry() {
        ConstantSimpleFunction.of(ResourceKey.of("wyck:constant"), 1.0).register();
        Registry<net.minecraft.world.level.levelgen.densityfunction.DensityFunction> registry =
            BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        net.minecraft.world.level.levelgen.densityfunction.DensityFunction registered =
            registry.getValue(Identifier.parse("wyck:constant"));
        assertNotNull(registered, "density function never landed in worldgen/density_function");
        assertEquals(1.0, DensityTestSupport.sample(registered), EXACT);
    }

    @Test
    void registeringADensityFunctionLeavesVanillaOnesAlone() {
        ConstantSimpleFunction.of(ResourceKey.of("wyck:coexist"), 2.0).register();
        Registry<net.minecraft.world.level.levelgen.densityfunction.DensityFunction> registry =
            BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        assertNotNull(registry.getValue(Identifier.parse("wyck:coexist")));
        assertNotNull(registry.getValue(Identifier.parse("minecraft:overworld/depth")));
    }
}
