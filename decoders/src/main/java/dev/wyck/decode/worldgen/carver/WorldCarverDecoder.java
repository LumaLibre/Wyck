package dev.wyck.decode.worldgen.carver;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.carver.WorldCarver;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.WorldCarverType;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import net.minecraft.core.Holder;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class WorldCarverDecoder implements Decodable<WorldCarver, Object> {

    @Override
    public WorldCarver decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return WorldCarver.reference(Decoders.referenceKey(holder));
        }
        net.minecraft.world.level.levelgen.carver.WorldCarver configured = Decoders.value(minecraftObject);
        dev.wyck.keys.ResourceKey typeKey = Decoders.registryKey(
            net.minecraft.core.registries.BuiltInRegistries.CARVER_TYPE,
            configured.codec()
        );
        WorldCarverType type = java.util.Arrays.stream(WorldCarverType.values())
            .filter(candidate -> candidate.key().equals(typeKey.value()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported world carver type '" + typeKey + "'"));
        return ComposedCarver.of(
            type,
            new CarverConfigurationDecoders().decode(configured)
        );
    }
}
