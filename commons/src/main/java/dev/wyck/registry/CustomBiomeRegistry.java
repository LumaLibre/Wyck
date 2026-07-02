package dev.wyck.registry;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.model.biome.Biome;
import dev.wyck.model.biome.VanillaBiome;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.keys.ResourceKeyImpl;
import dev.wyck.registry.handlers.AttributeMapHandler;
import dev.wyck.registry.handlers.BiomeGenerationSettingsHandler;
import dev.wyck.registry.handlers.MobSpawnSettingsHandler;
import dev.wyck.registry.handlers.ParticleCatalogHandler;
import dev.wyck.registry.handlers.SpecialEffectsHandler;
import dev.wyck.registry.internal.FrozenRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.BiomeSettings;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.entity.MobCategory;
import dev.wyck.wrapper.environment.BiomeTempModifier;
import dev.wyck.wrapper.environment.GrassColorModifier;
import dev.wyck.wrapper.environment.attribute.NmsEnvironmentAttributes;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
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
@AsOf("1.1.0")
@ApiStatus.Internal
public class CustomBiomeRegistry implements BiomeRegistry {

    private static final SpecialEffectsHandler SPECIAL_EFFECTS_HANDLER = new SpecialEffectsHandler();
    private static final ParticleCatalogHandler PARTICLE_CATALOG_HANDLER = new ParticleCatalogHandler();
    private static final AttributeMapHandler ATTRIBUTE_MAP_HANDLER = new AttributeMapHandler();
    private static final MobSpawnSettingsHandler MOB_SPAWN_SETTINGS_HANDLER = new MobSpawnSettingsHandler();
    private static final BiomeGenerationSettingsHandler BIOME_GENERATION_SETTINGS_HANDLER = new BiomeGenerationSettingsHandler();

    private final Lazy<FrozenRegistry> biomeRegistry = FrozenRegistry.lazy("worldgen/biome");

    // TODO: Extract commons


    /**
     * Builds the NMS {@link net.minecraft.world.level.biome.Biome} from a {@link Biome} using the registered settings, colors,
     * special effects, particles, environment attributes, and mob-spawn settings.
     *
     * <p>This performs no registration and touches no server state, so it is safe to call during the
     * bootstrap/registry-load phase as well as at runtime. Covariantly narrows the API's
     * {@link BiomeRegistry#buildDelegate(AbstractBiome)} {@code Object} return to {@link net.minecraft.world.level.biome.Biome}.
     *
     * @param biome the custom biome to convert
     * @return the constructed NMS biome
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public net.minecraft.world.level.biome.Biome buildDelegate(AbstractBiome biome) {
        Preconditions.checkNotNull(biome, "biome cannot be null");

        BiomeSettings settings = biome.getSettings();

        net.minecraft.world.level.biome.Biome.BiomeBuilder biomeBuilder = new net.minecraft.world.level.biome.Biome.BiomeBuilder()
            .downfall(settings.downfall())
            .temperature(settings.temperature())
            .temperatureAdjustment(settings.modifier().toNms(net.minecraft.world.level.biome.Biome.TemperatureModifier.class))
            .hasPrecipitation(settings.hasPrecipitation())
            .mobSpawnSettings(MobSpawnSettings.EMPTY)
            .generationSettings(net.minecraft.world.level.biome.BiomeGenerationSettings.EMPTY);

        // TODO: Replace with WrappedEnvironmentAttributeMap in the future
        if (biome.getFogColor() != null) {
            biomeBuilder.setAttribute(EnvironmentAttributes.FOG_COLOR, biome.getFogColor());
        }
        if (biome.getSkyColor() != null) {
            biomeBuilder.setAttribute(EnvironmentAttributes.SKY_COLOR, biome.getSkyColor());
        }
        if (biome.getWaterFogColor() != null) {
            biomeBuilder.setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, biome.getWaterFogColor());
        }

        BiomeSpecialEffects effects = SPECIAL_EFFECTS_HANDLER.build(biome);
        SPECIAL_EFFECTS_HANDLER.handle(effects, biomeBuilder);

        ParticleCatalog particleCatalog = biome.getParticleCatalog();
        PARTICLE_CATALOG_HANDLER.handle(particleCatalog, biomeBuilder);

        EnvironmentAttributeMap wrappedAttributeMap = biome.getAttributes();
        ATTRIBUTE_MAP_HANDLER.handle(wrappedAttributeMap, biomeBuilder);

        BiomeSpawner spawner = biome.getBiomeSpawner();
        MOB_SPAWN_SETTINGS_HANDLER.handle(spawner, biomeBuilder);

        BiomeGenerationSettings generationSettings = biome.getGenerationSettings();
        BIOME_GENERATION_SETTINGS_HANDLER.handle(generationSettings, biomeBuilder);

        return biomeBuilder.build();
    }

    /**
     * Registers a custom biome to the running server's biome registry (runtime injection).
     * Unlocks the registry, builds the biome via {@link #buildDelegate(AbstractBiome)}, registers it
     * if absent, and re-freezes the registry.
     *
     * @param biomes The CustomBiome objects that should be registered to the server.
     * @since 2.3.1
     */
    @Override
    @AsOf("2.3.1")
    @SuppressWarnings("unchecked")
    public void register(Collection<Biome> biomes) {
        Preconditions.checkNotNull(biomes, "biomes cannot be null");

        this.biomeRegistry.get().whileUnfrozen(() -> {
            Registry<net.minecraft.world.level.biome.Biome> registry = (Registry<@NonNull net.minecraft.world.level.biome.Biome>) biomeRegistry.get().toMinecraft();
            for (Biome biome : biomes) {
                Identifier resourceLocation = ((ResourceKeyImpl) biome.getResourceKey()).resourceLocation();

                net.minecraft.world.level.biome.Biome createdBiome = buildDelegate(biome);

                if (!registry.containsKey(resourceLocation)) {
                    Registry.register(registry, resourceLocation, createdBiome);
                }

                KeyChains.BIOMES.append(biome);
            }
        });
    }


    /**
     * This method modifies an existing biome on the Minecraft server.
     * Another biome must already exist with the same ResourceKey.
     * It takes a CustomBiome object as an argument.
     *
     * @param abstractBiomes The CustomBiome objects containing the modifications to apply to the server's biomes.
     * @since 0.0.8
     */
    @Override
    @AsOf("0.0.8")
    public void modify(Collection<AbstractBiome> abstractBiomes) {
        for (AbstractBiome abstractBiome : abstractBiomes) {
            ResourceKey key = abstractBiome.getResourceKey();
            Preconditions.checkNotNull(key, "key cannot be null");
            Preconditions.checkNotNull(abstractBiome, "newData cannot be null");

            BiomeSettings settings = abstractBiome.getSettings();


            Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = (Registry<@NonNull net.minecraft.world.level.biome.Biome>) this.biomeRegistry.get().toMinecraft();
            net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional((Identifier) key.resourceLocation()).orElseThrow(
                () -> new IllegalStateException("Biome " + key + " is not registered in the internal biome registry")
            );

            // Rebuild biome components
            net.minecraft.world.level.biome.Biome.ClimateSettings climateSettings = new net.minecraft.world.level.biome.Biome.ClimateSettings(
                settings.hasPrecipitation(), settings.temperature(),
                settings.modifier().toNms(net.minecraft.world.level.biome.Biome.TemperatureModifier.class), settings.downfall());
            ParticleCatalog particleCatalog = abstractBiome.getParticleCatalog();
            List<net.minecraft.world.attribute.AmbientParticle> particles = PARTICLE_CATALOG_HANDLER.create(particleCatalog);

            net.minecraft.world.attribute.EnvironmentAttributeMap.Builder environmentAttributeMapBuilder = net.minecraft.world.attribute.EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.AMBIENT_PARTICLES, particles);

            // TODO: Replace with WrappedEnvironmentAttributeMap in the future
            if (abstractBiome.getFogColor() != null) {
                environmentAttributeMapBuilder.set(EnvironmentAttributes.FOG_COLOR, abstractBiome.getFogColor());
            }
            if (abstractBiome.getSkyColor() != null) {
                environmentAttributeMapBuilder.set(EnvironmentAttributes.SKY_COLOR, abstractBiome.getSkyColor());
            }
            if (abstractBiome.getWaterFogColor() != null) {
                environmentAttributeMapBuilder.set(EnvironmentAttributes.WATER_FOG_COLOR, abstractBiome.getWaterFogColor());
            }
            EnvironmentAttributeMap wrappedAttributeMap = abstractBiome.getAttributes();
            NmsEnvironmentAttributes.applyTo(environmentAttributeMapBuilder, wrappedAttributeMap);

            net.minecraft.world.attribute.EnvironmentAttributeMap environmentAttributeMap = environmentAttributeMapBuilder.build();

            BiomeSpecialEffects specialEffects = SPECIAL_EFFECTS_HANDLER.build(abstractBiome);

            BiomeSpawner spawner = abstractBiome.getBiomeSpawner();

            BiomeGenerationSettings generationSettings = abstractBiome.getGenerationSettings();

            // Time to reflect
            try {
                Field climateSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("climateSettings");
                Field environmentAttributesField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("attributes");
                Field specialEffectsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("specialEffects");

                climateSettingsField.setAccessible(true);
                environmentAttributesField.setAccessible(true);
                specialEffectsField.setAccessible(true);

                climateSettingsField.set(biome, climateSettings);
                environmentAttributesField.set(biome, environmentAttributeMap);
                specialEffectsField.set(biome, specialEffects);

                if (spawner != null) {
                    Field mobSpawnSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("mobSettings");
                    mobSpawnSettingsField.setAccessible(true);
                    mobSpawnSettingsField.set(biome, spawner.toMinecraft());
                }

                if (generationSettings != null) {
                    Field generationSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("generationSettings");
                    generationSettingsField.setAccessible(true);
                    generationSettingsField.set(biome, generationSettings.toMinecraft());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to modify biome settings", e);
            }

            if (abstractBiome instanceof Biome customBiome) {
                KeyChains.BIOMES.replace(key, customBiome);
            }
        }
    }

    @AsOf("2.3.0")
    @SuppressWarnings("unchecked")
    public @Nullable AbstractBiome getBiome(ResourceKey key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        if (KeyChains.BIOMES.isRegistered(key)) {
            return KeyChains.BIOMES.getOrThrow(key);
        }

        Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = (Registry<@NonNull net.minecraft.world.level.biome.Biome>) this.biomeRegistry.get().toMinecraft();

        net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional((Identifier) key.resourceLocation()).orElse(null);
        if (biome == null) {
            return null;
        }

        AbstractBiome.Builder builder = AbstractBiome.builder(key);

        try {
            Field climateSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("climateSettings");
            climateSettingsField.setAccessible(true);

            net.minecraft.world.level.biome.Biome.ClimateSettings climate = (net.minecraft.world.level.biome.Biome.ClimateSettings) climateSettingsField.get(biome);
            net.minecraft.world.attribute.EnvironmentAttributeMap attributeMap = biome.getAttributes();
            MobSpawnSettings mobSettings = biome.getMobSettings();
            BiomeSpecialEffects specialEffects = biome.getSpecialEffects();

            builder.waterColor(Color.fromARGB(specialEffects.waterColor()));
            specialEffects.foliageColorOverride().ifPresent(c -> builder.foliageColor(Color.fromARGB(c)));
            specialEffects.dryFoliageColorOverride().ifPresent(c -> builder.dryFoliageColor(Color.fromARGB(c)));
            specialEffects.grassColorOverride().ifPresent(c -> builder.grassColor(Color.fromARGB(c)));
            builder.grassColorModifier(GrassColorModifier.TRANSLATOR.fromNms(specialEffects.grassColorModifier()));


            builder.settings(BiomeSettings.builder()
                .hasPrecipitation(climate.hasPrecipitation())
                .temperature(climate.temperature())
                .downfall(climate.downfall())
                .modifier(BiomeTempModifier.TRANSLATOR.fromNms(climate.temperatureModifier()))
                .build());


            if (attributeMap.contains(EnvironmentAttributes.FOG_COLOR)) {
                builder.fogColor(Color.fromARGB(attributeMap.applyModifier(EnvironmentAttributes.FOG_COLOR, 0)));
            }
            if (attributeMap.contains(EnvironmentAttributes.SKY_COLOR)) {
                builder.skyColor(Color.fromARGB(attributeMap.applyModifier(EnvironmentAttributes.SKY_COLOR, 0)));
            }
            if (attributeMap.contains(EnvironmentAttributes.WATER_FOG_COLOR)) {
                builder.waterFogColor(Color.fromARGB(attributeMap.applyModifier(EnvironmentAttributes.WATER_FOG_COLOR, 0)));
            }

            // TODO: Reverse AmbientParticles back to wrappers
            // TODO: Reverse attributes back to wrappers
            // TODO: Reverse generation settings back to wrappers
            //attributeMap.applyModifier(Environment)

            builder.setSpawner(readSpawner(mobSettings));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to read biome state for " + key, e);
        }


        AbstractBiome abstractBiome = builder.build();
        if (key.namespace().equals(ResourceKey.MINECRAFT_NAMESPACE)) {
            return VanillaBiome.builder(abstractBiome).build();
        }
        return abstractBiome;
    }

    @SuppressWarnings("unchecked")
    private BiomeSpawner readSpawner(MobSpawnSettings settings) {
        BiomeSpawner.Builder spawnerBuilder = BiomeSpawner.builder()
            .setCreatureGenerationProbability(settings.getCreatureProbability());


        for (net.minecraft.world.entity.MobCategory nmsCategory : net.minecraft.world.entity.MobCategory.values()) {
            MobCategory category = toWrapperCategory(nmsCategory);

            for (Weighted<MobSpawnSettings.SpawnerData> weighted : settings.getMobs(nmsCategory).unwrap()) {
                MobSpawnSettings.SpawnerData data = weighted.value();
                org.bukkit.entity.EntityType type = toBukkitEntityType(data.type());
                if (type == null) continue;
                spawnerBuilder.addSpawners(category, weighted.weight(), type, data.minCount(), data.maxCount());
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

                spawnerBuilder.addMobSpawnCost(type, cost.charge(), cost.energyBudget());
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
