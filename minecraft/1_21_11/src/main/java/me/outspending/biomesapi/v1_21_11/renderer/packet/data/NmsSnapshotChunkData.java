package me.outspending.biomesapi.v1_21_11.renderer.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.misc.ChunkLocation;
import me.outspending.biomesapi.renderer.packet.data.SnapshotChunkData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A {@link SnapshotChunkData} backed by the chunk sections already decoded from the outgoing
 * packet. Construction is free (it only holds the section array); biome reads are computed on
 * demand and the center biome is memoized.
 *
 * <p>This is single-threaded by contract: the sections are local to one packet on one Netty
 * thread, so no synchronization is needed.
 *
 * @version 2.2.0
 * @since 2.2.0
 * @author Jsinco
 */
@AsOf("2.2.0")
public final class NmsSnapshotChunkData implements SnapshotChunkData {

    // Noise biomes are stored at 4x4x4 per section: a block coordinate maps to a noise
    // cell by >>2. Block 7 -> noise cell 1 (the legacy center sample at block 7,0,7).
    private static final int CENTER_NOISE_XZ = 1;

    private final ChunkLocation location;
    private final LevelChunkSection[] sections;

    private Biome center; // memoized; null until first read

    public NmsSnapshotChunkData(@NotNull ChunkLocation location, @NotNull LevelChunkSection[] sections) {
        this.location = location;
        this.sections = sections;
    }

    @Override
    public @NotNull ChunkLocation location() {
        return location;
    }

    @Override
    public @NotNull Biome centerBiome() {
        Biome c = this.center;
        if (c == null) {
            Holder<net.minecraft.world.level.biome.@NotNull Biome> holder =
                    sections[0].getNoiseBiome(CENTER_NOISE_XZ, 0, CENTER_NOISE_XZ);
            c = CraftBiome.minecraftHolderToBukkit(holder);
            this.center = c;
        }
        return c;
    }

    @Override
    public @NotNull Biome biomeAt(int x, int y, int z) {
        int sectionIdx = y >> 4;
        LevelChunkSection section = sections[sectionIdx];
        Holder<net.minecraft.world.level.biome.@NotNull Biome> holder =
                section.getNoiseBiome((x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2);
        return CraftBiome.minecraftHolderToBukkit(holder);
    }

    @Override
    public @NotNull Optional<ChunkSnapshot> bukkitSnapshot() {
        // No cheap, thread-safe snapshot is available from packet sections.
        return Optional.empty();
    }
}