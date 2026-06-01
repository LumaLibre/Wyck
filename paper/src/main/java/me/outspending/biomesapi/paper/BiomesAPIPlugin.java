package me.outspending.biomesapi.paper;

import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.ErrorTracker;
import dev.faststats.core.Metrics;
import dev.faststats.core.data.Metric;
import me.outspending.biomesapi.BiomesAPI;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.factory.BuildInfo;
import me.outspending.biomesapi.paper.configs.BiomesAPIPluginConfig;
import me.outspending.biomesapi.paper.renderer.packet.PacketHandlerFactoryPluginImpl;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

@WireFactory
@AsOf("2.1.0")
@ApiStatus.Internal
public final class BiomesAPIPlugin extends JavaPlugin implements BiomesAPI {

    public static boolean STOPPING = false;
    private static final String CONFIG_FILE = "settings.yml";
    private static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();

    private final PacketHandlerFactoryPluginImpl handlerFactory;
    private final BiomesAPIPluginConfig config;
    private Metrics metrics;

    public BiomesAPIPlugin() {
        BiomesAPIPluginConfig loaded;
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(getDataPath().resolve(CONFIG_FILE))
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .build();

            CommentedConfigurationNode root = loader.load();
            loaded = root.get(BiomesAPIPluginConfig.class, new BiomesAPIPluginConfig());

            root.set(BiomesAPIPluginConfig.class, loaded);
            loader.save(root);
        } catch (Throwable e) {
            getComponentLogger().error("Failed to load config", e);
            loaded = new BiomesAPIPluginConfig();
        }

        config = loaded;
        handlerFactory = new PacketHandlerFactoryPluginImpl(this);
        PacketHandler.WIRE.setProvider(BiomesAPIPlugin.class, handlerFactory);
        getComponentLogger().info("Wired BiomesAPI PacketHandler to {}", handlerFactory.getClass().getName());
    }

    @Override
    public void onEnable() {
        handlerFactory.enableDeferredHandlers();

        if (config.metrics()) {
            metrics = BukkitMetrics.factory()
                    .token(BuildInfo.METRICS_TOKEN)
                    .errorTracker(ERROR_TRACKER)
                    .addMetric(Metric.string("forced_injector", () -> config.forcedInjector().toString()))
                    .addMetric(Metric.number("registered_biomes", () -> BiomeHandler.getRegisteredBiomes().size()))
                    .addMetric(Metric.bool("is_external", this::isExternal))
                    .addMetric(Metric.string("plugin_name", () -> "BiomesAPI (Standalone)"))
                    .create(this);

            metrics.ready();
            getComponentLogger().info("Metrics enabled");
        }

        getComponentLogger().info("BiomesAPI v{} running on Minecraft {}", version(), Bukkit.getMinecraftVersion());
    }

    @Override
    public void onDisable() {
        STOPPING = true;
        for (PacketHandler packetHandler : handlerFactory.getHandlers()) {
            packetHandler.unregister();
        }
        if (metrics != null) {
            metrics.shutdown();
            getComponentLogger().info("Metrics disabled");
        }
    }

    public BiomesAPIPluginConfig getPluginConfig() {
        return config;
    }

    @Override
    public @Nullable Metrics metrics() {
        return metrics;
    }

    @Override
    public @NotNull JavaPlugin plugin() {
        return this;
    }
}
