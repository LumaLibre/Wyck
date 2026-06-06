package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions.toBlockHolderSet;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CaveCarverConfigurationFactoryImpl implements CaveCarverConfiguration.Factory {

    @Override
    public Object toNms(CaveCarverConfiguration configuration) {
        HeightProvider y = (HeightProvider) configuration.y().toMinecraft();
        FloatProvider yScale = (FloatProvider) configuration.yScale().toMinecraft();
        VerticalAnchor lavaLevel = (VerticalAnchor) configuration.lavaLevel().toMinecraft();
        CarverDebugSettings debugSettings = (CarverDebugSettings) configuration.debugSettings().toMinecraft();
        HolderSet<Block> replaceable = toBlockHolderSet(configuration.replaceable());
        FloatProvider horizontalRadiusMultiplier = (FloatProvider) configuration.horizontalRadiusMultiplier().toMinecraft();
        FloatProvider verticalRadiusMultiplier = (FloatProvider) configuration.verticalRadiusMultiplier().toMinecraft();
        FloatProvider floorLevel = (FloatProvider) configuration.floorLevel().toMinecraft();

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

}