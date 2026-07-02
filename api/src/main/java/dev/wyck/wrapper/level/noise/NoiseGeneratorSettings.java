package dev.wyck.wrapper.level.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.worldgen.NoiseGeneratorSettingsRegistry;
import dev.wyck.wrapper.internal.NmsHandle;
import dev.wyck.wrapper.level.noise.settings.NoiseSettings;
import dev.wyck.wrapper.worldgen.climate.ClimatePoint;
import dev.wyck.wrapper.worldgen.surface.SurfaceRule;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Wraps {@code NoiseGeneratorSettings}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseGeneratorSettings extends NmsHandle, Noise {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.noise.NoiseGeneratorSettingsFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        NoiseGeneratorSettings create(Data data);
    }

    /**
     * The data backing these settings.
     *
     * @return the data
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Data data();

    /**
     * The key of this noise generator setting.
     * @return the key of this noise generator setting, if present
     * @since 2.4.0
     */
    @Override
    default @Nullable ResourceKey key() {
        return this.data().resourceKey();
    }

    /**
     * Registers this noise generator setting.
     * @return this noise generator setting
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default NoiseGeneratorSettings register() {
        ResourceKey key = this.data().resourceKey();
        Preconditions.checkState(key != null, "resourceKey must set in order to be registered");
        NoiseGeneratorSettingsRegistry.registry().register(key, this);
        return this;
    }

    /**
     * Creates a new builder for noise generator settings.
     *
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    record Data(
        @Nullable ResourceKey resourceKey,
        NoiseSettings noiseSettings,
        Material defaultBlock,
        Material defaultFluid,
        NoiseRouter noiseRouter,
        SurfaceRule surfaceRule,
        List<ClimatePoint> spawnTarget,
        int seaLevel,
        @Deprecated boolean disableMobGeneration,
        boolean aquifersEnabled,
        boolean oreVeinsEnabled,
        boolean useLegacyRandomSource
    ) {}

    /**
     * Builder for noise generator settings.
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    final class Builder {

        private @Nullable ResourceKey resourceKey = null;
        private NoiseSettings noiseSettings = NoiseSettings.OVERWORLD;
        private Material defaultBlock = Material.STONE;
        private Material defaultFluid = Material.WATER;
        private @Nullable NoiseRouter noiseRouter = null;
        private @Nullable SurfaceRule surfaceRule = null;
        private List<ClimatePoint> spawnTarget = List.of();
        private int seaLevel = 63;
        private @Deprecated boolean disableMobGeneration = false;
        private boolean aquifersEnabled = true;
        private boolean oreVeinsEnabled = true;
        private boolean useLegacyRandomSource = false;


        @AsOf("2.4.0")
        public Builder resourceKey(@Nullable ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        @AsOf("2.4.0")
        public Builder noiseSettings(NoiseSettings noiseSettings) {
            this.noiseSettings = noiseSettings;
            return this;
        }

        @AsOf("2.4.0")
        public Builder defaultBlock(Material defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        @AsOf("2.4.0")
        public Builder defaultFluid(Material defaultFluid) {
            this.defaultFluid = defaultFluid;
            return this;
        }

        @AsOf("2.4.0")
        public Builder noiseRouter(NoiseRouter noiseRouter) {
            this.noiseRouter = noiseRouter;
            return this;
        }

        @AsOf("2.4.0")
        public Builder surfaceRule(SurfaceRule surfaceRule) {
            this.surfaceRule = surfaceRule;
            return this;
        }

        @AsOf("2.4.0")
        public Builder spawnTarget(List<ClimatePoint> spawnTarget) {
            this.spawnTarget = List.copyOf(spawnTarget);
            return this;
        }

        @AsOf("2.4.0")
        public Builder seaLevel(int seaLevel) {
            this.seaLevel = seaLevel;
            return this;
        }

        /**
         * @param disableMobGeneration whether to disable mob generation
         * @return this builder, for chaining
         * @since 2.4.0
         * @deprecated deprecated as it in Minecraft.
         */
        @Deprecated
        @AsOf("2.4.0")
        public Builder disableMobGeneration(boolean disableMobGeneration) {
            this.disableMobGeneration = disableMobGeneration;
            return this;
        }

        @AsOf("2.4.0")
        public Builder aquifersEnabled(boolean aquifersEnabled) {
            this.aquifersEnabled = aquifersEnabled;
            return this;
        }

        @AsOf("2.4.0")
        public Builder oreVeinsEnabled(boolean oreVeinsEnabled) {
            this.oreVeinsEnabled = oreVeinsEnabled;
            return this;
        }

        @AsOf("2.4.0")
        public Builder useLegacyRandomSource(boolean useLegacyRandomSource) {
            this.useLegacyRandomSource = useLegacyRandomSource;
            return this;
        }

        @AsOf("2.4.0")
        public NoiseGeneratorSettings build() {
            Preconditions.checkArgument(this.noiseRouter != null, "noiseRouter must be set");
            Preconditions.checkArgument(this.surfaceRule != null, "surfaceRule must be set");
            Data data = new Data(
                this.resourceKey,
                this.noiseSettings,
                this.defaultBlock,
                this.defaultFluid,
                this.noiseRouter,
                this.surfaceRule,
                this.spawnTarget,
                this.seaLevel,
                this.disableMobGeneration,
                this.aquifersEnabled,
                this.oreVeinsEnabled,
                this.useLegacyRandomSource
            );

            return WIRE.get().create(data);
        }

        @AsOf("2.4.0")
        public NoiseGeneratorSettings register() {
            return this.build().register();
        }
    }
}