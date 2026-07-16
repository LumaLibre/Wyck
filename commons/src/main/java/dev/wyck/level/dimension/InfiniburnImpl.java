package dev.wyck.level.dimension;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.WorldgenConversions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record InfiniburnImpl(
    @Override Set<Material> blocks,
    @Override Optional<ResourceKey> tag
) implements Infiniburn {
    @Override
    public Object toMinecraft() {
        return this.asHolderSet();
    }

    public TagKey<Block> asTagKey() {
        Identifier id = tag.orElseThrow().identifier();
        return TagKey.create(Registries.BLOCK, id);
    }

    public HolderSet<Block> asHolderSet() {
        if (blocks.isEmpty() && tag.isEmpty()) {
            throw new IllegalStateException("Infiniburn must have either blocks or a tag");
        }

        if (!blocks.isEmpty()) {
            return WorldgenConversions.toBlockHolderSet(blocks);
        }

        Registry<Block> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BLOCK);
        return registry.getOrThrow(this.asTagKey());
    }
}
