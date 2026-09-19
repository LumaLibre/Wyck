package dev.wyck.decode.worldgen.carver;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.carver.WorldCarver;
import dev.wyck.worldgen.carver.WorldCarverType;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
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
        WorldCarverType type = WorldCarverType.TRANSLATOR.fromNms(configured.codec());
        return ComposedCarver.of(
            type,
            new CarverConfigurationDecoders().decode(configured)
        );
    }
}
