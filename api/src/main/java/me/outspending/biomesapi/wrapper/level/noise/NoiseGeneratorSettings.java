package me.outspending.biomesapi.wrapper.level.noise;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.worldgen.LevelNoiseGeneratorSettingsRegistry;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.noise.settings.NoiseSettings;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRule;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Wraps {@code NoiseGeneratorSettings}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface NoiseGeneratorSettings extends NmsHandle, Noise {

    Codec<NoiseGeneratorSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceKey.CODEC.optionalFieldOf("resource_key").forGetter(s -> Optional.ofNullable(s.data().resourceKey())),
        NoiseSettings.CODEC.fieldOf("noise_settings").forGetter(s -> s.data().noiseSettings()),
        Codecs.MATERIAL_CODEC.fieldOf("default_block").forGetter(s -> s.data().defaultBlock()),
        Codecs.MATERIAL_CODEC.fieldOf("default_fluid").forGetter(s -> s.data().defaultFluid()),
        NoiseRouter.CODEC.fieldOf("noise_router").forGetter(s -> s.data().noiseRouter()),
        SurfaceRule.CODEC.fieldOf("surface_rule").forGetter(s -> s.data().surfaceRule()),
        Codec.list(ClimatePoint.CODEC).optionalFieldOf("spawn_target", List.of()).forGetter(s -> s.data().spawnTarget()),
        Codec.INT.optionalFieldOf("sea_level", 63).forGetter(s -> s.data().seaLevel()),
        Codec.BOOL.optionalFieldOf("disable_mob_generation", false).forGetter(s -> s.data().disableMobGeneration()),
        Codec.BOOL.optionalFieldOf("aquifers_enabled", true).forGetter(s -> s.data().aquifersEnabled()),
        Codec.BOOL.optionalFieldOf("ore_veins_enabled", true).forGetter(s -> s.data().oreVeinsEnabled()),
        Codec.BOOL.optionalFieldOf("use_legacy_random_source", false).forGetter(s -> s.data().useLegacyRandomSource())
    ).apply(instance, NoiseGeneratorSettings::of));


    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.NoiseGeneratorSettingsFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<NoiseGeneratorSettings> {
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
        LevelNoiseGeneratorSettingsRegistry.registry().register(key, this);
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

    @AsOf("2.4.0")
    static NoiseGeneratorSettings fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @AsOf("2.4.0")
    static NoiseGeneratorSettings of(Data data) {
        return WIRE.get().create(data);
    }

    @AsOf("2.4.0")
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static NoiseGeneratorSettings of(Optional<ResourceKey> resourceKey, NoiseSettings noiseSettings, Material defaultBlock, Material defaultFluid, NoiseRouter noiseRouter, SurfaceRule surfaceRule, List<ClimatePoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {
        Data data = new Data(
            resourceKey.orElse(null),
            noiseSettings,
            defaultBlock,
            defaultFluid,
            noiseRouter,
            surfaceRule,
            spawnTarget,
            seaLevel,
            disableMobGeneration,
            aquifersEnabled,
            oreVeinsEnabled,
            useLegacyRandomSource
        );

        return of(data);
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