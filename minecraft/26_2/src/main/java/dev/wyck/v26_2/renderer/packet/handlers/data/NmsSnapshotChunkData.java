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

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class NmsSnapshotChunkData implements SnapshotChunkData {

    private final ChunkLocation location;
    private final LevelChunkSection[] sections;

    private final Map<Holder<net.minecraft.world.level.biome.Biome>, Biome> bukkitBiomes = new IdentityHashMap<>();

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
            c = toBukkit(sections[0].getNoiseBiome(CENTER_NOISE_XZ, 0, CENTER_NOISE_XZ));
            this.center = c;
        }
        return c;
    }

    @Override
    public Biome biomeAt(int x, int y, int z) {
        LevelChunkSection section = sections[y >> 4];
        return toBukkit(section.getNoiseBiome((x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2));
    }

    private Biome toBukkit(Holder<net.minecraft.world.level.biome.Biome> holder) {
        return bukkitBiomes.computeIfAbsent(holder, CraftBiome::minecraftHolderToBukkit);
    }

    @Override
    public Optional<ChunkSnapshot> bukkitSnapshot() {
        return Optional.empty();
    }
}