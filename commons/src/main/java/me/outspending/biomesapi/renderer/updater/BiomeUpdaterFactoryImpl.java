package me.outspending.biomesapi.renderer.updater;

import me.outspending.biomesapi.annotations.WireFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class BiomeUpdaterFactoryImpl implements BiomeUpdater.Factory {
    @Override
    public BiomeUpdater create(Plugin plugin) {
        return new BiomeUpdaterImpl(plugin);
    }
}
