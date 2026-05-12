package me.outspending.biomesapi;

import com.google.common.base.Preconditions;
import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.Metrics;
import dev.faststats.core.data.Metric;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.factory.BuildInfo;
import me.outspending.biomesapi.factory.NullableWireProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Common interface for miscellaneous BiomesAPI methods and utilities.
 * Implemented by the main plugin class when running as a standalone plugin, and by a default
 * implementation when shaded into a consumer plugin.
 *
 * @since 2.1.0
 * @version 2.1.0
 * @author Jsinco
 */
@AsOf("2.1.0")
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface BiomesAPI {

    @ApiStatus.Internal
    NullableWireProvider<BiomesAPI> WIRE = NullableWireProvider.empty();
    Logger LOGGER = Logger.getLogger("BiomesAPI");

    /**
     * @return the active BiomesAPI instance. When running as a standalone plugin, this is the
     * Bukkit-managed plugin. When shaded into a consumer plugin, returns a default instance
     * with no plugin context.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static @NotNull BiomesAPI biomesapi() {
        LOGGER.info("BiomesAPI version " + BuildInfo.VERSION + " is active.");
        return WIRE.optional().orElseGet(ShadedBiomesAPI::get);
    }

    /**
     * Determines the plugin instance associated with BiomesAPI.
     * @return the plugin instance if the library is running as a standalone plugin or {@link JavaPlugin#getProvidingPlugin(Class)} otherwise.
     * @since 2.1.0
     * @throws IllegalStateException if called from the static initializer for the given JavaPlugin
     */
    @AsOf("2.1.0")
    default @NotNull JavaPlugin plugin() {
        return WIRE.optional()
                .filter(api -> api instanceof JavaPlugin)
                .map(api -> (JavaPlugin) api)
                .orElse(JavaPlugin.getProvidingPlugin(BiomesAPI.class));
    }

    /**
     * @return the version of BiomesAPI
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default @NotNull String version() {
        return BuildInfo.VERSION;
    }

    /**
     * @return true if the library is shaded into a consumer plugin (no standalone instance available).
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default boolean isShaded() {
        return WIRE.optional().map(api -> api instanceof ShadedBiomesAPI).orElse(true);
    }

    /**
     * @return true if the library is running as a standalone plugin.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default boolean isExternal() {
        return !isShaded();
    }

    /**
     * Returns the metrics instance for the plugin.
     * @return the metrics instance for the plugin
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    @Nullable Metrics metrics();

    /**
     * Default implementation of BiomesAPI when shaded into a consumer plugin.
     * Constructed reflectively by the wire as a fallback when no standalone plugin
     * has registered itself.
     *
     * @since 2.1.0
     * @version 2.1.2
     * @author Jsinco
     */
    @AsOf("2.1.2")
    @ApiStatus.Internal
    final class ShadedBiomesAPI implements BiomesAPI {

        private static final long ENABLE_WAIT_TIMEOUT_SECONDS = 10;

        private static ShadedBiomesAPI INSTANCE;
        private volatile @Nullable Metrics metrics;

        private static synchronized ShadedBiomesAPI get() {
            if (INSTANCE == null) {
                INSTANCE = new ShadedBiomesAPI();
            }
            return INSTANCE;
        }

        @Override
        public @Nullable Metrics metrics() {
            return metrics;
        }

        private ShadedBiomesAPI() {
            Preconditions.checkState(INSTANCE == null, "Already initialized");
            registerWhenReady();
        }

        private void registerWhenReady() {
            JavaPlugin plugin = tryResolvePlugin();
            if (plugin != null && plugin.isEnabled()) {
                setupMetrics(plugin);
                return;
            }

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "BiomesAPI-Metrics");
                t.setDaemon(true);
                return t;
            });

            long startMillis = System.currentTimeMillis();
            long timeoutMillis = TimeUnit.SECONDS.toMillis(ENABLE_WAIT_TIMEOUT_SECONDS);

            executor.scheduleAtFixedRate(() -> {
                JavaPlugin resolved = tryResolvePlugin();
                if (resolved != null && resolved.isEnabled()) {
                    Bukkit.getGlobalRegionScheduler().run(resolved, _ -> setupMetrics(resolved));
                    executor.shutdown();
                    return;
                }
                if (System.currentTimeMillis() - startMillis > timeoutMillis) {
                    executor.shutdown();
                }
            }, 100, 100, TimeUnit.MILLISECONDS);
        }


        private @Nullable JavaPlugin tryResolvePlugin() {
            try {
                return plugin();
            } catch (IllegalStateException _) {
                return null;
            }
        }

        private void setupMetrics(JavaPlugin plugin) {
            Metrics built = BukkitMetrics.factory()
                    .token(BuildInfo.METRICS_TOKEN)
                    .addMetric(Metric.number("registered_biomes", () -> BiomeHandler.getRegisteredBiomes().size()))
                    .addMetric(Metric.bool("is_external", this::isExternal))
                    .addMetric(Metric.string("plugin_name", plugin::getName))
                    .create(plugin);

            this.metrics = built;

            Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onDisable(PluginDisableEvent event) {
                    if (event.getPlugin().equals(plugin)) {
                        built.shutdown();
                    }
                }
            }, plugin);
        }
    }
}