package dev.wyck.decode.worldgen.carver;

import dev.wyck.worldgen.carver.CanyonWorldCarver;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.CaveWorldCarver;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import dev.wyck.decode.Decoders;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CarverConfigurationDecoders extends DecoderRegistry<CarverConfiguration, net.minecraft.world.level.levelgen.carver.WorldCarver> {

    public CarverConfigurationDecoders() {
        register("cave", this::cave);
        register("canyon", this::canyon);
    }

    @Override
    protected dev.wyck.keys.ResourceKey discriminate(net.minecraft.world.level.levelgen.carver.WorldCarver minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.CARVER_TYPE, minecraftObject.codec());
    }

    private CarverConfiguration cave(net.minecraft.world.level.levelgen.carver.WorldCarver minecraftObject) {
        net.minecraft.world.level.levelgen.carver.CaveWorldCarver config =
            (net.minecraft.world.level.levelgen.carver.CaveWorldCarver) minecraftObject;
        return CaveWorldCarver.of(
            config.probability(),
            HeightProvider.decode(config.y()),
            IntProvider.decode(config.count()),
            FloatProvider.decode(config.thickness()),
            config.weirdThicknessBias(),
            FloatProvider.decode(config.roomVerticalRadiusMultiplier()),
            FloatProvider.decode(config.horizontalRadiusMultiplier()),
            FloatProvider.decode(config.verticalRadiusMultiplier()),
            FloatProvider.decode(config.startVerticalRadiusMultiplier()),
            FloatProvider.decode(config.floorLevel())
        );
    }

    private CarverConfiguration canyon(net.minecraft.world.level.levelgen.carver.WorldCarver minecraftObject) {
        net.minecraft.world.level.levelgen.carver.CanyonWorldCarver config =
            (net.minecraft.world.level.levelgen.carver.CanyonWorldCarver) minecraftObject;
        return CanyonWorldCarver.of(
            config.probability(),
            HeightProvider.decode(config.y()),
            FloatProvider.decode(config.verticalRotation()),
            new CanyonShapeDecoder().decode(config.shape())
        );
    }
}
