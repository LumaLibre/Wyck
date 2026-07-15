package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.synth.NoiseParameters;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NoiseParameterFunctionImpl implements NoiseParameterFunction {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    protected final Optional<ResourceKey> resourceKey;
    protected final NoiseParameters noiseParameters;

    public NoiseParameterFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters) {
        this.resourceKey = resourceKey;
        this.noiseParameters = noiseParameters;
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return resourceKey;
    }

    @Override
    public NoiseParameters noiseParameters() {
        return noiseParameters;
    }

    @Override
    public NoiseParameterFunction register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
