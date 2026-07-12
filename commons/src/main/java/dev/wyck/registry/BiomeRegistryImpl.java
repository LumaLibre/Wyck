package dev.wyck.registry;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.model.biome.Biome;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.ClimateSettings;
import dev.wyck.wrapper.biome.entity.BiomeSpawner;
import dev.wyck.wrapper.biome.entity.MobCategory;
import dev.wyck.wrapper.biome.TemperatureModifier;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.environment.attribute.NmsEnvironmentAttributes;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.entity.EntityType;
import dev.wyck.wrapper.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * This class implements the BiomeRegistry interface and provides a method to register a custom biome to a Minecraft server.
 * It uses the BiomeLock class to unlock the biome registry before registering the custom biome, and then freezes the registry again.
 *
 * @version 2.0.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@WireFactory
@AsOf("0.0.1")
@ApiStatus.Internal
public class BiomeRegistryImpl implements BiomeRegistry {

    private final Lazy<WyckRegistry> registry = WyckRegistry.lazy(RegistryId.BIOME);

    @Override
    @AsOf("2.3.0")
    public net.minecraft.world.level.biome.Biome buildDelegate(Biome biome) {
        Preconditions.checkNotNull(biome, "biome cannot be null");

        ClimateSettings settings = biome.climateSettings();
        BiomeSpecialEffects specialEffects = biome.specialEffects();
        EnvironmentAttributeMap attributes = biome.attributes();
        BiomeSpawner spawner = biome.biomeSpawner();
        BiomeGenerationSettings generationSettings = biome.generationSettings();

        net.minecraft.world.level.biome.Biome.BiomeBuilder biomeBuilder = new net.minecraft.world.level.biome.Biome.BiomeBuilder()
            .hasPrecipitation(settings.hasPrecipitation())
            .downfall(settings.downfall())
            .temperature(settings.temperature())
            .temperatureAdjustment(settings.temperatureModifier().toNms(net.minecraft.world.level.biome.Biome.TemperatureModifier.class))
            .specialEffects(specialEffects.asHandle())
            .mobSpawnSettings(spawner != null ? spawner.asHandle() : net.minecraft.world.level.biome.MobSpawnSettings.EMPTY)
            .generationSettings(generationSettings != null ? generationSettings.asHandle() : net.minecraft.world.level.biome.BiomeGenerationSettings.EMPTY);

        if (!attributes.empty()) {
            NmsEnvironmentAttributes.applyTo(biomeBuilder, attributes);
        }

        return biomeBuilder.build();
    }

    @Override
    @AsOf("2.3.1")
    @SuppressWarnings("unchecked")
    public void register(Collection<Biome> biomes) {
        Preconditions.checkNotNull(biomes, "biomes cannot be null");

        this.registry.get().whileUnfrozen(() -> {
            Registry<net.minecraft.world.level.biome.Biome> registry = (Registry<net.minecraft.world.level.biome.@NonNull Biome>) this.registry.get().toMinecraft();
            for (Biome biome : biomes) {
                Identifier resourceLocation = biome.resourceKey().identifier();

                net.minecraft.world.level.biome.Biome createdBiome = buildDelegate(biome);

                if (!registry.containsKey(resourceLocation)) {
                    Registry.register(registry, resourceLocation, createdBiome);
                }

                try {
                    KeyChains.BIOMES.append(biome);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    @AsOf("0.0.8")
    public void modify(Collection<Biome> biomes) {
        for (Biome abstractBiome : biomes) {
            ResourceKey key = abstractBiome.resourceKey();
            Preconditions.checkNotNull(key, "key cannot be null");
            Preconditions.checkNotNull(abstractBiome, "newData cannot be null");

            Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = this.registry.get().asHandle();
            net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional((Identifier) key.resourceLocation()).orElseThrow(
                () -> new IllegalStateException("Biome " + key + " is not registered in the internal biome registry")
            );

            // Rebuild biome components
            net.minecraft.world.attribute.EnvironmentAttributeMap.Builder environmentAttributeMapBuilder = net.minecraft.world.attribute.EnvironmentAttributeMap.builder();
            NmsEnvironmentAttributes.applyTo(environmentAttributeMapBuilder, abstractBiome.attributes());

            net.minecraft.world.level.biome.Biome.ClimateSettings climateSettings = abstractBiome.climateSettings().asHandle();
            net.minecraft.world.level.biome.BiomeSpecialEffects specialEffects = abstractBiome.specialEffects().asHandle();
            net.minecraft.world.attribute.EnvironmentAttributeMap environmentAttributeMap = environmentAttributeMapBuilder.build();

            BiomeSpawner spawner = abstractBiome.biomeSpawner();
            BiomeGenerationSettings gen = abstractBiome.generationSettings();
            net.minecraft.world.level.biome.MobSpawnSettings mobSpawnSettings = spawner != null ? spawner.asHandle() : net.minecraft.world.level.biome.MobSpawnSettings.EMPTY;
            net.minecraft.world.level.biome.BiomeGenerationSettings generationSettings = gen != null ? gen.asHandle() : net.minecraft.world.level.biome.BiomeGenerationSettings.EMPTY;

            // Time to reflect
            try {
                // TODO: Clean this up with some reflection util
                Field climateSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("climateSettings");
                Field environmentAttributesField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("attributes");
                Field specialEffectsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("specialEffects");
                Field mobSpawnSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("mobSettings");
                Field generationSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("generationSettings");

                climateSettingsField.setAccessible(true);
                environmentAttributesField.setAccessible(true);
                specialEffectsField.setAccessible(true);
                mobSpawnSettingsField.setAccessible(true);
                generationSettingsField.setAccessible(true);

                climateSettingsField.set(biome, climateSettings);
                environmentAttributesField.set(biome, environmentAttributeMap);
                specialEffectsField.set(biome, specialEffects);
                mobSpawnSettingsField.set(biome, mobSpawnSettings);
                generationSettingsField.set(biome, generationSettings);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to modify biome settings", e);
            }

            KeyChains.BIOMES.replace(key, abstractBiome);
        }
    }

    // TODO: Cleanup when decoders are available
    @AsOf("2.3.0")
    @SuppressWarnings("unchecked")
    public @Nullable Biome getBiome(ResourceKey key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        if (KeyChains.BIOMES.isRegistered(key)) {
            return KeyChains.BIOMES.getOrThrow(key);
        }

        Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = this.registry.get().asHandle();

        net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional((Identifier) key.resourceLocation()).orElse(null);
        if (biome == null) {
            return null;
        }

        Biome.Builder builder = Biome.builder(key);

        try {
            Field climateSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("climateSettings");
            climateSettingsField.setAccessible(true);

            net.minecraft.world.level.biome.Biome.ClimateSettings climate = (net.minecraft.world.level.biome.Biome.ClimateSettings) climateSettingsField.get(biome);
            net.minecraft.world.attribute.EnvironmentAttributeMap attributeMap = biome.getAttributes();
            net.minecraft.world.level.biome.MobSpawnSettings mobSettings = biome.getMobSettings();
            net.minecraft.world.level.biome.BiomeSpecialEffects specialEffects = biome.getSpecialEffects();


            builder.specialEffects(BiomeSpecialEffects.builder()
                .waterColor(specialEffects.waterColor())
                .foliageColorOverride(specialEffects.foliageColorOverride().orElse(null))
                .dryFoliageColorOverride(specialEffects.dryFoliageColorOverride().orElse(null))
                .grassColorOverride(specialEffects.grassColorOverride().orElse(null))
                .grassColorModifier(GrassColorModifier.TRANSLATOR.fromNms(specialEffects.grassColorModifier()))
                .build());

            builder.climateSettings(
                ClimateSettings.builder()
                    .hasPrecipitation(climate.hasPrecipitation())
                    .temperature(climate.temperature())
                    .downfall(climate.downfall())
                    .temperatureModifier(TemperatureModifier.TRANSLATOR.fromNms(climate.temperatureModifier()))
                    .build()
            );

            // TODO: Reverse AmbientParticles back to wrappers
            // TODO: Reverse attributes back to wrappers
            // TODO: Reverse generation settings back to wrappers
            //attributeMap.applyModifier(Environment)

            builder.biomeSpawner(readSpawner(mobSettings));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to read biome state for " + key, e);
        }

        return builder.build();
    }

    // TODO: Remove this when decoders are available

    @SuppressWarnings("unchecked")
    private BiomeSpawner readSpawner(MobSpawnSettings settings) {
        BiomeSpawner.Builder spawnerBuilder = BiomeSpawner.builder()
            .creatureGenerationProbability(settings.getCreatureProbability());


        for (net.minecraft.world.entity.MobCategory nmsCategory : net.minecraft.world.entity.MobCategory.values()) {
            MobCategory category = toWrapperCategory(nmsCategory);

            for (Weighted<MobSpawnSettings.SpawnerData> weighted : settings.getMobs(nmsCategory).unwrap()) {
                MobSpawnSettings.SpawnerData data = weighted.value();
                org.bukkit.entity.EntityType type = toBukkitEntityType(data.type());
                if (type == null) continue;
                spawnerBuilder.spawner(category, weighted.weight(), type, data.minCount(), data.maxCount());
            }
        }

        try {
            Field costsField = MobSpawnSettings.class.getDeclaredField("mobSpawnCosts");
            costsField.setAccessible(true);
            Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> costs =
                (Map<net.minecraft.world.entity.EntityType<?>, MobSpawnSettings.MobSpawnCost>) costsField.get(settings);

            for (Map.Entry<net.minecraft.world.entity.EntityType<?>, MobSpawnSettings.MobSpawnCost> entry : costs.entrySet()) {
                org.bukkit.entity.EntityType type = toBukkitEntityType(entry.getKey());
                if (type == null) continue;
                MobSpawnSettings.MobSpawnCost cost = entry.getValue();

                spawnerBuilder.spawnCost(type, cost.charge(), cost.energyBudget());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to read mob spawn costs", e);
        }

        return spawnerBuilder.build();
    }

    private static MobCategory toWrapperCategory(net.minecraft.world.entity.MobCategory nms) {
        return MobCategory.TRANSLATOR.fromNms(nms);
    }

    private static org.bukkit.entity.@Nullable EntityType toBukkitEntityType(net.minecraft.world.entity.EntityType<?> nms) {
        NamespacedKey nmsKey = NamespacedKey.fromString(
            net.minecraft.world.entity.EntityType.getKey(nms).toString());
        return nmsKey == null ? null : org.bukkit.Registry.ENTITY_TYPE.get(nmsKey);
    }
}
