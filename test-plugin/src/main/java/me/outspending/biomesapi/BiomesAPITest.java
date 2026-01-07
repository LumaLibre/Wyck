package me.outspending.biomesapi;

import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.event.connection.configuration.PlayerConnectionReconfigureEvent;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.setter.BiomeSetter;
import me.outspending.biomesapi.wrapper.environment.AmbientParticle;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BiomesAPITest extends JavaPlugin implements Listener {


    private PacketHandler packetHandler;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        int color = Integer.parseInt("4498DB", 16);
        int red = Integer.parseInt("EE0000", 16);

        BedRule bedRule = BedRule.builder()
                .setCanSetSpawn(BedRule.Rule.NEVER)
                .setExplodes(true)
                .setErrorMessage(Component.text("You cannot sleep here!"))
                .build();

        WrappedEnvironmentAttributeMap attributeMap = WrappedEnvironmentAttributeMap.builder()
                .setAttribute(WrappedEnvironmentAttributes.CLOUD_COLOR, red)
                .setAttribute(WrappedEnvironmentAttributes.SUNRISE_SUNSET_COLOR, color)
                .setAttribute(WrappedEnvironmentAttributes.BED_RULE, bedRule)
                .setAttribute(WrappedEnvironmentAttributes.FOG_COLOR, color)
                .setAttribute(WrappedEnvironmentAttributes.WATER_EVAPORATES, true)
                .setAttribute(WrappedEnvironmentAttributes.MONSTERS_BURN, false)
                .setAttribute(WrappedEnvironmentAttributes.FOG_START_DISTANCE, 1.0f)
                .setAttribute(WrappedEnvironmentAttributes.FAST_LAVA, true)
                .build();

        CustomBiome biome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                //.fogColor("#FFFFFF") // #db4929
                .foliageColor("#F5F2EB")
                .skyColor("#B99DFC")
                .waterColor("#F5F2EB") // #F5F2EB
                .waterFogColor("#000000")
                .grassColor("#9D00FF")
                .particleRenderer(ParticleRenderer.of(AmbientParticle.WITCH, 0.01f))
                .blockReplacements(
                        BlockReplacement.of(Material.GRASS_BLOCK, Material.WATER),
                        BlockReplacement.of(Material.STONE, Material.DIAMOND_BLOCK)
                )
                .environmentAttributeMap(attributeMap)
                .build();

        biome.register();


//        packetHandler = PacketHandler.of(this, PacketHandler.Manipulator.PACKETEVENTS);
//        packetHandler.register();
//
//
//        PhonyCustomBiome phonyCustomBiome = PhonyCustomBiome.builder()
//                .setCustomBiome(biome)
//                .build();
//
//        packetHandler.appendBiome(phonyCustomBiome);
    }

    @Override
    public void onDisable() {
        //packetHandler.unregister();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        CustomBiome customBiome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
        if (customBiome == null) {
            throw new IllegalStateException("Custom biome not found!");
        }

        Player player = e.getPlayer();

        BiomeSetter biomeSetter = BiomeSetter.of();
        biomeSetter.setChunkBiome(e.getBlock().getChunk(), customBiome, true);

    }
}