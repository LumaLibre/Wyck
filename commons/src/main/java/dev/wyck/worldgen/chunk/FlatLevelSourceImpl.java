package dev.wyck.worldgen.chunk;

import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FlatLevelSourceImpl extends ChunkGeneratorImpl implements FlatLevelSource {

    private final FlatLevelGeneratorSettings settings;

    public FlatLevelSourceImpl(BiomeSource biomeSource, FlatLevelGeneratorSettings settings) {
        super(biomeSource);
        this.settings = settings;
    }

    @Override
    public FlatLevelGeneratorSettings settings() {
        return this.settings;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.FlatLevelSource(
            settings.asHandle(),
            biomeSource.asHandle()
        );
    }
}
