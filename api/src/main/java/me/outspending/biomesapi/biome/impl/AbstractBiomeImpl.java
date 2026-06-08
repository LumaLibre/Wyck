package me.outspending.biomesapi.biome.impl;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
@ApiStatus.Internal
public class AbstractBiomeImpl implements AbstractBiome {

    // Required Settings
    private final ResourceKey resourceKey;
    private BiomeSettings settings;

    // Required Colors
    private int waterColor;

    // Optional Colors
    private @Nullable Integer fogColor;
    private @Nullable Integer waterFogColor;
    private @Nullable Integer skyColor;
    private @Nullable Integer foliageColor;
    private @Nullable Integer grassColor;
    private @Nullable Integer dryFoliageColor;

    // Optional Settings
    private GrassColorModifier grassColorModifier;
    private ParticleCatalog particleCatalog;

    private WrappedEnvironmentAttributeMap attributes;

    private @Nullable BiomeSpawner biomeSpawner;
    private @Nullable BiomeGenerationSettings generationSettings;

    public AbstractBiomeImpl(
        ResourceKey resourceKey,
        BiomeSettings settings,
        int waterColor,
        @Nullable Integer fogColor,
        @Nullable Integer waterFogColor,
        @Nullable Integer skyColor,
        @Nullable Integer foliageColor,
        @Nullable Integer grassColor,
        @Nullable Integer dryFoliageColor,
        GrassColorModifier grassColorModifier,
        ParticleCatalog particleCatalog,
        WrappedEnvironmentAttributeMap attributes,
        @Nullable BiomeSpawner biomeSpawner,
        @Nullable BiomeGenerationSettings generationSettings
    ) {
        this.resourceKey = resourceKey;
        this.settings = settings;
        this.waterColor = waterColor;
        this.fogColor = fogColor;
        this.waterFogColor = waterFogColor;
        this.skyColor = skyColor;
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.dryFoliageColor = dryFoliageColor;
        this.grassColorModifier = grassColorModifier;
        this.particleCatalog = particleCatalog;
        this.attributes = attributes;
        this.biomeSpawner = biomeSpawner;
        this.generationSettings = generationSettings;
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(resourceKey.namespace(), resourceKey.path());
    }

    @Override
    public Biome toBukkitBiome() {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).getOrThrow(this.getKey());
    }

    @Override
    public ResourceKey getResourceKey() {
        return this.resourceKey;
    }

    @Override
    public BiomeSettings getSettings() {
        return this.settings;
    }

    @Override
    public @Nullable Integer getFogColor() {
        return fogColor;
    }

    @Override
    public int getWaterColor() {
        return waterColor;
    }

    @Override
    public @Nullable Integer getWaterFogColor() {
        return waterFogColor;
    }

    @Override
    public @Nullable Integer getSkyColor() {
        return skyColor;
    }

    @Override
    public @Nullable Integer getFoliageColor() {
        return foliageColor;
    }

    @Override
    public @Nullable Integer getGrassColor() {
        return grassColor;
    }

    public @Nullable Integer getDryFoliageColor() {
        return dryFoliageColor;
    }

    @Override
    public GrassColorModifier getGrassColorModifier() {
        return grassColorModifier;
    }

    @Override
    public ParticleCatalog getParticleCatalog() {
        return particleCatalog;
    }

    @Override
    public WrappedEnvironmentAttributeMap getAttributes() {
        return attributes;
    }

    @Override
    public @Nullable BiomeSpawner getBiomeSpawner() {
        return biomeSpawner;
    }

    @Override
    public @Nullable BiomeGenerationSettings getGenerationSettings() {
        return generationSettings;
    }

    @Override
    public AbstractBiomeImpl setSettings(BiomeSettings settings) {
        this.settings = settings;
        return this;
    }

    @Override
    public AbstractBiomeImpl setFogColor(@Nullable Integer fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setWaterColor(int waterColor) {
        this.waterColor = waterColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setWaterFogColor(@Nullable Integer waterFogColor) {
        this.waterFogColor = waterFogColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setSkyColor(@Nullable Integer skyColor) {
        this.skyColor = skyColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setFoliageColor(@Nullable Integer foliageColor) {
        this.foliageColor = foliageColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setGrassColor(@Nullable Integer grassColor) {
        this.grassColor = grassColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setDryFoliageColor(@Nullable Integer dryFoliageColor) {
        this.dryFoliageColor = dryFoliageColor;
        return this;
    }

    @Override
    public AbstractBiomeImpl setGrassColorModifier(GrassColorModifier grassColorModifier) {
        this.grassColorModifier = grassColorModifier;
        return this;
    }

    @Override
    public AbstractBiomeImpl setParticleCatalog(ParticleCatalog particleCatalog) {
        this.particleCatalog = particleCatalog;
        return this;
    }

    @Override
    public AbstractBiomeImpl setAttributes(WrappedEnvironmentAttributeMap attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public AbstractBiomeImpl setBiomeSpawner(@Nullable BiomeSpawner biomeSpawner) {
        this.biomeSpawner = biomeSpawner;
        return this;
    }

    @Override
    public AbstractBiomeImpl setGenerationSettings(@Nullable BiomeGenerationSettings generationSettings) {
        this.generationSettings = generationSettings;
        return this;
    }


    @Override
    public boolean isSimilar(AbstractBiome otherBiome) {
        if (this == otherBiome) return true;
        if (!this.resourceKey.equals(otherBiome.getResourceKey())) return false;
        if (!this.settings.equals(otherBiome.getSettings())) return false;
        if (!Objects.equals(this.fogColor, otherBiome.getFogColor())) return false;
        if (this.waterColor != otherBiome.getWaterColor()) return false;
        if (!Objects.equals(this.waterFogColor, otherBiome.getWaterFogColor())) return false;
        if (!Objects.equals(this.skyColor, otherBiome.getSkyColor())) return false;
        if (!Objects.equals(this.foliageColor, otherBiome.getFoliageColor())) return false;
        if (!Objects.equals(this.grassColor, otherBiome.getGrassColor())) return false;
        if (!Objects.equals(this.dryFoliageColor, otherBiome.getDryFoliageColor())) return false;
        if (!this.grassColorModifier.equals(otherBiome.getGrassColorModifier())) return false;
        if (!this.particleCatalog.equals(otherBiome.getParticleCatalog())) return false;
        return this.attributes.equals(otherBiome.getAttributes());
    }

}
