package dev.wyck.test.bootstrap.decode;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.function.misc.RangeChoice;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import dev.wyck.worldgen.function.noise.NoiseFunction;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import dev.wyck.worldgen.synth.ComposedNoiseParameters;
import dev.wyck.worldgen.synth.NoiseParameters;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class DensityFunctionDecodeTest {

    private static final Set<ResourceKey> UNWRAPPED = Set.of(
        key("beardifier"),
        key("distance_to_point"),
        key("floor"),
        key("round"),
        key("ceil"),
        key("truncate"),
        key("pow"),
        key("spline"),
        key("lerp"),
        key("interval_select"),
        key("slice"),
        key("old_blended_noise")
    );

    private static dev.wyck.decode.worldgen.function.DensityFunctionDecoders decoders() {
        record Holder() {
            static final dev.wyck.decode.worldgen.function.DensityFunctionDecoders INSTANCE =
                new dev.wyck.decode.worldgen.function.DensityFunctionDecoders();
        }
        return Holder.INSTANCE;
    }

    private static JsonElement encode(net.minecraft.world.level.levelgen.densityfunction.DensityFunction function) {
        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);
        return DensityFunctions.DIRECT_CODEC.encodeStart(ops, function).getOrThrow(IllegalStateException::new);
    }

    @Test
    void everyVanillaDensityFunctionTypeIsDecodedOrExplicitlyUnwrapped() {
        List<ResourceKey> missing = BuiltInRegistries.DENSITY_FUNCTION_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !UNWRAPPED.contains(key))
            .filter(key -> !decoders().handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no density function decoder is registered for: " + missing);
    }

    @Test
    void nothingListedAsUnwrappedActuallyHasADecoder() {
        List<ResourceKey> stale = UNWRAPPED.stream().filter(decoders()::handles).toList();
        assertTrue(stale.isEmpty(), () -> "these types have a decoder and should leave the unwrapped list: " + stale);
    }

    @Test
    void decodingKeepsTheShapeOfAComposedFunction() {
        DensityFunction original = DensityFunction
            .noise(NoiseParameters.of(-7, List.of(1.0, 1.0, 2.0)), 0.25, 0.0)
            .clamp(-1.0, 1.0)
            .flatCache();

        Marker marker = assertInstanceOf(Marker.class, DensityFunction.decode(original.asHandle()));
        assertEquals(Marker.Type.CACHE, marker.type());

        ClampedTransformer clamp = assertInstanceOf(ClampedTransformer.class, marker.input());
        assertEquals(-1.0, clamp.min());
        assertEquals(1.0, clamp.max());

        NoiseFunction noise = assertInstanceOf(NoiseFunction.class, clamp.input());
        assertEquals(0.25, noise.xzScale());
        assertEquals(0.0, noise.yScale());

        ComposedNoiseParameters parameters = assertInstanceOf(ComposedNoiseParameters.class, noise.noiseParameters());
        assertEquals(-7, parameters.firstOctave());
        assertEquals(List.of(1.0, 1.0, 2.0), parameters.amplitudes());
    }

    @Test
    void decodingReadsTheFieldsOfFunctionsWyckCannotCastTo() {
        DensityFunction original = DensityFunction.rangeChoice(
            DensityFunction.yClampedGradient(-64, 320, 1.5, -1.5),
            -0.5,
            0.5,
            DensityFunction.constant(3.0),
            DensityFunction.constant(4.0)
        );

        RangeChoice decoded = assertInstanceOf(RangeChoice.class, DensityFunction.decode(original.asHandle()));
        assertEquals(-0.5, decoded.minInclusive());
        assertEquals(0.5, decoded.maxExclusive());
        assertEquals(3.0, assertInstanceOf(ConstantSimpleFunction.class, decoded.whenInRange()).value());
        assertEquals(4.0, assertInstanceOf(ConstantSimpleFunction.class, decoded.whenOutOfRange()).value());

        YClampedGradient gradient = assertInstanceOf(YClampedGradient.class, decoded.input());
        assertEquals(-64, gradient.fromY());
        assertEquals(320, gradient.toY());
        assertEquals(1.5, gradient.fromValue());
        assertEquals(-1.5, gradient.toValue());
    }

    @Test
    void decodingKeepsReferencesToRegisteredFunctions() {
        ResourceKey key = key("overworld/continents");
        DensityFunction decoded = DensityFunction.decode(DensityFunction.reference(key).asHandle());

        assertInstanceOf(ReferencedDensityFunction.class, decoded);
        assertEquals(key, decoded.resourceKey().orElseThrow());
    }

    @Test
    void aNormalisedFunctionStillRoundTripsToTheSameSerializedForm() {
        DensityFunction original = DensityFunction.mappedNoise(
            NoiseParameters.reference(key("erosion")), 1.0, 0.0, -0.5, 0.5
        );

        DensityFunction decoded = DensityFunction.decode(original.asHandle());

        assertInstanceOf(TwoArgumentSimpleFunction.class, decoded);
        assertEquals(encode(original.asHandle()), encode(decoded.asHandle()));
    }

    @Test
    void everySlotOfTheVanillaOverworldNoiseRouterHasAKnownTopLevelType() {
        var settings = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS)
            .getOrThrow(net.minecraft.world.level.levelgen.NoiseGeneratorSettings.OVERWORLD)
            .value();
        var router = settings.noiseRouter();

        for (net.minecraft.world.level.levelgen.densityfunction.DensityFunction slot : List.of(
            router.temperature(),
            router.vegetation(),
            router.continents(),
            router.erosion(),
            router.depth(),
            router.ridges(),
            router.chunkSurfaceLevel(),
            router.finalDensity()
        )) {
            ResourceKey type = decoders().typeOf(slot);
            assertTrue(decoders().handles(type) || UNWRAPPED.contains(type), () -> "unknown router slot type " + type);
        }
    }

    private static ResourceKey key(String path) {
        return ResourceKey.minecraft(path);
    }
}
