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
 * @version 1.1.0
 * @since 0.0.2
 */
@ApiStatus.Internal
@AsOf("1.1.0")
public final class CustomBiomeImpl implements CustomBiome {

    // Required Settings
    private final BiomeResourceKey resourceKey;
    private final BiomeSettings settings;

    // Required Colors
    private int waterColor = 0xF54927;

    // Optional Colors
    private Integer fogColor;
    private Integer waterFogColor;
    private Integer skyColor;
    private Integer foliageColor;
    private Integer grassColor;
    private Integer dryFoliageColor;

    // Optional Settings
    private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;
    private ParticleCatalog particleCatalog = ParticleCatalog.EMPTY;
    private BlockReplacement[] blockReplacements = new BlockReplacement[0];

    private WrappedEnvironmentAttributeMap environmentAttributeMap = WrappedEnvironmentAttributeMap.EMPTY;

    @AsOf("1.1.0")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            @Nullable Integer fogColor,
            int waterColor,
            @Nullable Integer waterFogColor,
            @Nullable Integer skyColor,

            @NotNull ParticleCatalog particleCatalog
    ) {
        this.resourceKey = resourceKey;
        this.settings = settings;
        this.particleCatalog = particleCatalog;

        this.fogColor = fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.skyColor = skyColor;
        this.blockReplacements = new BlockReplacement[0];
    }

    @AsOf("1.1.0")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            @Nullable Integer fogColor,
            int waterColor,
            @Nullable Integer waterFogColor,
            @Nullable Integer skyColor,
            @Nullable Integer foliageColor,
            @Nullable Integer grassColor,

            @NotNull ParticleCatalog particleCatalog
    ) {
        this(resourceKey, settings, fogColor, waterColor, waterFogColor, skyColor, particleCatalog);
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.blockReplacements = new BlockReplacement[0];
    }

    @AsOf("1.1.0")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            @Nullable Integer fogColor,
            int waterColor,
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
        this(resourceKey, settings, fogColor, waterColor, waterFogColor, skyColor, particleCatalog);
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.dryFoliageColor = dryFoliageColor;
        this.grassColorModifier = grassColorModifier;
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
    public void setFogColor(@Nullable Integer fogColor) {
        this.fogColor = fogColor;
    }

    @Override
    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    @Override
    public void setWaterFogColor(@Nullable Integer waterFogColor) {
        this.waterFogColor = waterFogColor;
    }

    @Override
    public void setSkyColor(@Nullable Integer skyColor) {
        this.skyColor = skyColor;
    }

    @Override
    public void setFoliageColor(@Nullable Integer foliageColor) {
        this.foliageColor = foliageColor;
    }

    @Override
    public void setGrassColor(@Nullable Integer grassColor) {
        this.grassColor = grassColor;
    }

    public void setDryFoliageColor(@Nullable Integer dryFoliageColor) {
        this.dryFoliageColor = dryFoliageColor;
    }

    @Override
    public void setGrassColorModifier(@NotNull GrassColorModifier grassColorModifier) {
        this.grassColorModifier = grassColorModifier;
    }

    @Override
    public void setParticleCatalog(@NotNull ParticleCatalog particleCatalog) {
        this.particleCatalog = particleCatalog;
    }

    @Override
    public void setBlockReplacements(@NotNull BlockReplacement[] blockReplacements) {
        this.blockReplacements = blockReplacements;
    }

    @Override
    public void setEnvironmentAttributeMap(@NotNull WrappedEnvironmentAttributeMap environmentAttributeMap) {
        this.environmentAttributeMap = environmentAttributeMap;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void register() {
        BiomeRegistry.newRegistry().register(this);
    }

    @Override
    public void modify() {
        BiomeRegistry.newRegistry().modify(this);
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
