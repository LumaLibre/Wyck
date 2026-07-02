package dev.wyck.registry.level;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import io.papermc.paper.FeatureHooks;
import io.papermc.paper.world.PaperWorldLoader;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.KeyChains;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.StemPersistence;
import dev.wyck.registry.internal.FrozenRegistry;
import dev.wyck.registry.level.dimension.DimensionRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.util.internal.InternalReflectUtil;
import dev.wyck.wrapper.level.spawner.LevelSpawner;
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
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.bukkit.Bukkit;
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

        Preconditions.checkArgument(server.getWorld(levelKey.namespace() + ":" + levelKey.path()) == null || server.getWorld(levelId.getPath()) == null, "world already loaded under %s", levelKey);

        String name = levelId.getPath();
        PaperWorldLoader.LoadedWorldData loadedWorldData = PaperWorldLoader.loadWorldData(minecraftServer, dimensionKey, name);
        PrimaryLevelData primaryLevelData = (PrimaryLevelData) minecraftServer.getWorldData();

        WorldLoader.DataLoadContext context = minecraftServer.worldLoaderContext;
        WorldOptions worldOptions = new WorldOptions(world.getSeed(), world.generateStructures(), world.bonusChest());

        DedicatedServerProperties.WorldDimensionData properties = new DedicatedServerProperties.WorldDimensionData(new JsonObject(), "normal");
        WorldDimensions baseDimensions = properties.create(context.datapackWorldgen());
        WorldDimensions worldDimensions = withStem(baseDimensions, stemKey, stem);

        Registry<LevelStem> stemRegistry = context.datapackDimensions().lookupOrThrow(Registries.LEVEL_STEM);
        WorldDimensions.Complete complete = worldDimensions.bake(stemRegistry); // TODO
        WorldGenSettings genSettings = new WorldGenSettings(worldOptions, worldDimensions);

        long biomeZoomSeed = BiomeManager.obfuscateSeed(genSettings.options().seed());

        SavedDataStorage savedDataStorage = new SavedDataStorage(
            minecraftServer.storageSource.getDimensionPath(dimensionKey).resolve(LevelResource.DATA.id()),
            minecraftServer.getFixerUpper(),
            minecraftServer.registryAccess()
        );
        savedDataStorage.set(WorldGenSettings.TYPE, genSettings);

        List<CustomSpawner> spawners = new ArrayList<>();
        for (LevelSpawner spawner : world.getSpawners()) {
            spawners.add((CustomSpawner) spawner.toMinecraft(savedDataStorage));
        }

        //noinspection DataFlowIssue
        ServerLevel serverLevel = new ServerLevel(
            minecraftServer,
            InternalReflectUtil.getFieldValue(minecraftServer, "executor"),
            minecraftServer.storageSource,
            genSettings,
            dimensionKey,
            stem,
            primaryLevelData.isDebugWorld(),
            biomeZoomSeed,
            spawners,
            true,
            stemKey,
            world.getEnvironment(),
            null,
            null, // nullable here and up there too ^
            savedDataStorage,
            loadedWorldData
        );

        minecraftServer.addLevel(serverLevel);
        minecraftServer.initWorld(serverLevel, null);
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