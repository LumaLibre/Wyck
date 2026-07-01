package me.outspending.biomesapi.v1_21_11.wrapper.worldgen.surface;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceCondition;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import me.outspending.biomesapi.wrapper.worldgen.surface.CaveSurface;
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

    @Override
    public SurfaceCondition fromMinecraft(Object nms) {
        SurfaceRules.ConditionSource source = (SurfaceRules.ConditionSource) nms;

        Identifier typeId = BuiltInRegistries.MATERIAL_CONDITION.getKey(source.codec().codec());
        Preconditions.checkNotNull(typeId, "unregistered surface condition type: %s", source.getClass());

        return switch (typeId.getPath()) {
            case "stone_depth" -> new SurfaceCondition.StoneDepth(
                InternalReflectUtil.<Integer>getFieldValue(source, "offset"),
                InternalReflectUtil.<Boolean>getFieldValue(source, "addSurfaceDepth"),
                InternalReflectUtil.<Integer>getFieldValue(source, "secondaryDepthRange"),
                CaveSurface.TRANSLATOR.fromNms(InternalReflectUtil.<net.minecraft.world.level.levelgen.placement.CaveSurface>getFieldValue(source, "surfaceType")));

            case "not" -> new SurfaceCondition.Not(fromMinecraft(InternalReflectUtil.getFieldValue(source, "target")));

            case "water" -> new SurfaceCondition.Water(
                InternalReflectUtil.<Integer>getFieldValue(source, "offset"),
                InternalReflectUtil.<Integer>getFieldValue(source, "surfaceDepthMultiplier"),
                InternalReflectUtil.<Boolean>getFieldValue(source, "addStoneDepth"));

            case "biome" -> new SurfaceCondition.Biome(biomeKeys(InternalReflectUtil.getFieldValue(source, "biomes")));

            case "noise_threshold" -> new SurfaceCondition.Noise(
                noiseKey(InternalReflectUtil.getFieldValue(source, "noise")),
                InternalReflectUtil.<Double>getFieldValue(source, "minThreshold"),
                InternalReflectUtil.<Double>getFieldValue(source, "maxThreshold"));

            case "y_above" -> new SurfaceCondition.YCheck(
                fromAnchor(InternalReflectUtil.getFieldValue(source, "anchor")),
                InternalReflectUtil.<Integer>getFieldValue(source, "surfaceDepthMultiplier"),
                InternalReflectUtil.<Boolean>getFieldValue(source, "addStoneDepth"));

            case "vertical_gradient" -> {
                SurfaceRules.VerticalGradientConditionSource vg = (SurfaceRules.VerticalGradientConditionSource) source;
                yield new SurfaceCondition.VerticalGradient(
                    vg.randomName().toString(),
                    fromAnchor(vg.trueAtAndBelow()),
                    fromAnchor(vg.falseAtAndAbove()));
            }

            case "steep" -> SurfaceCondition.steep();
            case "hole" -> SurfaceCondition.hole();
            case "above_preliminary_surface" -> SurfaceCondition.abovePreliminarySurface();
            case "temperature" -> SurfaceCondition.temperature();

            default -> throw new UnsupportedOperationException("no wrapper representation for surface condition type: " + typeId);
        };
    }

    private SurfaceRules.ConditionSource stoneDepth(SurfaceCondition.StoneDepth stoneDepth) {
        net.minecraft.world.level.levelgen.placement.CaveSurface surfaceType = stoneDepth.surfaceType().toNms(net.minecraft.world.level.levelgen.placement.CaveSurface.class);
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
        net.minecraft.world.level.levelgen.VerticalAnchor anchor = (net.minecraft.world.level.levelgen.VerticalAnchor) yCheck.anchor().toMinecraft();
        if (yCheck.addStoneDepth()) {
            return SurfaceRules.yStartCheck(anchor, yCheck.surfaceDepthMultiplier());
        }
        return SurfaceRules.yBlockCheck(anchor, yCheck.surfaceDepthMultiplier());
    }

    private SurfaceRules.ConditionSource verticalGradient(SurfaceCondition.VerticalGradient verticalGradient) {
        net.minecraft.world.level.levelgen.VerticalAnchor trueAtAndBelow = (net.minecraft.world.level.levelgen.VerticalAnchor) verticalGradient.trueAtAndBelow().toMinecraft();
        net.minecraft.world.level.levelgen.VerticalAnchor falseAtAndAbove = (net.minecraft.world.level.levelgen.VerticalAnchor) verticalGradient.falseAtAndAbove().toMinecraft();
        return SurfaceRules.verticalGradient(verticalGradient.randomName(), trueAtAndBelow, falseAtAndAbove);
    }

    private static VerticalAnchor fromAnchor(net.minecraft.world.level.levelgen.VerticalAnchor anchor) {
        if (anchor instanceof net.minecraft.world.level.levelgen.VerticalAnchor.Absolute(int y)) {
            return VerticalAnchor.absolute(y);
        }
        if (anchor instanceof net.minecraft.world.level.levelgen.VerticalAnchor.AboveBottom(int offset)) {
            return VerticalAnchor.aboveBottom(offset);
        }
        net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop belowTop = (net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop) anchor;
        return VerticalAnchor.belowTop(belowTop.offset());
    }

    private static List<ResourceKey> biomeKeys(HolderSet<Biome> biomes) {
        List<ResourceKey> keys = new ArrayList<>();
        for (Holder<Biome> holder : biomes) {
            Identifier id = holder.unwrapKey().orElseThrow().identifier();
            keys.add(ResourceKey.of(id.getNamespace(), id.getPath()));
        }
        return keys;
    }

    private static ResourceKey noiseKey(net.minecraft.resources.ResourceKey<NormalNoise.NoiseParameters> key) {
        Identifier id = key.identifier();
        return ResourceKey.of(id.getNamespace(), id.getPath());
    }
}