package dev.wyck.decode.tags;

import dev.wyck.biome.Biome;
import dev.wyck.decode.Decoders;
import dev.wyck.tags.TagSet;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@NullMarked
@ApiStatus.Internal
public final class BiomeTagSetDecoder implements Decodable<TagSet<Biome>, HolderSet<net.minecraft.world.level.biome.Biome>> {

    @Override
    public TagSet<Biome> decode(HolderSet<net.minecraft.world.level.biome.Biome> minecraftObject) {
        return minecraftObject.unwrapKey()
            .map(tag -> TagSet.ofBiomeTag(Decoders.key(tag.location())))
            .orElseGet(() -> TagSet.ofBiomes(minecraftObject.stream()
                .map(holder -> Biome.reference(Decoders.referenceKey(holder)))
                .collect(Collectors.toCollection(LinkedHashSet::new))));
    }
}
