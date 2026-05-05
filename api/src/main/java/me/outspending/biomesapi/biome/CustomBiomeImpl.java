package me.outspending.biomesapi.biome;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.GrassColorModifier;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * This class represents a custom biome implementation.
 *
 * @author Outspending
 * @version 2.1.0
 * @since 0.0.2
 */
@ApiStatus.Internal
@AsOf("2.1.0")
public final class CustomBiomeImpl implements CustomBiome {

    // Required Settings
    private final BiomeResourceKey resourceKey;
    private final BiomeSettings settings;

    // Required Colors
    private int waterColor;

    // Optional Colors
    private Integer fogColor;
    private Integer waterFogColor;
    private Integer skyColor;
    private Integer foliageColor;
    private Integer grassColor;
    private Integer dryFoliageColor;

    // Optional Settings
    private GrassColorModifier grassColorModifier;
    private ParticleCatalog particleCatalog;
    private BlockReplacement[] blockReplacements;

    private WrappedEnvironmentAttributeMap environmentAttributeMap;


    @AsOf("2.1.0")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,
            int waterColor,
            @Nullable Integer fogColor,
            @Nullable Integer waterFogColor,
            @Nullable Integer skyColor,
            @Nullable Integer foliageColor,
            @Nullable Integer grassColor,
            @Nullable Integer dryFoliageColor,
            @NotNull GrassColorModifier grassColorModifier,
            @NotNull ParticleCatalog particleCatalog,
            @NotNull BlockReplacement[] blockReplacements,
            @NotNull WrappedEnvironmentAttributeMap environmentAttributeMap
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
        this.blockReplacements = blockReplacements;
        this.environmentAttributeMap = environmentAttributeMap;
    }

    @Override
    public @NotNull NamespacedKey toNamespacedKey() {
        return new NamespacedKey(resourceKey.namespace(), resourceKey.path());
    }


    public @NotNull Biome toBukkitBiome() {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).getOrThrow(this.toNamespacedKey());
    }

    @Override
    public @NotNull BiomeResourceKey getResourceKey() {
        return this.resourceKey;
    }

    @Override
    public @NotNull BiomeSettings getSettings() {
        return this.settings;
    }

    @Override
    public Integer getFogColor() {
        return fogColor;
    }

    @Override
    public int getWaterColor() {
        return waterColor;
    }

    @Override
    public Integer getWaterFogColor() {
        return waterFogColor;
    }

    @Override
    public Integer getSkyColor() {
        return skyColor;
    }

    @Override
    public Integer getFoliageColor() {
        return foliageColor;
    }

    @Override
    public Integer getGrassColor() {
        return grassColor;
    }

    public Integer getDryFoliageColor() {
        return dryFoliageColor;
    }

    @Override
    public GrassColorModifier getGrassColorModifier() {
        return grassColorModifier;
    }

    @Override
    public @NotNull ParticleCatalog getParticleCatalog() {
        return particleCatalog;
    }

    @Override
    public @NotNull BlockReplacement[] getBlockReplacements() {
        return blockReplacements;
    }

    @Override
    public @NotNull WrappedEnvironmentAttributeMap getEnvironmentAttributeMap() {
        return environmentAttributeMap;
    }

    @Override
    public CustomBiome setFogColor(@Nullable Integer fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    @Override
    public CustomBiome setWaterColor(int waterColor) {
        this.waterColor = waterColor;
        return this;
    }

    @Override
    public CustomBiome setWaterFogColor(@Nullable Integer waterFogColor) {
        this.waterFogColor = waterFogColor;
        return this;
    }

    @Override
    public CustomBiome setSkyColor(@Nullable Integer skyColor) {
        this.skyColor = skyColor;
        return this;
    }

    @Override
    public CustomBiome setFoliageColor(@Nullable Integer foliageColor) {
        this.foliageColor = foliageColor;
        return this;
    }

    @Override
    public CustomBiome setGrassColor(@Nullable Integer grassColor) {
        this.grassColor = grassColor;
        return this;
    }

    @Override
    public CustomBiome setDryFoliageColor(@Nullable Integer dryFoliageColor) {
        this.dryFoliageColor = dryFoliageColor;
        return this;
    }

    @Override
    public CustomBiome setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier) {
        this.grassColorModifier = grassColorModifier;
        return this;
    }

    @Override
    public CustomBiome setParticleCatalog(@NotNull ParticleCatalog particleCatalog) {
        this.particleCatalog = particleCatalog;
        return this;
    }

    @Override
    public CustomBiome setBlockReplacements(@NotNull BlockReplacement[] blockReplacements) {
        this.blockReplacements = blockReplacements;
        return this;
    }

    @Override
    public CustomBiome setEnvironmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
        this.environmentAttributeMap = environmentAttributeMap;
        return this;
    }

    @Override
    public CustomBiome register() {
        BiomeRegistry.newRegistry().register(this);
        return this;
    }

    @Override
    public CustomBiome modify() {
        BiomeRegistry.newRegistry().modify(this);
        return this;
    }

    @Override // TODO: cleanup
    public boolean isSimilar(@NotNull CustomBiome otherBiome) {
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
        if (this.blockReplacements.length != otherBiome.getBlockReplacements().length) return false;
        for (int i = 0; i < this.blockReplacements.length; i++) {
            if (!this.blockReplacements[i].equals(otherBiome.getBlockReplacements()[i])) {
                return false;
            }
        }
        return this.environmentAttributeMap.equals(otherBiome.getEnvironmentAttributeMap());
    }
}
