package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CaveCarverConfigurationFactoryImpl implements CaveCarverConfiguration.Factory {

    @Override
    public Object toNms(CaveCarverConfiguration configuration) {
        net.minecraft.world.level.levelgen.heightproviders.HeightProvider y = (net.minecraft.world.level.levelgen.heightproviders.HeightProvider) configuration.y().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider yScale = (net.minecraft.util.valueproviders.FloatProvider) configuration.yScale().toMinecraft();
        net.minecraft.world.level.levelgen.VerticalAnchor lavaLevel = (net.minecraft.world.level.levelgen.VerticalAnchor) configuration.lavaLevel().toMinecraft();
        net.minecraft.world.level.levelgen.carver.CarverDebugSettings debugSettings = (net.minecraft.world.level.levelgen.carver.CarverDebugSettings) configuration.debugSettings().toMinecraft();
        HolderSet<Block> replaceable = WorldgenConversions.toBlockHolderSet(configuration.replaceable());
        net.minecraft.util.valueproviders.FloatProvider horizontalRadiusMultiplier = (net.minecraft.util.valueproviders.FloatProvider) configuration.horizontalRadiusMultiplier().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider verticalRadiusMultiplier = (net.minecraft.util.valueproviders.FloatProvider) configuration.verticalRadiusMultiplier().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider floorLevel = (net.minecraft.util.valueproviders.FloatProvider) configuration.floorLevel().toMinecraft();

        return new net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration(
            configuration.probability(),
            y,
            yScale,
            lavaLevel,
            debugSettings,
            replaceable,
            horizontalRadiusMultiplier,
            verticalRadiusMultiplier,
            floorLevel
        );
    }

    @Override
    public CaveCarverConfiguration fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration nmsConfig = (net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration) nms;

        return new CaveCarverConfiguration(
            nmsConfig.probability,
            HeightProvider.fromMinecraft(nmsConfig.y),
            FloatProvider.fromMinecraft(nmsConfig.yScale),
            VerticalAnchor.fromMinecraft(nmsConfig.lavaLevel),
            CarverDebugSettings.fromMinecraft(nmsConfig.debugSettings),
            WorldgenConversions.fromBlockHolderSet(nmsConfig.replaceable),
            FloatProvider.fromMinecraft(nmsConfig.horizontalRadiusMultiplier),
            FloatProvider.fromMinecraft(nmsConfig.verticalRadiusMultiplier),
            FloatProvider.fromMinecraft(InternalReflectUtil.getFieldValue(nmsConfig, "floorLevel"))
        );
    }
}