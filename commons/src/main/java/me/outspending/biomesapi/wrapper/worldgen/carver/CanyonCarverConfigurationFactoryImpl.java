package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CanyonCarverConfigurationFactoryImpl implements CanyonCarverConfiguration.Factory {

    @Override
    public @NotNull Object toNms(@NotNull CanyonCarverConfiguration configuration) {
        HeightProvider y = (HeightProvider) configuration.y().toMinecraft();
        FloatProvider yScale = (FloatProvider) configuration.yScale().toMinecraft();
        VerticalAnchor lavaLevel = (VerticalAnchor) configuration.lavaLevel().toMinecraft();
        CarverDebugSettings debugSettings = (CarverDebugSettings) configuration.debugSettings().toMinecraft();
        HolderSet<@NotNull Block> replaceable = WorldgenConversions.toBlockHolderSet(configuration.replaceable());
        FloatProvider verticalRotation = (FloatProvider) configuration.verticalRotation().toMinecraft();
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
}