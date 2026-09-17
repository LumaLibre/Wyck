package dev.wyck.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Cloner;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// TODO: Remove

@NullMarked
@ApiStatus.Internal
public final class DatapackPromotion {

    private static volatile Mode MODE = Mode.OFF;
    private static volatile @Nullable DatapackPromotion CURRENT;

    private final String namespace;
    private final Map<Object, ResourceKey<?>> wrapperKeys = new IdentityHashMap<>();
    private final Map<ResourceKey<Feature>, Feature> configuredFeatures = new LinkedHashMap<>();
    private final Map<ResourceKey<PlacedFeature>, PlacedFeature> placedFeatures = new LinkedHashMap<>();
    private final Map<ResourceKey<WorldCarver>, WorldCarver> carvers = new LinkedHashMap<>();
    private final Map<ResourceKey<PlacedFeature>, PlacedFileData> placedForFiles = new LinkedHashMap<>();
    private final Map<Feature, ResourceKey<Feature>> configuredFeatureKeys = new IdentityHashMap<>();
    private final Map<String, Integer> counters = new LinkedHashMap<>();

    private HolderLookup.@Nullable Provider provider;
    private HolderLookup.@Nullable Provider vanilla;

    private DatapackPromotion(String namespace) {
        this.namespace = namespace;
    }

    public static DatapackPromotion beginCollect(String namespace) {
        DatapackPromotion promotion = new DatapackPromotion(namespace);
        CURRENT = promotion;
        MODE = Mode.COLLECT;
        return promotion;
    }

    public void beginReference() {
        MODE = Mode.REFERENCE;
    }

    public static void end() {
        MODE = Mode.OFF;
        CURRENT = null;
    }

    public static boolean isCollectMode() {
        return MODE == Mode.COLLECT;
    }

    public static boolean isReferenceMode() {
        return MODE == Mode.REFERENCE;
    }

    public static DatapackPromotion current() {
        DatapackPromotion promotion = CURRENT;
        if (promotion == null) {
            throw new IllegalStateException("no active datapack promotion");
        }
        return promotion;
    }

    public HolderLookup.Provider provider() {
        if (this.provider == null) {
            throw new IllegalStateException("provider not built yet");
        }
        return this.provider;
    }


    @SuppressWarnings("unchecked")
    public void collectConfiguredFeature(Object wrapper, Feature value) {
        ResourceKey<Feature> key = (ResourceKey<Feature>) this.wrapperKeys.computeIfAbsent(
            wrapper, w -> nextKey(Registries.FEATURE, "configured_feature"));
        this.configuredFeatures.putIfAbsent(key, value);
        this.configuredFeatureKeys.putIfAbsent(value, key);
    }

    @SuppressWarnings("unchecked")
    public void collectPlacedFeature(Object wrapper, PlacedFeature value) {
        ResourceKey<PlacedFeature> key = (ResourceKey<PlacedFeature>) this.wrapperKeys.computeIfAbsent(
            wrapper, w -> nextKey(Registries.PLACED_FEATURE, "placed_feature"));
        this.placedFeatures.putIfAbsent(key, value);
    }

    @SuppressWarnings("unchecked")
    public void collectCarver(Object wrapper, WorldCarver value) {
        ResourceKey<WorldCarver> key = (ResourceKey<WorldCarver>) this.wrapperKeys.computeIfAbsent(
            wrapper, w -> nextKey(Registries.CARVER, "configured_carver"));
        this.carvers.putIfAbsent(key, value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object reference(Object wrapper, ResourceKey registry) {
        ResourceKey key = this.wrapperKeys.get(wrapper);
        if (key == null) {
            throw new IllegalStateException("custom worldgen object was not collected: " + wrapper);
        }
        return provider().lookupOrThrow(registry).getOrThrow(key);
    }

    public void buildProvider() {
        HolderLookup.Provider vanillaProvider = VanillaRegistries.createWorldLookup();
        this.vanilla = vanillaProvider;
        HolderGetter<Feature> vanillaFeatures = vanillaProvider.lookupOrThrow(Registries.FEATURE);

        RegistrySetBuilder builder = new RegistrySetBuilder();

        builder.add(Registries.FEATURE, context -> {
            for (Map.Entry<ResourceKey<Feature>, Feature> entry : this.configuredFeatures.entrySet()) {
                context.register(entry.getKey(), entry.getValue());
            }
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            HolderGetter<Feature> customFeatures = context.lookup(Registries.FEATURE);
            for (Map.Entry<ResourceKey<PlacedFeature>, PlacedFeature> entry : this.placedFeatures.entrySet()) {
                PlacedFeature original = entry.getValue();
                Holder<Feature> patchFeature = rewireFeature(original.feature(), customFeatures, vanillaFeatures);

                context.register(entry.getKey(), new PlacedFeature(patchFeature, original.placement()));
                this.placedForFiles.put(entry.getKey(), new PlacedFileData(configuredFeatureId(original.feature()), original.placement()));
            }
        });

        builder.add(Registries.CARVER, context -> {
            for (Map.Entry<ResourceKey<WorldCarver>, WorldCarver> entry : this.carvers.entrySet()) {
                context.register(entry.getKey(), entry.getValue());
            }
        });

        Cloner.Factory clonerFactory = new Cloner.Factory();
        for (RegistryDataLoader.RegistryData<?> data : RegistryDataLoader.WORLD_REGISTRIES) {
            data.runWithArguments(clonerFactory::addCodec);
        }

        RegistrySetBuilder.PatchedRegistries patched = builder.buildPatch(
            RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY),
            vanillaProvider,
            clonerFactory
        );
        this.provider = patched.full();
    }

    private Holder<Feature> rewireFeature(Holder<Feature> feature, HolderGetter<Feature> customFeatures, HolderGetter<Feature> vanillaFeatures) {
        if (feature.kind() == Holder.Kind.DIRECT) {
            ResourceKey<Feature> key = this.configuredFeatureKeys.get(feature.value());
            if (key == null) {
                throw new IllegalStateException("placed feature referenced an uncollected custom configured feature");
            }
            return customFeatures.getOrThrow(key);
        }
        ResourceKey<Feature> key = feature.unwrapKey()
            .orElseThrow(() -> new IllegalStateException("placed feature configured holder had no key"));
        return vanillaFeatures.getOrThrow(key);
    }

    private String configuredFeatureId(Holder<Feature> feature) {
        if (feature.kind() == Holder.Kind.DIRECT) {
            ResourceKey<Feature> key = this.configuredFeatureKeys.get(feature.value());
            if (key == null) {
                throw new IllegalStateException("placed feature referenced an uncollected custom configured feature");
            }
            return key.identifier().toString();
        }
        ResourceKey<Feature> key = feature.unwrapKey()
            .orElseThrow(() -> new IllegalStateException("placed feature configured holder had no key"));
        return key.identifier().toString();
    }


    public void writeFiles(Path root, Gson gson) throws Exception {
        if (this.vanilla == null) {
            throw new IllegalStateException("provider not built yet");
        }
        // let the provider mint the ops so the owner matches its own lookups exactly
        RegistryOps<JsonElement> ops = this.vanilla.createSerializationContext(JsonOps.INSTANCE);

        for (Map.Entry<ResourceKey<Feature>, Feature> entry : this.configuredFeatures.entrySet()) {
            writeJson(root, "worldgen/feature", entry.getKey(),
                encode(Feature.DIRECT_CODEC, ops, entry.getValue()), gson);
        }

        for (Map.Entry<ResourceKey<PlacedFeature>, PlacedFileData> entry : this.placedForFiles.entrySet()) {
            PlacedFileData data = entry.getValue();

            // placement modifiers are leaves with no registry refs, plain ops is enough
            DataResult<JsonElement> placement = net.minecraft.world.level.levelgen.placement.PlacementModifier.CODEC
                .listOf().encodeStart(JsonOps.INSTANCE, data.placement());
            JsonElement placementJson = placement.getOrThrow(message -> new IllegalStateException("placement encode failed: " + message));

            JsonObject json = new JsonObject();
            json.addProperty("feature", data.featureId());
            json.add("placement", placementJson);

            writeJson(root, "worldgen/placed_feature", entry.getKey(), json, gson);
        }

        for (Map.Entry<ResourceKey<WorldCarver>, WorldCarver> entry : this.carvers.entrySet()) {
            writeJson(root, "worldgen/carver", entry.getKey(),
                encode(WorldCarver.DIRECT_CODEC, ops, entry.getValue()), gson);
        }
    }

    private static <T> JsonElement encode(com.mojang.serialization.Codec<T> codec, RegistryOps<JsonElement> ops, T value) {
        DataResult<JsonElement> result = codec.encodeStart(ops, value);
        return result.getOrThrow(message -> new IllegalStateException("worldgen encode failed: " + message));
    }

    private static void writeJson(Path root, String dir, ResourceKey<?> key, JsonElement json, Gson gson) throws Exception {
        Identifier id = key.identifier();
        Path file = root.resolve("data")
            .resolve(id.getNamespace())
            .resolve(dir)
            .resolve(id.getPath() + ".json");
        Files.createDirectories(file.getParent());
        Files.writeString(file, gson.toJson(json));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private ResourceKey nextKey(ResourceKey registry, String suffix) {
        int n = this.counters.merge(suffix, 1, Integer::sum) - 1;
        Identifier id = Identifier.fromNamespaceAndPath(this.namespace, "promoted_" + suffix + "_" + n);
        return ResourceKey.create(registry, id);
    }

    private record PlacedFileData(String featureId, List<net.minecraft.world.level.levelgen.placement.PlacementModifier> placement) {
    }

    public enum Mode {
        OFF,
        COLLECT,
        REFERENCE
    }
}