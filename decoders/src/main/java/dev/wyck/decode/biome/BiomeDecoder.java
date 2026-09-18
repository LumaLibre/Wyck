package dev.wyck.decode.biome;

import dev.wyck.biome.Biome;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.entity.MobSpawnSettings;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.decode.Decoders;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BiomeDecoder implements Decodable<Biome, Object> {

    @Override
    @SuppressWarnings("removal")
    public Biome decode(Object minecraftObject) { // TODO: maybe remove holder support here
        net.minecraft.world.level.biome.Biome biome = Decoders.value(minecraftObject);
        var key = minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()
            ? Decoders.referenceKey(holder)
            : BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME).getResourceKey(biome)
                .map(Decoders::key)
                .orElseThrow(() -> new IllegalArgumentException("The biome is not registered: " + biome));

        EnvironmentAttributeMap attributes = EnvironmentAttributeMap.decode(biome.getAttributes());
        Biome.Builder builder = Biome.builder(key)
            .climateSettings(ClimateSettings.decode(biome.climateSettings))
            .specialEffects(BiomeSpecialEffects.decode(biome.getSpecialEffects()))
            .attributes(attributes)
            .generationSettings(BiomeGenerationSettings.decode(biome.getGenerationSettings()));

        MobSpawnSettings spawnSettings = attributes.get(EnvironmentAttributes.NATURAL_MOB_SPAWNS);
        if (spawnSettings != null) {
            builder.biomeSpawner(BiomeSpawner.decode(spawnSettings.toMinecraft()));
        }
        return builder.build();
    }
}
