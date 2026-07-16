package dev.wyck.registry.level;

import dev.wyck.level.dimension.Dimension;
import dev.wyck.util.internal.InternalReflectUtil;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.generator.CustomChunkGenerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class RuntimeLevelStemEditor implements LevelStemEditor {

    private static final Field DIMENSION_TYPE_REGISTRATION_FIELD = InternalReflectUtil.field(Level.class, "dimensionTypeRegistration");
    private static final Field WORLD_GEN_CONTEXT_FIELD = InternalReflectUtil.field(ChunkMap.class, "worldGenContext");
    private static final Field RANDOM_STATE_FIELD = InternalReflectUtil.field(ChunkMap.class, "randomState");
    private static final Field CHUNK_GEN_STATE_FIELD = InternalReflectUtil.field(ChunkMap.class, "chunkGeneratorState");

    private @Nullable ChunkGenerator generatorEdit;
    private @Nullable Dimension dimensionTypeEdit;
    private final Map<Level, Holder<DimensionType>> dimensionTypeSnapshots = new IdentityHashMap<>();
    private final Map<ChunkMap, GeneratorSnapshot> generatorSnapshots = new IdentityHashMap<>();


    @Override
    public LevelStemEditor chunkGenerator(ChunkGenerator generator) {
        this.generatorEdit = generator;
        return this;
    }

    @Override
    public LevelStemEditor dimension(Dimension dimensionType) {
        this.dimensionTypeEdit = dimensionType;
        return this;
    }


    @Override
    public LevelStemEditor apply(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        Identifier dimId = level.dimension().identifier();

        applyDimensionType(level, dimId);
        applyChunkGenerator(level, dimId);
        return this;
    }

    @Override
    public LevelStemEditor restore(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        ChunkMap chunkMap = level.getChunkSource().chunkMap;

        Holder<DimensionType> dimSnapshot = this.dimensionTypeSnapshots.remove(level);
        GeneratorSnapshot genSnapshot = this.generatorSnapshots.remove(chunkMap);

        if (dimSnapshot == null && genSnapshot == null) {
            throw new IllegalStateException("cannot restore " + level.dimension().identifier() + ". This editor never edited this world?");
        }

        if (dimSnapshot != null) {
            InternalReflectUtil.set(DIMENSION_TYPE_REGISTRATION_FIELD, level, dimSnapshot);
        }

        if (genSnapshot != null) {
            InternalReflectUtil.set(WORLD_GEN_CONTEXT_FIELD, chunkMap, genSnapshot.originalContext());
            InternalReflectUtil.set(RANDOM_STATE_FIELD, chunkMap, genSnapshot.originalRandomState());
            InternalReflectUtil.set(CHUNK_GEN_STATE_FIELD, chunkMap, genSnapshot.originalGeneratorState());
        }
        return this;
    }


    private void applyDimensionType(ServerLevel level, Identifier dimId) {
        Dimension edit = this.dimensionTypeEdit;
        if (edit == null) {
            return;
        }

        Registry<DimensionType> registry = level.registryAccess().lookupOrThrow(Registries.DIMENSION_TYPE);
        Holder<DimensionType> newHolder = registry.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.DIMENSION_TYPE, edit.resourceKey().identifier()));
        Holder<DimensionType> oldHolder = level.dimensionTypeRegistration();

        DimensionType oldType = oldHolder.value();
        DimensionType newType = newHolder.value();

        // TODO: figure out what to do about starlight
        //if (oldType.minY() != newType.minY() || oldType.height() != newType.height() || oldType.logicalHeight() != newType.logicalHeight()) {
        //    throw IllegalDimensionGeometry.dimensionType(dimId.toString(), oldType.minY(), newType.minY(), oldType.height(), newType.height(), oldType.logicalHeight(), newType.logicalHeight());
        //}

        this.dimensionTypeSnapshots.putIfAbsent(level, oldHolder);

        InternalReflectUtil.set(DIMENSION_TYPE_REGISTRATION_FIELD, level, newHolder);
    }


    private void applyChunkGenerator(ServerLevel level, Identifier dimId) {
        ChunkGenerator edit = this.generatorEdit;
        if (edit == null) {
            return;
        }

        net.minecraft.world.level.chunk.ChunkGenerator newGenerator = edit.asHandle();

        // TODO: figure out what to do about starlight
        //int genMinY = newGenerator.getMinY();
        //int genHeight = newGenerator.getGenDepth();
        //if (genMinY != level.getMinY() || genHeight != level.getHeight()) {
        //    throw IllegalDimensionGeometry.chunkGenerator(dimId.toString(), level.getMinY(), genMinY, level.getHeight(), genHeight);
        //}

        ChunkMap chunkMap = level.getChunkSource().chunkMap;
        RegistryAccess registries = level.registryAccess();
        long seed = level.getSeed();

        net.minecraft.world.level.chunk.ChunkGenerator randomGenerator = newGenerator;
        if (randomGenerator instanceof CustomChunkGenerator custom) {
            randomGenerator = custom.getDelegate();
        }

        final RandomState randomState;
        if (randomGenerator instanceof NoiseBasedChunkGenerator noiseGen) {
            randomState = RandomState.create(
                noiseGen.generatorSettings().value(), registries.lookupOrThrow(Registries.NOISE), seed);
        } else {
            randomState = RandomState.create(
                NoiseGeneratorSettings.dummy(), registries.lookupOrThrow(Registries.NOISE), seed);
        }

        ChunkGeneratorStructureState genState = newGenerator.createState(
            registries.lookupOrThrow(Registries.STRUCTURE_SET), randomState, seed, level.spigotConfig);

        WorldGenContext oldCtx = chunkMap.worldGenContext;

        this.generatorSnapshots.putIfAbsent(chunkMap, new GeneratorSnapshot(
            oldCtx,
            InternalReflectUtil.get(RANDOM_STATE_FIELD, chunkMap),
            InternalReflectUtil.get(CHUNK_GEN_STATE_FIELD, chunkMap)
        ));

        WorldGenContext newCtx = new WorldGenContext(
            oldCtx.level(),
            newGenerator,
            oldCtx.structureManager(),
            oldCtx.lightEngine(),
            oldCtx.mainThreadExecutor(),
            oldCtx.unsavedListener()
        );

        InternalReflectUtil.set(WORLD_GEN_CONTEXT_FIELD, chunkMap, newCtx);
        InternalReflectUtil.set(RANDOM_STATE_FIELD, chunkMap, randomState);
        InternalReflectUtil.set(CHUNK_GEN_STATE_FIELD, chunkMap, genState);
    }


    private record GeneratorSnapshot(WorldGenContext originalContext, RandomState originalRandomState, ChunkGeneratorStructureState originalGeneratorState) {}
}