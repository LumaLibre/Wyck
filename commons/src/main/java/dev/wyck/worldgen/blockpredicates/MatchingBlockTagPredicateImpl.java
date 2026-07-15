package dev.wyck.worldgen.blockpredicates;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.blockpredicates.MatchingBlockTagPredicate;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MatchingBlockTagPredicateImpl(
    @Override BlockVector offset,
    @Override ResourceKey tag
) implements MatchingBlockTagPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesTag(
            WorldgenConversions.toVec3i(offset),
            net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, tag.asHandle())
        );
    }
}