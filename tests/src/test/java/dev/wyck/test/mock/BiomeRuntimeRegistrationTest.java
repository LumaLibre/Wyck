package dev.wyck.test.mock;

import dev.wyck.biome.Biome;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.NoneFeatureConfiguration;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.placement.PlacementModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BiomeRuntimeRegistrationTest {

    private static RegistryAccess.Frozen access;

    @BeforeAll
    static void installServer() {
        access = MockServer.install();
    }

    @AfterAll
    static void removeServer() {
        MockServer.uninstall();
    }

    private static net.minecraft.core.Registry<net.minecraft.world.level.biome.Biome> biomes() {
        return access.lookupOrThrow(Registries.BIOME);
    }

    private static Biome.Builder builder(String key) {
        return Biome.builder()
                .resourceKey(ResourceKey.of(key))
                .specialEffects(BiomeSpecialEffects.builder()
                        .foliageColorOverride(0x8b69ca)
                        .build());
    }

    private static void assertFrozen(String context) {
        net.minecraft.world.level.biome.Biome any = biomes().getValue(Identifier.parse("minecraft:plains"));
        assertThrows(IllegalStateException.class,
                () -> net.minecraft.core.Registry.register(biomes(), Identifier.parse("test:direct_write"), any),
                context);
    }

    @Test
    void theBiomeRegistryIsFrozenBeforeAnythingRegisters() {
        assertTrue(biomes().size() > 0, "expected vanilla biomes to be present");
        assertFrozen("a live server's biome registry is frozen; if this does not throw, "
                + "the test is not exercising the runtime path at all");
    }

    @Test
    void registerAddsTheBiomeToTheLiveRegistry() {
        builder("test:runtime_biome").register();

        net.minecraft.world.level.biome.Biome registered = biomes().getValue(Identifier.parse("test:runtime_biome"));
        assertNotNull(registered, "biome missing from the live registry; present: " + biomes().size());
        assertEquals(0x8b69ca, registered.getFoliageColor(), "foliage override did not survive registration");
    }

    @Test
    void registerLeavesTheRegistryFrozen() {
        builder("test:refreeze_biome").register();

        assertFrozen("register() left the biome registry unfrozen, so anything downstream could still write to it");
    }

    @Test
    void registerAcceptsABiomeCarryingGenerationSettings() {
        builder("test:generated_biome")
                .generationSettings(BiomeGenerationSettings.builder()
                        .feature(Decoration.VEGETAL_DECORATION, PlacedFeature.builder()
                                .feature(ConfiguredFeature.of(FeatureType.NO_OP, NoneFeatureConfiguration.INSTANCE))
                                .modifier(PlacementModifier.rarityFilter(1))
                                .modifier(PlacementModifier.inSquare())
                                .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                                .modifier(PlacementModifier.biomeFilter())
                                .build())
                        .build())
                .register();

        net.minecraft.world.level.biome.Biome registered = biomes().getValue(Identifier.parse("test:generated_biome"));
        assertNotNull(registered, "biome with generation settings never reached the live registry");
        assertTrue(registered.getGenerationSettings().features().stream().anyMatch(step -> step.size() > 0),
                "generation settings came back empty, so the feature was dropped on the way in");
    }

    @Test
    void registerKeepsVanillaBiomesIntact() {
        int before = biomes().size();
        assertNotNull(biomes().getValue(Identifier.parse("minecraft:plains")));

        builder("test:coexist_biome").register();

        assertEquals(before + 1, biomes().size());
        assertNotNull(biomes().getValue(Identifier.parse("minecraft:plains")), "register() dropped a vanilla biome");
    }
}
