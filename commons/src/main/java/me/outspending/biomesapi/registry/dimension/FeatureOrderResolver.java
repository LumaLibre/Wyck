package me.outspending.biomesapi.registry.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
final class FeatureOrderResolver {

    private FeatureOrderResolver() {
    }

    static BiomeGenerationSettings reorder(Registry<Biome> biomeRegistry, Biome target, Set<Biome> exclude) {
        BiomeGenerationSettings settings = target.getGenerationSettings();
        List<HolderSet<PlacedFeature>> steps = settings.features();

        BiomeGenerationSettings.PlainBuilder builder = new BiomeGenerationSettings.PlainBuilder();
        copyCarvers(settings, builder);

        for (int step = 0; step < steps.size(); step++) {
            Map<PlacedFeature, Integer> rank = buildStepRank(biomeRegistry, exclude, step);

            List<Holder<PlacedFeature>> ordered = new ArrayList<>();
            for (Holder<PlacedFeature> holder : steps.get(step)) {
                ordered.add(holder);
            }
            // stable sort by global rank, unranked features sort last keeping their existing order
            ordered.sort(Comparator.comparingInt(holder -> rankOf(rank, holder)));

            for (Holder<PlacedFeature> holder : ordered) {
                builder.addFeature(step, holder);
            }
        }

        return builder.build();
    }

    private static Map<PlacedFeature, Integer> buildStepRank(Registry<Biome> biomeRegistry, Set<Biome> exclude, int step) {
        Map<PlacedFeature, Set<PlacedFeature>> edges = new IdentityHashMap<>();
        for (Biome biome : biomeRegistry) {
            if (exclude.contains(biome)) {
                continue;
            }
            List<HolderSet<PlacedFeature>> biomeSteps = biome.getGenerationSettings().features();
            if (step >= biomeSteps.size()) {
                continue;
            }
            List<PlacedFeature> inStep = new ArrayList<>();
            for (Holder<PlacedFeature> holder : biomeSteps.get(step)) {
                inStep.add(holder.value());
            }
            for (int i = 0; i < inStep.size(); i++) {
                PlacedFeature feature = inStep.get(i);
                edges.computeIfAbsent(feature, k -> newIdentitySet());
                if (i < inStep.size() - 1) {
                    edges.get(feature).add(inStep.get(i + 1));
                }
            }
        }
        return topologicalRank(edges);
    }


    private static Map<PlacedFeature, Integer> buildGlobalRank(Registry<Biome> biomeRegistry, Set<Biome> exclude) {
        Map<PlacedFeature, Set<PlacedFeature>> edges = new IdentityHashMap<>();

        for (Biome biome : biomeRegistry) {
            if (exclude.contains(biome)) {
                continue;
            }
            addBiomeEdges(biome, edges);
        }

        return topologicalRank(edges);
    }

    private static void addBiomeEdges(Biome biome, Map<PlacedFeature, Set<PlacedFeature>> edges) {
        List<PlacedFeature> concat = new ArrayList<>();
        for (HolderSet<PlacedFeature> stepSet : biome.getGenerationSettings().features()) {
            for (Holder<PlacedFeature> holder : stepSet) {
                concat.add(holder.value());
            }
        }

        for (int i = 0; i < concat.size(); i++) {
            PlacedFeature feature = concat.get(i);
            edges.computeIfAbsent(feature, k -> newIdentitySet());
            if (i < concat.size() - 1) {
                edges.get(feature).add(concat.get(i + 1));
            }
        }
    }

    private static Map<PlacedFeature, Integer> topologicalRank(Map<PlacedFeature, Set<PlacedFeature>> edges) {
        // in degree per node
        Map<PlacedFeature, Integer> inDegree = new IdentityHashMap<>();
        for (PlacedFeature node : edges.keySet()) {
            inDegree.putIfAbsent(node, 0);
        }
        for (Set<PlacedFeature> successors : edges.values()) {
            for (PlacedFeature successor : successors) {
                inDegree.merge(successor, 1, Integer::sum);
                edges.computeIfAbsent(successor, k -> newIdentitySet());
            }
        }

        Deque<PlacedFeature> ready = new ArrayDeque<>();
        for (Map.Entry<PlacedFeature, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                ready.add(entry.getKey());
            }
        }

        Map<PlacedFeature, Integer> rank = new IdentityHashMap<>();
        int next = 0;
        while (!ready.isEmpty()) {
            PlacedFeature node = ready.poll();
            rank.put(node, next++);
            for (PlacedFeature successor : edges.getOrDefault(node, Set.of())) {
                int remaining = inDegree.merge(successor, -1, Integer::sum);
                if (remaining == 0) {
                    ready.add(successor);
                }
            }
        }

        return rank;
    }

    private static int rankOf(Map<PlacedFeature, Integer> rank, Holder<PlacedFeature> holder) {
        Integer r = rank.get(holder.value());
        return r != null ? r : Integer.MAX_VALUE;
    }

    private static void copyCarvers(BiomeGenerationSettings settings, BiomeGenerationSettings.PlainBuilder builder) {
        for (Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> carver : settings.getCarvers()) {
            builder.addCarver(carver);
        }
    }

    private static Set<PlacedFeature> newIdentitySet() {
        return Collections.newSetFromMap(new IdentityHashMap<>());
    }
}