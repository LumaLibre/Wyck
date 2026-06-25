package me.outspending.biomesapi.v1_21_11.wrapper.worldgen.surface;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceCondition;
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
public class SurfaceConditionFactoryImpl implements SurfaceCondition.Factory {

    @Override
    public Object toNms(SurfaceCondition condition) {
        return switch (condition) {
            case SurfaceCondition.StoneDepth stoneDepth -> stoneDepth(stoneDepth);
            case SurfaceCondition.Not not -> not(not);
            case SurfaceCondition.Water water -> water(water);
            case SurfaceCondition.Biome biome -> biome(biome);
            case SurfaceCondition.Noise noise -> noise(noise);
            case SurfaceCondition.YCheck yCheck -> yCheck(yCheck);
            case SurfaceCondition.VerticalGradient verticalGradient -> verticalGradient(verticalGradient);
            case SurfaceCondition.Steep ignored -> SurfaceRules.steep();
            case SurfaceCondition.Hole ignored -> SurfaceRules.hole();
            case SurfaceCondition.AbovePreliminarySurface ignored -> SurfaceRules.abovePreliminarySurface();
            case SurfaceCondition.Temperature ignored -> SurfaceRules.temperature();
        };
    }

    private SurfaceRules.ConditionSource stoneDepth(SurfaceCondition.StoneDepth stoneDepth) {
        CaveSurface surfaceType = caveSurface(stoneDepth.surfaceType());
        return SurfaceRules.stoneDepthCheck(stoneDepth.offset(), stoneDepth.addSurfaceDepth(), stoneDepth.secondaryDepthRange(), surfaceType);
    }

    private SurfaceRules.ConditionSource not(SurfaceCondition.Not not) {
        SurfaceRules.ConditionSource target = (SurfaceRules.ConditionSource) not.target().toMinecraft();
        return SurfaceRules.not(target);
    }

    private SurfaceRules.ConditionSource water(SurfaceCondition.Water water) {
        if (water.addStoneDepth()) {
            return SurfaceRules.waterStartCheck(water.offset(), water.surfaceDepthMultiplier());
        }
        return SurfaceRules.waterBlockCheck(water.offset(), water.surfaceDepthMultiplier());
    }

    @SuppressWarnings("unchecked")
    protected SurfaceRules.ConditionSource biome(SurfaceCondition.Biome biome) {
        List<net.minecraft.resources.ResourceKey<Biome>> keys = new ArrayList<>();
        for (ResourceKey key : biome.biomes()) {
            Identifier id = (Identifier) key.resourceLocation();
            keys.add(net.minecraft.resources.ResourceKey.create(Registries.BIOME, id));
        }
        net.minecraft.resources.ResourceKey<Biome>[] array = keys.toArray(net.minecraft.resources.ResourceKey[]::new);
        return SurfaceRules.isBiome(array);
    }

    protected SurfaceRules.ConditionSource noise(SurfaceCondition.Noise noise) {
        Identifier id = (Identifier) noise.noise().resourceLocation();
        net.minecraft.resources.ResourceKey<NormalNoise.NoiseParameters> key =
            net.minecraft.resources.ResourceKey.create(Registries.NOISE, id);
        return SurfaceRules.noiseCondition(key, noise.minThreshold(), noise.maxThreshold());
    }

    private SurfaceRules.ConditionSource yCheck(SurfaceCondition.YCheck yCheck) {
        VerticalAnchor anchor = (VerticalAnchor) yCheck.anchor().toMinecraft();
        if (yCheck.addStoneDepth()) {
            return SurfaceRules.yStartCheck(anchor, yCheck.surfaceDepthMultiplier());
        }
        return SurfaceRules.yBlockCheck(anchor, yCheck.surfaceDepthMultiplier());
    }

    private SurfaceRules.ConditionSource verticalGradient(SurfaceCondition.VerticalGradient verticalGradient) {
        VerticalAnchor trueAtAndBelow = (VerticalAnchor) verticalGradient.trueAtAndBelow().toMinecraft();
        VerticalAnchor falseAtAndAbove = (VerticalAnchor) verticalGradient.falseAtAndAbove().toMinecraft();
        return SurfaceRules.verticalGradient(verticalGradient.randomName(), trueAtAndBelow, falseAtAndAbove);
    }

    private CaveSurface caveSurface(me.outspending.biomesapi.wrapper.worldgen.surface.CaveSurface surface) {
        return switch (surface) {
            case FLOOR -> CaveSurface.FLOOR;
            case CEILING -> CaveSurface.CEILING;
        };
    }
}