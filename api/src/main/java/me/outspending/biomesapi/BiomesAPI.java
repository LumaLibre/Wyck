package me.outspending.biomesapi;

import com.google.common.base.Preconditions;
import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.Metrics;
import dev.faststats.core.data.Metric;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.RegisteredBiomes;
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

    /**
     * @return the active BiomesAPI instance. When running as a standalone plugin, this is the
     * Bukkit-managed plugin. When shaded into a consumer plugin, returns a default instance
     * with no plugin context.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static @NotNull BiomesAPI biomesapi() {
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
        try {
            return WIRE.optional()
                    .filter(api -> api instanceof JavaPlugin)
                    .map(api -> (JavaPlugin) api)
                    .orElse(JavaPlugin.getProvidingPlugin(BiomesAPI.class));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Called before plugin was loaded. Do not use static initializers in your main plugin class or non-lazy classes for this component.", e);
        }
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
     * Returns the metrics instance for BiomesAPI, if available.
     * @return the metrics instance, or null if metrics is not available.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    @Nullable Metrics metrics();


    /**
     * Disables metrics for BiomesAPI when shaded into a consumer plugin.
     * @since 2.2.0
     * @throws UnsupportedOperationException if BiomesAPI is running as a standalone plugin.
     * @throws IllegalStateException if metrics are already enabled.
     */
    @AsOf("2.2.0")
    default void disableMetrics() {
        if (isExternal()) {
            throw new UnsupportedOperationException("Cannot disable metrics for BiomesAPI as an external plugin.");
        }
        ShadedBiomesAPI shaded = (ShadedBiomesAPI) biomesapi();
        if (shaded.metrics == null) {
            shaded.disableMetrics = true;
        } else {
            throw new IllegalStateException("Metrics already enabled, call this method in onLoad() or onEnable().");
        }
    }

    /**
     * Default implementation of BiomesAPI when shaded into a consumer plugin.
     * Constructed reflectively by the wire as a fallback when no standalone plugin
     * has registered itself.
     *
     * @since 2.1.0
     * @version 2.2.0
     * @author Jsinco
     */
    @AsOf("2.2.0")
    @ApiStatus.Internal
    final class ShadedBiomesAPI implements BiomesAPI {

        private static final long ENABLE_WAIT_TIMEOUT_SECONDS = 10;

        private static ShadedBiomesAPI INSTANCE;
        private volatile @Nullable Metrics metrics;
        private volatile boolean disableMetrics;

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
            if (!disableMetrics || isExternal()) { // Nothing can reach this early enough, but oh well
                registerWhenReady();
            }
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
                if (disableMetrics || isExternal()) {
                    executor.shutdown();
                    return;
                }

                JavaPlugin resolved = tryResolvePlugin();
                if (resolved != null && resolved.isEnabled()) {
                    Bukkit.getGlobalRegionScheduler().run(resolved, _ -> {
                        if (disableMetrics || isExternal()) return;
                        setupMetrics(resolved);
                    });
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
                    .addMetric(Metric.number("registered_biomes", RegisteredBiomes::size))
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