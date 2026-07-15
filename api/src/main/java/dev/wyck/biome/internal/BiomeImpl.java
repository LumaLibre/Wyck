package dev.wyck.biome.internal;

import dev.wyck.biome.Biome;
import dev.wyck.registry.BiomeRegistry;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import dev.wyck.keys.ResourceKey;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.biome.BiomeGenerationSettings;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * This implementation is here because it does not compile
 * against Minecraft internals as of right now.
 * Do not directly use this class, use {@link Biome}.
 */
@NullMarked
@ApiStatus.Internal
public class BiomeImpl implements Biome {

    private final ResourceKey resourceKey;
    private ClimateSettings climateSettings;
    private BiomeSpecialEffects specialEffects;
    private EnvironmentAttributeMap attributes;
    private @Nullable BiomeSpawner biomeSpawner;
    private @Nullable BiomeGenerationSettings generationSettings;

    public BiomeImpl(
        ResourceKey resourceKey,
        ClimateSettings climateSettings,
        BiomeSpecialEffects specialEffects,
        EnvironmentAttributeMap attributes,
        @Nullable BiomeSpawner biomeSpawner,
        @Nullable BiomeGenerationSettings generationSettings
    ) {
        this.resourceKey = resourceKey;
        this.climateSettings = climateSettings;
        this.specialEffects = specialEffects;
        this.attributes = attributes;
        this.biomeSpawner = biomeSpawner;
        this.generationSettings = generationSettings;
    }

    @Override
    public ResourceKey resourceKey() {
        return this.resourceKey;
    }

    @Override
    public ClimateSettings climateSettings() {
        return this.climateSettings;
    }

    @Override
    public void climateSettings(ClimateSettings climateSettings) {
        this.climateSettings = climateSettings;
    }

    @Override
    public BiomeSpecialEffects specialEffects() {
        return this.specialEffects;
    }

    @Override
    public void specialEffects(BiomeSpecialEffects specialEffects) {
        this.specialEffects = specialEffects;
    }

    @Override
    public EnvironmentAttributeMap attributes() {
        return this.attributes;
    }

    @Override
    public void attributes(EnvironmentAttributeMap attributes) {
        this.attributes = attributes;
    }

    @Override
    public @Nullable BiomeSpawner biomeSpawner() {
        return this.biomeSpawner;
    }

    @Override
    public void biomeSpawner(@Nullable BiomeSpawner biomeSpawner) {
        this.biomeSpawner = biomeSpawner;
    }

    @Override
    public @Nullable BiomeGenerationSettings generationSettings() {
        return this.generationSettings;
    }

    @Override
    public void generationSettings(@Nullable BiomeGenerationSettings generationSettings) {
        this.generationSettings = generationSettings;
    }

    @Override
    public org.bukkit.block.Biome bukkitBiome() {
        NamespacedKey bukkitKey = new NamespacedKey(resourceKey.namespace(), resourceKey.path());
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).getOrThrow(bukkitKey);
    }

    @Override
    public boolean isSimilar(Biome otherBiome) {
        if (this == otherBiome) return true;
        if (!this.resourceKey.equals(otherBiome.resourceKey())) return false;
        if (!Objects.equals(this.climateSettings, otherBiome.climateSettings())) return false;
        if (!Objects.equals(this.specialEffects, otherBiome.specialEffects())) return false;
        if (!Objects.equals(this.attributes, otherBiome.attributes())) return false;
        if (!Objects.equals(this.biomeSpawner, otherBiome.biomeSpawner())) return false;
        return Objects.equals(this.generationSettings, otherBiome.generationSettings());
    }

    @Override
    public Key key() {
        return this.resourceKey;
    }

    @Override
    public Object toMinecraft() {
        return BiomeRegistry.registry().buildDelegate(this);
    }

    @Override
    public String toString() {
        return "BiomeImpl{" +
            "resourceKey=" + resourceKey +
            ", climateSettings=" + climateSettings +
            ", specialEffects=" + specialEffects +
            ", attributes=" + attributes +
            ", biomeSpawner=" + biomeSpawner +
            ", generationSettings=" + generationSettings +
            '}';
    }
}
