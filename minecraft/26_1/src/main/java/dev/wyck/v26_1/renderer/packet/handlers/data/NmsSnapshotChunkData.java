package dev.wyck.v26_1.renderer.packet.handlers.data;

import dev.wyck.annotations.AsOf;
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
@AsOf("2.2.0")
@ApiStatus.Internal
public final class NmsSnapshotChunkData implements SnapshotChunkData {

    private final ChunkLocation location;
    private final LevelChunkSection[] sections;

    private @Nullable Biome center; // memoized; null until first read

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
        // No cheap, thread-safe snapshot is available from packet sections.
        return Optional.empty();
    }
}