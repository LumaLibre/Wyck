package me.outspending.biomesapi.wrapper.level.noise;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class LevelNoiseGeneratorSettingsImpl implements LevelNoiseGeneratorSettings {

    private final Data data;
    private final Lazy<NoiseGeneratorSettings> cached = Lazy.of(this::build);

    public LevelNoiseGeneratorSettingsImpl(Data data) {
        this.data = data;
    }

    @Override
    public Data data() {
        return this.data;
    }

    @Override
    public Object toMinecraft() {
        return this.cached.get();
    }

    private NoiseGeneratorSettings build() {
        NoiseSettings noiseSettings = (NoiseSettings) this.data.noiseSettings().toMinecraft();
        BlockState defaultBlock = blockState(this.data.defaultBlock());
        BlockState defaultFluid = blockState(this.data.defaultFluid());
        NoiseRouter noiseRouter = (NoiseRouter) this.data.noiseRouter().toMinecraft();
        SurfaceRules.RuleSource surfaceRule = (SurfaceRules.RuleSource) this.data.surfaceRule().toMinecraft();
        List<Climate.ParameterPoint> spawnTarget = spawnTarget(this.data.spawnTarget());
        return new NoiseGeneratorSettings(
            noiseSettings,
            defaultBlock,
            defaultFluid,
            noiseRouter,
            surfaceRule,
            spawnTarget,
            this.data.seaLevel(),
            this.data.disableMobGeneration(),
            this.data.aquifersEnabled(),
            this.data.oreVeinsEnabled(),
            this.data.useLegacyRandomSource()
        );
    }

    private static BlockState blockState(Material material) {
        Block block = CraftMagicNumbers.getBlock(material);
        Preconditions.checkNotNull(block, "Material '%s' is not a block", material);

        return block.defaultBlockState();
    }

    private static List<Climate.ParameterPoint> spawnTarget(List<ClimatePoint> points) {
        List<Climate.ParameterPoint> result = new ArrayList<>();
        for (ClimatePoint point : points) {
            result.add((Climate.ParameterPoint) point.toMinecraft());
        }
        return result;
    }
}