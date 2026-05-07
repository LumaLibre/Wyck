package me.outspending.biomesapi;

import com.google.common.base.Preconditions;
import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.Metrics;
import dev.faststats.core.data.Metric;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.factory.BuildInfo;
import me.outspending.biomesapi.factory.NullableWireProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @version 2.1.0
     * @author Jsinco
     */
    @AsOf("2.1.0")
    @ApiStatus.Internal
    final class ShadedBiomesAPI implements BiomesAPI {

        private static ShadedBiomesAPI INSTANCE;
        private final Metrics metrics;

        @Override
        public @NotNull Metrics metrics() {
            return metrics;
        }

        private static synchronized ShadedBiomesAPI get() {
            if (INSTANCE == null) {
                INSTANCE = new ShadedBiomesAPI();
            }
            return INSTANCE;
        }

        public ShadedBiomesAPI() {
            Preconditions.checkState(INSTANCE == null, "Already initialized");

            JavaPlugin plugin = plugin();

            this.metrics = BukkitMetrics.factory()
                    .token(BuildInfo.METRICS_TOKEN)
                    .addMetric(Metric.number("registered_biomes", () -> BiomeHandler.getRegisteredBiomes().size()))
                    .addMetric(Metric.bool("is_external", this::isExternal))
                    .create(plugin);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    metrics.shutdown();
                } catch (Throwable _) {}
            }, "BiomesAPI-Metrics-Shutdown"));
        }
    }
}