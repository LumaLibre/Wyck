package dev.wyck.worldgen.synth;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.kyori.adventure.key.Key;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedNoiseParametersImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override int firstOctave,
    @Override List<Double> amplitudes
) implements ComposedNoiseParameters {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.NOISE);

    public ComposedNoiseParametersImpl {
        amplitudes = List.copyOf(amplitudes);
    }

    @Override
    public Object toMinecraft() {
        return new NormalNoise.NoiseParameters(this.firstOctave, new DoubleArrayList(this.amplitudes));
    }

    @Override
    public ComposedNoiseParameters register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Can't register noise parameters without a resource key"));
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}