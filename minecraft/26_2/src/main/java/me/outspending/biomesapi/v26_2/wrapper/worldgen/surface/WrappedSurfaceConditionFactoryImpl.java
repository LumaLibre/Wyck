package me.outspending.biomesapi.v26_2.wrapper.worldgen.surface;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.wrapper.worldgen.surface.WrappedSurfaceCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class WrappedSurfaceConditionFactoryImpl implements WrappedSurfaceCondition.Factory {

    @Override
    public Object toNms(WrappedSurfaceCondition condition) {
        return switch (condition) {
            case WrappedSurfaceCondition.StoneDepth stoneDepth -> stoneDepth(stoneDepth);
            case WrappedSurfaceCondition.Not not -> not(not);
            case WrappedSurfaceCondition.Water water -> water(water);
            case WrappedSurfaceCondition.Biome biome -> biome(biome);
            case WrappedSurfaceCondition.Noise noise -> noise(noise);
            case WrappedSurfaceCondition.YCheck yCheck -> yCheck(yCheck);
            case WrappedSurfaceCondition.VerticalGradient verticalGradient -> verticalGradient(verticalGradient);
            case WrappedSurfaceCondition.Steep ignored -> SurfaceRules.steep();
            case WrappedSurfaceCondition.Hole ignored -> SurfaceRules.hole();
            case WrappedSurfaceCondition.AbovePreliminarySurface ignored -> SurfaceRules.abovePreliminarySurface();
            case WrappedSurfaceCondition.Temperature ignored -> SurfaceRules.temperature();
        };
    }

    private SurfaceRules.ConditionSource stoneDepth(WrappedSurfaceCondition.StoneDepth stoneDepth) {
        CaveSurface surfaceType = caveSurface(stoneDepth.surfaceType());
        return SurfaceRules.stoneDepthCheck(stoneDepth.offset(), stoneDepth.addSurfaceDepth(), stoneDepth.secondaryDepthRange(), surfaceType);
    }

    private SurfaceRules.ConditionSource not(WrappedSurfaceCondition.Not not) {
        SurfaceRules.ConditionSource target = (SurfaceRules.ConditionSource) not.target().toMinecraft();
        return SurfaceRules.not(target);
    }

    private SurfaceRules.ConditionSource water(WrappedSurfaceCondition.Water water) {
        if (water.addStoneDepth()) {
            return SurfaceRules.waterStartCheck(water.offset(), water.surfaceDepthMultiplier());
        }
        return SurfaceRules.waterBlockCheck(water.offset(), water.surfaceDepthMultiplier());
    }

    @SuppressWarnings("unchecked")
    protected SurfaceRules.ConditionSource biome(WrappedSurfaceCondition.Biome biome) {
        List<net.minecraft.resources.ResourceKey<Biome>> keys = new ArrayList<>();
        for (me.outspending.biomesapi.keys.ResourceKey key : biome.biomes()) {
            Identifier id = (Identifier) key.resourceLocation();
            keys.add(net.minecraft.resources.ResourceKey.create(Registries.BIOME, id));
        }
        net.minecraft.resources.ResourceKey<Biome>[] array = keys.toArray(net.minecraft.resources.ResourceKey[]::new);
        return SurfaceRules.isBiome(BootstrapSafeMinecraftRegistries.getter(Registries.BIOME), array);
    }

    protected SurfaceRules.ConditionSource noise(WrappedSurfaceCondition.Noise noise) {
        Identifier id = (Identifier) noise.noise().resourceLocation();
        net.minecraft.resources.ResourceKey<NormalNoise.NoiseParameters> key =
            net.minecraft.resources.ResourceKey.create(Registries.NOISE, id);
        return SurfaceRules.noiseCondition2d(key, noise.minThreshold(), noise.maxThreshold());
    }

    private SurfaceRules.ConditionSource yCheck(WrappedSurfaceCondition.YCheck yCheck) {
        VerticalAnchor anchor = (VerticalAnchor) yCheck.anchor().toMinecraft();
        if (yCheck.addStoneDepth()) {
            return SurfaceRules.yStartCheck(anchor, yCheck.surfaceDepthMultiplier());
        }
        return SurfaceRules.yBlockCheck(anchor, yCheck.surfaceDepthMultiplier());
    }

    private SurfaceRules.ConditionSource verticalGradient(WrappedSurfaceCondition.VerticalGradient verticalGradient) {
        VerticalAnchor trueAtAndBelow = (VerticalAnchor) verticalGradient.trueAtAndBelow().toMinecraft();
        VerticalAnchor falseAtAndAbove = (VerticalAnchor) verticalGradient.falseAtAndAbove().toMinecraft();
        return SurfaceRules.verticalGradient(verticalGradient.randomName(), trueAtAndBelow, falseAtAndAbove);
    }

    private CaveSurface caveSurface(WrappedSurfaceCondition.CaveSurface surface) {
        return switch (surface) {
            case FLOOR -> CaveSurface.FLOOR;
            case CEILING -> CaveSurface.CEILING;
        };
    }
}
