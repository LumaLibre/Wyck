package dev.wyck.wrapper.worldgen.carver.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface ReferencedCarver extends ConfiguredWorldCarver {

    @ApiStatus.Internal
    ConstructWireProvider<ReferencedCarver> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.types.ReferencedCarverImpl");

    @AsOf("3.0.0")
    static ReferencedCarver of(ResourceKey key) {
        return WIRE.construct(key);
    }
}
