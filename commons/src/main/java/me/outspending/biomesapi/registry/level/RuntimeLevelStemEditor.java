package me.outspending.biomesapi.registry.level;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.worldgen.TransientMultiNoiseBiomeSource;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A runtime implementation of {@link LevelStemEditor} that can be used to edit biomes in a
 * {@link World} at runtime.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class RuntimeLevelStemEditor implements LevelStemEditor {

    private static final Field BIOME_SOURCE_FIELD;
    private static final Field FEATURES_PER_STEP_FIELD;
    private static final Field BIOME_GENERATION_SETTINGS_FIELD;
    private static final Method MULTI_NOISE_PARAMETERS;
    private static final Field MULTI_NOISE_PARAMETERS_FIELD;

    static {
        // TODO: Clean this up
        try {
            BIOME_SOURCE_FIELD = ChunkGenerator.class.getDeclaredField("biomeSource");
            BIOME_SOURCE_FIELD.setAccessible(true);

            FEATURES_PER_STEP_FIELD = findFeaturesPerStepField();
            FEATURES_PER_STEP_FIELD.setAccessible(true);

            BIOME_GENERATION_SETTINGS_FIELD = Biome.class.getDeclaredField("generationSettings");
            BIOME_GENERATION_SETTINGS_FIELD.setAccessible(true);

            MULTI_NOISE_PARAMETERS = MultiNoiseBiomeSource.class.getDeclaredMethod("parameters");
            MULTI_NOISE_PARAMETERS.setAccessible(true);

            MULTI_NOISE_PARAMETERS_FIELD = MultiNoiseBiomeSource.class.getDeclaredField("parameters");
            MULTI_NOISE_PARAMETERS_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final List<LevelBiomeEdit> edits = new ArrayList<>();
    private final Map<ChunkGenerator, Snapshot> snapshots = new IdentityHashMap<>();



    @Override
    @AsOf("2.3.0")
    public LevelStemEditor addToDimension(ResourceKey dimension, ResourceKey biome, ClimatePoint point) {
        this.edits.add(new LevelBiomeEdit.Add(dimension, biome, point));
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public LevelStemEditor replaceInDimension(ResourceKey dimension, ResourceKey target, ResourceKey replacement) {
        this.edits.add(new LevelBiomeEdit.Replace(dimension, target, replacement));
        return this;
    }


    @Override
    @AsOf("2.3.0")
    public void apply() {
        for (World world : Bukkit.getWorlds()) {
            apply(world);
        }
    }

    @Override
    @AsOf("2.3.0")
    public void apply(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        Identifier dimId = level.dimension().identifier();

        List<LevelBiomeEdit> matching = new ArrayList<>();
        for (LevelBiomeEdit edit : this.edits) {
            ResourceKey d = edit.dimension();
            if (d.namespace().equals(dimId.getNamespace()) && d.path().equals(dimId.getPath())) {
                matching.add(edit);
            }
        }
        if (matching.isEmpty()) {
            return;
        }

        ChunkGenerator generator = level.getChunkSource().getGenerator();
        Registry<Biome> biomes = level.registryAccess().lookupOrThrow(Registries.BIOME);
        BiomeSource current = generator.getBiomeSource();


        List<LevelBiomeEdit.Add> adds = new ArrayList<>();
        Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements = new LinkedHashMap<>();
        for (LevelBiomeEdit edit : matching) {
            if (edit instanceof LevelBiomeEdit.Add add) {
                adds.add(add);
            } else if (edit instanceof LevelBiomeEdit.Replace replace) {
                replacements.put(nmsBiomeKey(replace.target()), biomes.getOrThrow(nmsBiomeKey(replace.replacement())));
            }
        }


        if (!adds.isEmpty() && !(current instanceof MultiNoiseBiomeSource)) {
            return;
        }


        BiomeSource edited = buildEditedSource(current, biomes, adds, replacements, dimId);
        reorderIntroducedBiomes(biomes, adds, replacements);

        if (!this.snapshots.containsKey(generator)) {
            this.snapshots.put(generator, new Snapshot(current, readFeaturesPerStep(generator)));
        }

        setBiomeSource(generator, edited);
        refreshFeaturesPerStep(generator, edited);
    }


    private static BiomeSource buildEditedSource(BiomeSource current, Registry<Biome> biomes, List<LevelBiomeEdit.Add> adds, Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements, Identifier dimId) {
        if (current instanceof MultiNoiseBiomeSource multiNoise) {
            return buildMultiNoise(multiNoise, biomes, adds, replacements);
        }
        if (current instanceof TheEndBiomeSource) {
            return buildEnd(biomes, replacements);
        }
        throw new IllegalStateException("cannot edit dimension " + dimId + ", unsupported biome source type " + current.getClass().getSimpleName() + ", only multi_noise and the_end are supported");
    }


    private static BiomeSource buildMultiNoise(MultiNoiseBiomeSource multiNoise, Registry<Biome> biomes, List<LevelBiomeEdit.Add> adds, Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements) {
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> entries = new ArrayList<>(liveParameters(multiNoise).values());

        // replaces swap the holder at every entry whose biome matches a target, climatePoint is preserved
        for (int i = 0; i < entries.size(); i++) {
            Holder<Biome> holder = entries.get(i).getSecond();
            net.minecraft.resources.ResourceKey<Biome> key = holder.unwrapKey().orElse(null);
            if (key != null) {
                Holder<Biome> replacement = replacements.get(key);
                if (replacement != null) {
                    entries.set(i, Pair.of(entries.get(i).getFirst(), replacement));
                }
            }
        }

        // adds append the new biome at its explicit climate climatePoint
        for (LevelBiomeEdit.Add add : adds) {
            Holder<Biome> holder = biomes.getOrThrow(nmsBiomeKey(add.biome()));
            Climate.ParameterPoint point = (Climate.ParameterPoint) add.point().toMinecraft();
            entries.add(Pair.of(point, holder));
        }

        Climate.ParameterList<Holder<Biome>> editedList = new Climate.ParameterList<>(entries);


        Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> codecSide = liveEither(multiNoise);
        return TransientMultiNoiseBiomeSource.create(codecSide, editedList);
    }


    private static BiomeSource buildEnd(Registry<Biome> biomes, Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements) {
        HolderGetter<Biome> substituting = new SubstitutingHolderGetter(biomes, replacements);
        return TheEndBiomeSource.create(substituting);
    }

    private static Object readFeaturesPerStep(ChunkGenerator generator) {
        try {
            return FEATURES_PER_STEP_FIELD.get(generator);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("failed to read featuresPerStep for snapshot", e);
        }
    }

    private static void reorderIntroducedBiomes(
        Registry<Biome> biomeRegistry,
        List<LevelBiomeEdit.Add> adds,
        Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements
    ) {
        Set<Biome> introduced = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
        for (LevelBiomeEdit.Add add : adds) {
            introduced.add(biomeRegistry.getOrThrow(nmsBiomeKey(add.biome())).value());
        }
        for (Holder<Biome> replacement : replacements.values()) {
            introduced.add(replacement.value());
        }

        for (Biome biome : introduced) {
            BiomeGenerationSettings reordered = FeatureOrderResolver.reorder(biomeRegistry, biome, introduced);
            setGenerationSettings(biome, reordered);
        }
    }

    private static void setGenerationSettings(Biome biome, BiomeGenerationSettings settings) {
        try {
            BIOME_GENERATION_SETTINGS_FIELD.set(biome, settings);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("failed to write reordered generation settings onto biome", e);
        }
    }

    private static void setBiomeSource(ChunkGenerator generator, BiomeSource source) {
        try {
            BIOME_SOURCE_FIELD.set(generator, source);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("failed to set biomeSource on generator", e);
        }
    }

    private static void refreshFeaturesPerStep(ChunkGenerator generator, BiomeSource source) {
        Function<Holder<Biome>, BiomeGenerationSettings> getter = generator.generationSettingsGetter;
        Supplier<List<FeatureSorter.StepFeatureData>> fresh = Suppliers.memoize(() ->
            FeatureSorter.buildFeaturesPerStep(
                List.copyOf(source.possibleBiomes()), biome -> getter.apply(biome).features(), true)
        );
        try {
            FEATURES_PER_STEP_FIELD.set(generator, fresh);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("failed to refresh featuresPerStep on generator", e);
        }
    }

    private static Field findFeaturesPerStepField() {
        for (Field f : ChunkGenerator.class.getDeclaredFields()) {
            if (Supplier.class.isAssignableFrom(f.getType())) {
                return f;
            }
        }
        throw new IllegalStateException("no Supplier field on ChunkGenerator for featuresPerStep");
    }

    @SuppressWarnings("unchecked")
    private static Climate.ParameterList<Holder<Biome>> liveParameters(MultiNoiseBiomeSource source) {
        try {
            return (Climate.ParameterList<Holder<Biome>>) MULTI_NOISE_PARAMETERS.invoke(source);
        } catch (Exception e) {
            throw new IllegalStateException("failed to read multi_noise parameters via reflection", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> liveEither(MultiNoiseBiomeSource source) {
        try {
            return (Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>>) MULTI_NOISE_PARAMETERS_FIELD.get(source);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("failed to read multi_noise parameters Either via reflection", e);
        }
    }

    private static net.minecraft.resources.ResourceKey<Biome> nmsBiomeKey(ResourceKey key) {
        // FIXME: Use resourceLocation()
        return net.minecraft.resources.ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(key.namespace(), key.path()));
    }


    private record Snapshot(BiomeSource originalSource, Object originalFeaturesPerStep) {
    }

    private record SubstitutingHolderGetter(HolderGetter<Biome> delegate, Map<net.minecraft.resources.ResourceKey<Biome>, Holder<Biome>> replacements) implements HolderGetter<Biome> {

        @Override
        public Optional<Holder.Reference<Biome>> get(net.minecraft.resources.ResourceKey<Biome> key) {
            return this.delegate.get(key);
        }

        @Override
        public Holder.Reference<Biome> getOrThrow(net.minecraft.resources.ResourceKey<Biome> key) {
            Holder<Biome> replacement = this.replacements.get(key);
            if (replacement != null) {
                if (replacement instanceof Holder.Reference<Biome> reference) {
                    return reference;
                }
                throw new IllegalStateException("end replacement for " + key.identifier() + " is not a registry reference holder, cannot substitute it into the end source");
            }
            return this.delegate.getOrThrow(key);
        }

        @Override
        public Optional<HolderSet.Named<Biome>> get(TagKey<Biome> tag) {
            return this.delegate.get(tag);
        }

        @Override
        public HolderSet.Named<Biome> getOrThrow(TagKey<Biome> tag) {
            return this.delegate.getOrThrow(tag);
        }
    }
}