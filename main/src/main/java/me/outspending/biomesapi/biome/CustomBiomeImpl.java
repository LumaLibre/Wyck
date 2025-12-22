package me.outspending.biomesapi.biome;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.outspending.biomesapi.BiomeSettings;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a custom biome implementation.
 *
 * @author Outspending
 * @version 0.0.4
 * @since 0.0.2
 */
@AsOf("0.0.4")
public final class CustomBiomeImpl implements CustomBiome {

    // Required Settings
    private final BiomeResourceKey resourceKey;
    private final BiomeSettings settings;

    // Required Colors
    private int fogColor;
    private int waterColor;
    private int waterFogColor;
    private int skyColor;

    // Optional Colors
    private int foliageColor = 0;
    private int grassColor = 0;

    // Optional Settings
    private ParticleRenderer particleRenderer;
    private BlockReplacement[] blockReplacements;

    @AsOf("0.0.2")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            int fogColor,
            int waterColor,
            int waterFogColor,
            int skyColor,

            @NotNull ParticleRenderer particleRenderer
    ) {
        this.resourceKey = resourceKey;
        this.settings = settings;
        this.particleRenderer = particleRenderer;

        this.fogColor = fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.skyColor = skyColor;
        this.blockReplacements = new BlockReplacement[0];
    }

    @AsOf("0.0.2")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            int fogColor,
            int waterColor,
            int waterFogColor,
            int skyColor,
            int foliageColor,
            int grassColor,

            @NotNull ParticleRenderer particleRenderer
    ) {
        this(resourceKey, settings, fogColor, waterColor, waterFogColor, skyColor, particleRenderer);
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.blockReplacements = new BlockReplacement[0];
    }

    @AsOf("0.0.4")
    public CustomBiomeImpl(
            @NotNull BiomeResourceKey resourceKey,
            @NotNull BiomeSettings settings,

            int fogColor,
            int waterColor,
            int waterFogColor,
            int skyColor,
            int foliageColor,
            int grassColor,

            @NotNull ParticleRenderer particleRenderer,
            @NotNull BlockReplacement[] blockReplacements
    ) {
        this(resourceKey, settings, fogColor, waterColor, waterFogColor, skyColor, particleRenderer);
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.blockReplacements = blockReplacements;
    }

    @Override
    public @NotNull NamespacedKey toNamespacedKey() {
        Identifier resourceLocation = resourceKey.resourceLocation();
        return new NamespacedKey(resourceLocation.getNamespace(), resourceLocation.getPath());
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
    public int getFogColor() {
        return fogColor;
    }

    @Override
    public int getWaterColor() {
        return waterColor;
    }

    @Override
    public int getWaterFogColor() {
        return waterFogColor;
    }

    @Override
    public int getSkyColor() {
        return skyColor;
    }

    @Override
    public int getFoliageColor() {
        return foliageColor;
    }

    @Override
    public int getGrassColor() {
        return grassColor;
    }

    @Override
    public @NotNull ParticleRenderer getParticleRenderer() {
        return particleRenderer;
    }

    @Override
    public @NotNull BlockReplacement[] getBlockReplacements() {
        return blockReplacements;
    }

    @Override
    public void setFogColor(int fogColor) {
        this.fogColor = fogColor;
    }

    @Override
    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    @Override
    public void setWaterFogColor(int waterFogColor) {
        this.waterFogColor = waterFogColor;
    }

    @Override
    public void setSkyColor(int skyColor) {
        this.skyColor = skyColor;
    }

    @Override
    public void setFoliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
    }

    @Override
    public void setGrassColor(int grassColor) {
        this.grassColor = grassColor;
    }

    @Override
    public void setParticleRenderer(@NotNull ParticleRenderer particleRenderer) {
        this.particleRenderer = particleRenderer;
    }

    @Override
    public void setBlockReplacements(@NotNull BlockReplacement[] blockReplacements) {
        this.blockReplacements = blockReplacements;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void register() {
        BiomeRegistry.newRegistry().register(this);
    }

}
