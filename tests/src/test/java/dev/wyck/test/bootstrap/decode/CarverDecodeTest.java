package dev.wyck.test.bootstrap.decode;

import dev.wyck.decode.Decoders;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.carver.WorldCarver;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class CarverDecodeTest {

    @Test
    void everyWorldCarverTypeHasAConfigurationDecoder() {
        var decoders = new dev.wyck.decode.worldgen.carver.CarverConfigurationDecoders();
        BuiltInRegistries.CARVER_TYPE.keySet().forEach(key -> assertTrue(
            decoders.handles(Decoders.key(key)), () -> "Missing carver decoder for " + key
        ));
        assertEquals(BuiltInRegistries.CARVER_TYPE.size(), decoders.handled().size());
    }

    @Test
    void everyVanillaCarverDecodesInline() {
        BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.CARVER).entrySet().forEach(entry -> {
            net.minecraft.world.level.levelgen.carver.WorldCarver minecraftCarver = entry.getValue();
            ComposedCarver decoded = assertInstanceOf(
                ComposedCarver.class,
                WorldCarver.decode(Holder.direct(minecraftCarver))
            );

            assertEquals(
                Decoders.registryKey(BuiltInRegistries.CARVER_TYPE, minecraftCarver.codec()),
                decoded.type().resourceKey()
            );
            assertEquals(
                probability(minecraftCarver),
                decoded.config().probability()
            );
        });
    }

    private static float probability(net.minecraft.world.level.levelgen.carver.WorldCarver carver) {
        if (carver instanceof net.minecraft.world.level.levelgen.carver.CaveWorldCarver cave) {
            return cave.probability();
        }
        return ((net.minecraft.world.level.levelgen.carver.CanyonWorldCarver) carver).probability();
    }
}
