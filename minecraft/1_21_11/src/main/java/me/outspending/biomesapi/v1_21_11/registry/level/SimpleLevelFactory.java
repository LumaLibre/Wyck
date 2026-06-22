package me.outspending.biomesapi.v1_21_11.registry.level;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import io.papermc.paper.FeatureHooks;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.KeyChains;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.StemPersistence;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.registry.level.dimension.DimensionRegistry;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.level.spawner.LevelSpawner;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class SimpleLevelFactory implements LevelFactory {

    private final Lazy<FrozenRegistry> levelStemRegistry = FrozenRegistry.lazy("dimension");

    @Override
    public World createWorld(LevelCreator world) {
        Preconditions.checkNotNull(world, "world cannot be null");

        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer minecraftServer = server.getServer();

        Identifier typeId;

        if (world.getDimension() != null) {
            ResourceKey localKey = world.getDimension().getResourceKey();
            typeId = (Identifier) localKey.toMinecraft();
            if (!KeyChains.DIMENSIONS.isRegistered(localKey)) {
                DimensionRegistry.registry().register(world.getDimension());
            }
        } else {
            typeId = (Identifier) Preconditions.checkNotNull(world.getDimensionType(), "dimensionType").toMinecraft();
        }

        ResourceKey levelKey = world.getLevelKey();
        Identifier levelId = (Identifier) levelKey.toMinecraft();
        net.minecraft.resources.ResourceKey<LevelStem> stemKey = net.minecraft.resources.ResourceKey.create(Registries.LEVEL_STEM, levelId);
        net.minecraft.resources.ResourceKey<Level> dimensionKey = net.minecraft.resources.ResourceKey.create(Registries.DIMENSION, stemKey.identifier());

        Holder<DimensionType> typeHolder = minecraftServer.registryAccess()
            .lookupOrThrow(Registries.DIMENSION_TYPE)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.DIMENSION_TYPE, typeId));

        ChunkGenerator generator = (ChunkGenerator) world.getGenerator().toMinecraft();
        LevelStem stem = new LevelStem(typeHolder, generator);

        if (world.getPersistence() == StemPersistence.PERSISTENT) {
            registerStem(levelId, stem);
        }

        NamespacedKey bukkitKey = new NamespacedKey(levelKey.namespace(), levelKey.path());
        Preconditions.checkArgument(
            Bukkit.getWorld(bukkitKey) == null && Bukkit.getWorld(levelId.getPath()) == null,
            "world already loaded under %s", levelKey);

        PrimaryLevelData primaryLevelData = (PrimaryLevelData) minecraftServer.getWorldData();

        WorldLoader.DataLoadContext context = minecraftServer.worldLoaderContext;
        WorldOptions worldOptions = new WorldOptions(world.getSeed(), world.generateStructures(), world.bonusChest());

        DedicatedServerProperties.WorldDimensionData properties =
            new DedicatedServerProperties.WorldDimensionData(new JsonObject(), "normal");
        WorldDimensions baseDimensions = properties.create(context.datapackWorldgen());
        WorldDimensions worldDimensions = withStem(baseDimensions, stemKey, stem);

        WorldGenSettings genSettings = new WorldGenSettings(worldOptions, worldDimensions);
        long biomeZoomSeed = BiomeManager.obfuscateSeed(genSettings.options().seed());

        List<CustomSpawner> spawners = new ArrayList<>();
        for (LevelSpawner spawner : world.getSpawners()) {
            spawners.add((CustomSpawner) spawner.toMinecraft(primaryLevelData));
        }

        //noinspection DataFlowIssue
        ServerLevel serverLevel = new ServerLevel(
            minecraftServer,
            minecraftServer.executor,
            minecraftServer.storageSource,
            primaryLevelData,
            dimensionKey,
            stem,
            primaryLevelData.isDebugWorld(),
            biomeZoomSeed,
            spawners,
            true,
            null,
            world.getEnvironment(),
            null,
            null
        );

        minecraftServer.addLevel(serverLevel);
        minecraftServer.initWorld(serverLevel, serverLevel.serverLevelData, worldOptions);
        serverLevel.setSpawnSettings(true);
        FeatureHooks.tickEntityManager(serverLevel);
        minecraftServer.prepareLevel(serverLevel);

        return serverLevel.getWorld();
    }

    private static WorldDimensions withStem(WorldDimensions base, net.minecraft.resources.ResourceKey<LevelStem> key, LevelStem stem) {
        var builder = ImmutableMap.<net.minecraft.resources.ResourceKey<LevelStem>, LevelStem>builder();
        builder.putAll(base.dimensions());
        builder.put(key, stem);
        return new WorldDimensions(builder.buildKeepingLast());
    }

    @SuppressWarnings("unchecked")
    private void registerStem(Identifier id, LevelStem stem) {
        levelStemRegistry.get().whileUnfrozen(r -> {
            Registry<LevelStem> registry = (Registry<@NonNull LevelStem>) r.toMinecraft();
            if (!registry.containsKey(id)) {
                Registry.register(registry, id, stem);
            }
        });
    }
}