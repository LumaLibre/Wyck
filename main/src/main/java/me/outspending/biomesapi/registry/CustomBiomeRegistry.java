package me.outspending.biomesapi.registry;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.unsafe.BiomeLock;
import me.outspending.biomesapi.registry.handlers.AttributeMapHandler;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.unsafe.UnsafeNMS;
import me.outspending.biomesapi.unsafe.UnsafeNMSHandler;
import me.outspending.biomesapi.registry.handlers.ParticleCatalogHandler;
import me.outspending.biomesapi.registry.handlers.SpecialEffectsHandler;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

/**
 * This class implements the BiomeRegistry interface and provides a method to register a custom biome to a Minecraft server.
 * It uses the BiomeLock class to unlock the biome registry before registering the custom biome, and then freezes the registry again.
 *
 * @version 1.1.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("1.1.0")
public class CustomBiomeRegistry implements BiomeRegistry {

    private static final SpecialEffectsHandler SPECIAL_EFFECTS_HANDLER = new SpecialEffectsHandler();
    private static final ParticleCatalogHandler PARTICLE_CATALOG_HANDLER = new ParticleCatalogHandler();
    private static final AttributeMapHandler ATTRIBUTE_MAP_HANDLER = new AttributeMapHandler();


    /**
     * This method registers a custom biome to a Minecraft server.
     * It first unlocks the biome registry using the BiomeLock class.
     * Then, it retrieves the biome registry from the server and creates a new Biome object with the settings and colors from the CustomBiome object.
     * The new Biome object is then registered to the biome registry using the ResourceLocation from the CustomBiome object.
     * Finally, it freezes the biome registry again using the BiomeLock class.
     *
     * @param biome The CustomBiome object that should be registered to the server.
     * @version 0.0.1
     */
    @Override
    @AsOf("0.0.1")
    @SuppressWarnings("unchecked")
    public void register(@NotNull CustomBiome biome) {
        Preconditions.checkNotNull(biome, "biome cannot be null");

        BiomeLock.unlock(() -> {

            // Retrieve the biome registry from NMS
            UnsafeNMSHandler.executeNMS(nms -> {
                Registry<@NotNull Biome> registry = (Registry<@NotNull Biome>) nms.getRegistry();

                // Get the ResourceLocation and BiomeSettings from the CustomBiome object
                Identifier resourceLocation = biome.getResourceKey().resourceLocation();
                BiomeSettings settings = biome.getSettings();

                // Build the Biome object
                Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder()
                        .downfall(settings.downfall())
                        .temperature(settings.temperature())
                        .temperatureAdjustment(settings.modifier().getModifier())
                        .hasPrecipitation(settings.hasPrecipitation())
                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                        .generationSettings(BiomeGenerationSettings.EMPTY);

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

                // Create a new Biome object with the settings and colors from the CustomBiome object
                BiomeSpecialEffects effects = SPECIAL_EFFECTS_HANDLER.build(biome);
                SPECIAL_EFFECTS_HANDLER.handle(effects, biomeBuilder);

                ParticleCatalog particleCatalog = biome.getParticleCatalog();
                PARTICLE_CATALOG_HANDLER.handle(particleCatalog, biomeBuilder);

                WrappedEnvironmentAttributeMap wrappedAttributeMap = biome.getEnvironmentAttributeMap();
                ATTRIBUTE_MAP_HANDLER.handle(wrappedAttributeMap, biomeBuilder);

                // Register the new Biome object to the biome registry
                Biome createdBiome = biomeBuilder.build();

                if (!registry.containsKey(resourceLocation)) {
                    Registry.register(registry, resourceLocation, createdBiome);
                }

                // Add the custom biome to the list of registered biomes
                BiomeHandler.getRegisteredBiomes().add(biome);
            });

            return null;
        });
    }

    /**
     * This method modifies an existing biome on the Minecraft server.
     * Another biome must already exist with the same ResourceKey.
     * It takes a CustomBiome object as an argument.
     *
     * @version 0.0.8
     * @param customBiome The CustomBiome that should internally be used to modify the existing biome.
     */
    @Override
    @AsOf("0.0.8")
    public void modify(@NotNull CustomBiome customBiome) {
        BiomeResourceKey resourceKey = customBiome.getResourceKey();

        Preconditions.checkArgument(BiomeHandler.isBiome(resourceKey), "Biome %s is not registered", resourceKey);


        BiomeSettings settings = customBiome.getSettings();

        UnsafeNMS nms = UnsafeNMSHandler.getNMS().orElseThrow();

        Registry<net.minecraft.world.level.biome.@NotNull Biome> biomeRegistry = (Registry<net.minecraft.world.level.biome.Biome>) nms.getRegistry();
        net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional(resourceKey.resourceLocation()).orElseThrow(
                () -> new IllegalStateException("Biome " + resourceKey + " is not registered in the internal biome registry")
        );

        // Rebuild biome components
        Biome.ClimateSettings climateSettings = new Biome.ClimateSettings(settings.hasPrecipitation(), settings.temperature(), settings.modifier().getModifier(), settings.downfall());
        ParticleCatalog particleCatalog = customBiome.getParticleCatalog();
        List<net.minecraft.world.attribute.AmbientParticle> particles = PARTICLE_CATALOG_HANDLER.create(particleCatalog);


        EnvironmentAttributeMap.Builder environmentAttributeMapBuilder = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.AMBIENT_PARTICLES, particles);

        // TODO: Replace with WrappedEnvironmentAttributeMap in the future
        if (customBiome.getFogColor() != null) {
            environmentAttributeMapBuilder.set(EnvironmentAttributes.FOG_COLOR, customBiome.getFogColor());
        }
        if (customBiome.getSkyColor() != null) {
            environmentAttributeMapBuilder.set(EnvironmentAttributes.SKY_COLOR, customBiome.getSkyColor());
        }
        if (customBiome.getWaterFogColor() != null) {
            environmentAttributeMapBuilder.set(EnvironmentAttributes.WATER_FOG_COLOR, customBiome.getWaterFogColor());
        }
        WrappedEnvironmentAttributeMap wrappedAttributeMap = customBiome.getEnvironmentAttributeMap();
        wrappedAttributeMap.applyToMapBuilder(environmentAttributeMapBuilder);


        EnvironmentAttributeMap environmentAttributeMap = environmentAttributeMapBuilder.build();

        BiomeSpecialEffects specialEffects = SPECIAL_EFFECTS_HANDLER.build(customBiome);

        // Time to reflect
        try {
            Field climateSettingsField = Biome.class.getDeclaredField("climateSettings");
            Field environmentAttributesField = Biome.class.getDeclaredField("attributes");
            Field specialEffectsField = Biome.class.getDeclaredField("specialEffects");

            climateSettingsField.setAccessible(true);
            environmentAttributesField.setAccessible(true);
            specialEffectsField.setAccessible(true);

            climateSettingsField.set(biome, climateSettings);
            environmentAttributesField.set(biome, environmentAttributeMap);
            specialEffectsField.set(biome, specialEffects);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to modify biome settings", e);
        }

        BiomeHandler.replaceBiome(resourceKey, customBiome);
    }

}
