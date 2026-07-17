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
import net.minecraft.world.level.levelgen.DensityFunction.SinglePointContext;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class DensityFunctionTest {

    private static final SinglePointContext ORIGIN = new SinglePointContext(0, 0, 0);
    private static final double EXACT = 0.0;

    private static double compute(DensityFunction function) {
        return function.<net.minecraft.world.level.levelgen.DensityFunction>asHandle().compute(ORIGIN);
    }

    @Test
    void aConstantComputesItsValue() {
        assertEquals(7.5, compute(ConstantSimpleFunction.of(7.5)), EXACT);
    }

    @Test
    void addSumsBothArguments() {
        DensityFunction sum = TwoArgumentSimpleFunction.add(
                ConstantSimpleFunction.of(2.0), ConstantSimpleFunction.of(3.5));

        assertEquals(5.5, compute(sum), EXACT);
    }

    @Test
    void mulMultipliesBothArguments() {
        DensityFunction product = TwoArgumentSimpleFunction.mul(
                ConstantSimpleFunction.of(3.0), ConstantSimpleFunction.of(4.0));

        assertEquals(12.0, compute(product), EXACT);
    }

    @Test
    void minAndMaxPickTheRightArgumentInEitherOrder() {
        assertEquals(2.0, compute(TwoArgumentSimpleFunction.min(
                ConstantSimpleFunction.of(2.0), ConstantSimpleFunction.of(9.0))), EXACT);
        assertEquals(2.0, compute(TwoArgumentSimpleFunction.min(
                ConstantSimpleFunction.of(9.0), ConstantSimpleFunction.of(2.0))), EXACT);
        assertEquals(9.0, compute(TwoArgumentSimpleFunction.max(
                ConstantSimpleFunction.of(2.0), ConstantSimpleFunction.of(9.0))), EXACT);
        assertEquals(9.0, compute(TwoArgumentSimpleFunction.max(
                ConstantSimpleFunction.of(9.0), ConstantSimpleFunction.of(2.0))), EXACT);
    }

    @Test
    void clampHoldsValuesInsideItsRange() {
        assertEquals(1.0, compute(ClampedTransformer.of(ConstantSimpleFunction.of(5.0), -1.0, 1.0)), EXACT);
        assertEquals(-1.0, compute(ClampedTransformer.of(ConstantSimpleFunction.of(-5.0), -1.0, 1.0)), EXACT);
    }

    @Test
    void clampLeavesValuesInsideItsRangeAlone() {
        assertEquals(0.25, compute(ClampedTransformer.of(ConstantSimpleFunction.of(0.25), -1.0, 1.0)), EXACT);
    }

    @Test
    void nestedFunctionsComposeInTheRightOrder() {
        DensityFunction nested = ClampedTransformer.of(
                TwoArgumentSimpleFunction.add(
                        TwoArgumentSimpleFunction.mul(ConstantSimpleFunction.of(2.0), ConstantSimpleFunction.of(4.0)),
                        ConstantSimpleFunction.of(1.0)),
                0.0, 5.0);

        // (2 * 4) + 1 = 9, clamped to 5
        assertEquals(5.0, compute(nested), EXACT);
    }

    @Test
    void aClampReportsItsRangeToVanilla() {
        net.minecraft.world.level.levelgen.DensityFunction clamped =
                ClampedTransformer.of(ConstantSimpleFunction.of(0.5), -2.0, 3.0).asHandle();

        assertEquals(-2.0, clamped.minValue(), EXACT);
        assertEquals(3.0, clamped.maxValue(), EXACT);
    }

    @Test
    void densityFunctionBindsInTheRegistry() {
        ConstantSimpleFunction densityFunction = ConstantSimpleFunction.of(ResourceKey.of("wyck:constant"), 1.0);
        densityFunction.register();

        Registry<net.minecraft.world.level.levelgen.DensityFunction> registry =
                BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);
        net.minecraft.world.level.levelgen.DensityFunction registered =
                registry.getValue(Identifier.parse("wyck:constant"));

        assertNotNull(registered, "density function never landed in worldgen/density_function");
        assertEquals(1.0, registered.compute(ORIGIN), EXACT);
    }

    @Test
    void registeringADensityFunctionLeavesVanillaOnesAlone() {
        ConstantSimpleFunction.of(ResourceKey.of("wyck:coexist"), 2.0).register();

        Registry<net.minecraft.world.level.levelgen.DensityFunction> registry =
                BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DENSITY_FUNCTION);

        assertNotNull(registry.getValue(Identifier.parse("wyck:coexist")));
        assertNotNull(registry.getValue(Identifier.parse("minecraft:overworld/depth")),
                "registering dropped a vanilla density function");
    }
}
