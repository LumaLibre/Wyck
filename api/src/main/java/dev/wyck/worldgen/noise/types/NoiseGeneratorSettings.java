package dev.wyck.worldgen.noise.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.noise.AquiferSettings;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.worldgen.noise.NoiseSettings;
import dev.wyck.worldgen.noise.SpawnTargetPoint;
import dev.wyck.worldgen.surface.SurfaceRule;
import dev.wyck.wrapper.Registerable;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The settings used by a noise-based chunk generator.
 *
 * @since 2.4.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseGeneratorSettings extends Noise, Registerable<NoiseGeneratorSettings> {

    /**
     * The vertical bounds used by the noise generator.
     * @return the noise settings
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    NoiseSettings noiseSettings();

    /**
     * The default block used for solid terrain.
     * @return the default terrain block
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    BlockData defaultBlock();

    /**
     * The default fluid used below the generated fluid level.
     * @return the default fluid block data
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    BlockData defaultFluid();

    /**
     * The density functions routed into terrain generation.
     * @return the noise router
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    NoiseRouter noiseRouter();

    /**
     * The surface rule applied after terrain density is generated.
     * @return the surface rule
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    SurfaceRule surfaceRule();

    /**
     * The climate targets used to select suitable player spawn locations.
     * @return an immutable list of spawn target points
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    List<SpawnTargetPoint> spawnTarget();

    /**
     * The default sea level.
     * @return the sea level
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int seaLevel();

    /**
     * Whether mob generation is disabled for this generator.
     * @return whether mob generation is disabled
     * @deprecated retained for compatibility with Minecraft's deprecated setting
     * @since 2.4.0
     */
    @Deprecated
    @AsOf("2.4.0")
    boolean disableMobGeneration();

    /**
     * The optional aquifer density-function settings.
     * @return the aquifer settings, if aquifers are enabled
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Optional<AquiferSettings> aquifers();

    /**
     * Whether terrain generation uses Minecraft's legacy random source.
     * @return whether the legacy random source is used
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean useLegacyRandomSource();

    /**
     * Converts these generator settings back to a builder.
     * @return a builder containing these settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates noise generator settings.
     * @param resourceKey the registry key, or null for an unregistered value
     * @param noiseSettings the vertical noise bounds
     * @param defaultBlock the default solid terrain block
     * @param defaultFluid the default fluid block data
     * @param noiseRouter the terrain density-function routes
     * @param surfaceRule the terrain surface rule
     * @param spawnTarget the player-spawn climate targets
     * @param seaLevel the default sea level
     * @param disableMobGeneration whether mob generation is disabled
     * @param aquifers the aquifer settings, or null to disable aquifers
     * @param useLegacyRandomSource whether to use Minecraft's legacy random source
     * @return new noise generator settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static NoiseGeneratorSettings of(
        @Nullable ResourceKey resourceKey,
        NoiseSettings noiseSettings,
        BlockData defaultBlock,
        BlockData defaultFluid,
        NoiseRouter noiseRouter,
        SurfaceRule surfaceRule,
        List<SpawnTargetPoint> spawnTarget,
        int seaLevel,
        boolean disableMobGeneration,
        @Nullable AquiferSettings aquifers,
        boolean useLegacyRandomSource
    ) {
        record Holder() {
            static final ConstructWireProvider<NoiseGeneratorSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.types.NoiseGeneratorSettingsImpl");
        }
        return Holder.WIRE.construct(
            Optional.ofNullable(resourceKey),
            noiseSettings,
            defaultBlock,
            defaultFluid,
            noiseRouter,
            surfaceRule,
            List.copyOf(spawnTarget),
            seaLevel,
            disableMobGeneration,
            Optional.ofNullable(aquifers),
            useLegacyRandomSource
        );
    }

    /**
     * Creates a new noise generator settings builder.
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NoiseGeneratorSettings}.
     *
     * @since 2.4.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private NoiseSettings noiseSettings = NoiseSettings.OVERWORLD;
        private BlockData defaultBlock = BukkitBootstrapUtil.util().createBlockData(Material.STONE);
        private BlockData defaultFluid = BukkitBootstrapUtil.util().createBlockData(Material.WATER);
        private @Nullable NoiseRouter noiseRouter;
        private @Nullable SurfaceRule surfaceRule;
        private List<SpawnTargetPoint> spawnTarget = new ArrayList<>();
        private int seaLevel = 63;
        private boolean disableMobGeneration;
        private @Nullable AquiferSettings aquifers;
        private boolean useLegacyRandomSource;

        public Builder() {}

        /**
         * Creates a builder containing the values of existing generator settings.
         * @param settings the generator settings to copy
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder(NoiseGeneratorSettings settings) {
            this.resourceKey = settings.resourceKey().orElse(null);
            this.noiseSettings = settings.noiseSettings();
            this.defaultBlock = settings.defaultBlock();
            this.defaultFluid = settings.defaultFluid();
            this.noiseRouter = settings.noiseRouter();
            this.surfaceRule = settings.surfaceRule();
            this.spawnTarget = new ArrayList<>(settings.spawnTarget());
            this.seaLevel = settings.seaLevel();
            this.disableMobGeneration = settings.disableMobGeneration();
            this.aquifers = settings.aquifers().orElse(null);
            this.useLegacyRandomSource = settings.useLegacyRandomSource();
        }

        /**

         * Sets the registry key.
         * @param resourceKey the registry key, or null for an unregistered value
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder resourceKey(@Nullable ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**

         * Sets the vertical noise bounds.
         * @param noiseSettings the noise settings
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder noiseSettings(NoiseSettings noiseSettings) {
            this.noiseSettings = noiseSettings;
            return this;
        }

        /**

         * Sets the default solid terrain block.
         * @param defaultBlock the default terrain block
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultBlock(BlockData defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        /**

         * Sets the default solid terrain material.
         * @param defaultBlock the default terrain material
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultBlock(Material defaultBlock) {
            this.defaultBlock = BukkitBootstrapUtil.util().createBlockData(defaultBlock);
            return this;
        }

        /**

         * Sets the default fluid block data.
         * @param defaultFluid the default fluid block data
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultFluid(BlockData defaultFluid) {
            this.defaultFluid = defaultFluid;
            return this;
        }

        /**

         * Sets the default fluid material.
         * @param defaultFluid the default fluid material
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultFluid(Material defaultFluid) {
            this.defaultFluid = BukkitBootstrapUtil.util().createBlockData(defaultFluid);
            return this;
        }

        /**

         * Sets the terrain density-function routes.
         * @param noiseRouter the noise router
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder noiseRouter(NoiseRouter noiseRouter) {
            this.noiseRouter = noiseRouter;
            return this;
        }

        /**

         * Sets the terrain surface rule.
         * @param surfaceRule the surface rule
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder surfaceRule(SurfaceRule surfaceRule) {
            this.surfaceRule = surfaceRule;
            return this;
        }

        /**

         * Replaces the player-spawn target list.
         * @param spawnTarget the player-spawn target points
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder spawnTarget(List<SpawnTargetPoint> spawnTarget) {
            this.spawnTarget = new ArrayList<>(spawnTarget);
            return this;
        }

        /**

         * Adds player-spawn target points.
         * @param spawnTarget the player-spawn target points to add
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder spawnTarget(SpawnTargetPoint... spawnTarget) {
            this.spawnTarget.addAll(Arrays.asList(spawnTarget));
            return this;
        }

        /**

         * Sets the default sea level.
         * @param seaLevel the sea level
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder seaLevel(int seaLevel) {
            this.seaLevel = seaLevel;
            return this;
        }

        /**

         * Sets whether mob generation is disabled.
         * @param disableMobGeneration whether mob generation is disabled
         * @return this builder
         * @deprecated retained for compatibility with Minecraft's deprecated setting
         * @since 2.4.0
         */
        @Deprecated
        @AsOf("2.4.0")
        public Builder disableMobGeneration(boolean disableMobGeneration) {
            this.disableMobGeneration = disableMobGeneration;
            return this;
        }

        /**

         * Sets the aquifer settings.
         * @param aquifers the aquifer settings, or null to disable aquifers
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder aquifers(@Nullable AquiferSettings aquifers) {
            this.aquifers = aquifers;
            return this;
        }

        /**

         * Sets whether Minecraft's legacy random source is used.
         * @param useLegacyRandomSource whether the legacy random source is used
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder useLegacyRandomSource(boolean useLegacyRandomSource) {
            this.useLegacyRandomSource = useLegacyRandomSource;
            return this;
        }

        /**
         * Builds the noise generator settings.
         * @return the noise generator settings
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseGeneratorSettings build() {
            Preconditions.checkNotNull(noiseRouter, "noiseRouter must be set");
            Preconditions.checkNotNull(surfaceRule, "surfaceRule must be set");
            return NoiseGeneratorSettings.of(
                resourceKey,
                noiseSettings,
                defaultBlock,
                defaultFluid,
                noiseRouter,
                surfaceRule,
                spawnTarget,
                seaLevel,
                disableMobGeneration,
                aquifers,
                useLegacyRandomSource
            );
        }

        /**
         * Builds and registers the noise generator settings.
         * @return the registered noise generator settings
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseGeneratorSettings register() {
            NoiseGeneratorSettings settings = build();
            settings.register();
            return settings;
        }
    }
}
