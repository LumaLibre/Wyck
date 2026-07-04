package dev.wyck.v26_2.renderer.packet.handlers.data;

import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class NmsSnapshotChunkData implements SnapshotChunkData {

    private final ChunkLocation location;
    private final LevelChunkSection[] sections;

    private @Nullable Biome center;

    public NmsSnapshotChunkData(ChunkLocation location, LevelChunkSection[] sections) {
        this.location = location;
        this.sections = sections;
    }

    @Override
    public ChunkLocation location() {
        return location;
    }

    @Override
    public Biome centerBiome() {
        Biome c = this.center;
        if (c == null) {
            Holder<net.minecraft.world.level.biome.Biome> holder =
                    sections[0].getNoiseBiome(CENTER_NOISE_XZ, 0, CENTER_NOISE_XZ);
            c = CraftBiome.minecraftHolderToBukkit(holder);
            this.center = c;
        }
        return c;
    }

    @Override
    public Biome biomeAt(int x, int y, int z) {
        int sectionIdx = y >> 4;
        LevelChunkSection section = sections[sectionIdx];
        Holder<net.minecraft.world.level.biome.Biome> holder =
                section.getNoiseBiome((x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2);
        return CraftBiome.minecraftHolderToBukkit(holder);
    }

    @Override
    public Optional<ChunkSnapshot> bukkitSnapshot() {
        return Optional.empty();
    }
}