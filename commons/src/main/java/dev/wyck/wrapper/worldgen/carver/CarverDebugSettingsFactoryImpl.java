package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CarverDebugSettingsFactoryImpl implements CarverDebugSettings.Factory {

    @Override
    public Object toNms(CarverDebugSettings settings) {
        BlockState air = toNmsState(settings.airState());
        BlockState water = toNmsState(settings.waterState());
        BlockState lava = toNmsState(settings.lavaState());
        BlockState barrier = toNmsState(settings.barrierState());
        return net.minecraft.world.level.levelgen.carver.CarverDebugSettings.of(
            settings.debugMode(), air, water, lava, barrier
        );
    }

    private BlockState toNmsState(Material material) {
        return CraftMagicNumbers.getBlock(material).defaultBlockState();
    }
}