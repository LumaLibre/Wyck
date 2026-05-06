package me.outspending.biomesapi.paper;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import me.outspending.biomesapi.BiomesAPI;
import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

@AsOf("2.1.0")
@ApiStatus.Internal
public final class BiomesAPIPluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {

    }

    @Override
    public JavaPlugin createPlugin(PluginProviderContext context) {
        BiomesAPIPlugin plugin = new BiomesAPIPlugin();
        BiomesAPI.WIRE.setProvider(BiomesAPIPluginBootstrap.class, plugin);
        context.getLogger().info("Wired BiomesAPI WireProvider to {}", plugin.getClass().getName());
        return plugin;
    }
}