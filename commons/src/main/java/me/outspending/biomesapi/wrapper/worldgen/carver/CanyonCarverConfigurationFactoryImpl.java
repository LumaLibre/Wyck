package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CanyonCarverConfigurationFactoryImpl implements CanyonCarverConfiguration.Factory {

    @Override
    public Object toNms(CanyonCarverConfiguration configuration) {
        net.minecraft.world.level.levelgen.heightproviders.HeightProvider y = (net.minecraft.world.level.levelgen.heightproviders.HeightProvider) configuration.y().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider yScale = (net.minecraft.util.valueproviders.FloatProvider) configuration.yScale().toMinecraft();
        net.minecraft.world.level.levelgen.VerticalAnchor lavaLevel = (net.minecraft.world.level.levelgen.VerticalAnchor) configuration.lavaLevel().toMinecraft();
        net.minecraft.world.level.levelgen.carver.CarverDebugSettings debugSettings = (net.minecraft.world.level.levelgen.carver.CarverDebugSettings) configuration.debugSettings().toMinecraft();
        HolderSet<Block> replaceable = WorldgenConversions.toBlockHolderSet(configuration.replaceable());
        net.minecraft.util.valueproviders.FloatProvider verticalRotation = (net.minecraft.util.valueproviders.FloatProvider) configuration.verticalRotation().toMinecraft();
        net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration shape =
                (net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration) configuration.shape().toMinecraft();

        return new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration(
            configuration.probability(),
            y,
            yScale,
            lavaLevel,
            debugSettings,
            replaceable,
            verticalRotation,
            shape
        );
    }

    @Override
    public CanyonCarverConfiguration fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration nmsConfig = (net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration) nms;


        return new CanyonCarverConfiguration(
            nmsConfig.probability,
            HeightProvider.fromMinecraft(nmsConfig.y),
            FloatProvider.fromMinecraft(nmsConfig.yScale),
            VerticalAnchor.fromMinecraft(nmsConfig.lavaLevel),
            CarverDebugSettings.fromMinecraft(nmsConfig.debugSettings),
            WorldgenConversions.fromBlockHolderSet(nmsConfig.replaceable),
            FloatProvider.fromMinecraft(nmsConfig.verticalRotation),
            CanyonShapeConfiguration.fromMinecraft(nmsConfig.shape)
        );
    }
}