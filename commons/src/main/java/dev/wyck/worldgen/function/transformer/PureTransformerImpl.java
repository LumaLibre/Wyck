package dev.wyck.worldgen.function.transformer;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.transformer.PureTransformer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class PureTransformerImpl implements PureTransformer {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    protected final Optional<ResourceKey> resourceKey;
    protected final DensityFunction input;

    public PureTransformerImpl(Optional<ResourceKey> resourceKey, DensityFunction input) {
        this.resourceKey = resourceKey;
        this.input = input;
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return this.resourceKey;
    }

    @Override
    public DensityFunction input() {
        return this.input;
    }

    @Override
    public PureTransformer register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
