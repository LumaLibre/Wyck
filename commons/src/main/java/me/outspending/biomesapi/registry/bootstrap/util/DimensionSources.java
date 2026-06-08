package me.outspending.biomesapi.registry.bootstrap.util;

import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.dimension.DimensionBiomeEdit;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Reconstructs the vanilla multi_noise biome list for a dimension at datapack discovery time and
 * applies a set of {@link DimensionBiomeEdit}s to it. Works without a populated biome registry
 * because it operates on {@code ResourceKey<Biome>} ids, not holders, and the vanilla climate
 * points are defined.
 *
 * <p>
 * Only dimensions with a defined preset source can be reconstructed here. The overworld and
 * nether qualify. Custom dimensions defined by other datapacks cannot be known at discovery and
 * must use {@link me.outspending.biomesapi.registry.dimension.RuntimeDimensionEditor}.
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class DimensionSources {

    private DimensionSources() {
    }

    public static DimensionDefaults defaultsFor(BiomeResourceKey dimension) {
        String id = idOf(dimension);
        return switch (id) {
            case "minecraft:overworld" -> new DimensionDefaults("minecraft:overworld", "minecraft:overworld");
            case "minecraft:the_nether" -> new DimensionDefaults("minecraft:the_nether", "minecraft:nether");
            default -> throw new UnsupportedOperationException("datapack dimension editing has no known defaults for " + id + ", use the unsafe runtime impl for custom or non multi_noise dimensions");
        };
    }


    public static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> vanillaPairs(BiomeResourceKey dimension) {
        String id = idOf(dimension);
        return switch (id) {
            case "minecraft:overworld" -> overworldPairs();
            case "minecraft:the_nether" -> netherPairs();
            default -> throw new UnsupportedOperationException("datapack dimension editing is only implemented for minecraft:overworld and minecraft:the_nether, got " + id + ", use the unsafe runtime impl for custom or non multi_noise dimensions");
        };
    }


    private static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> overworldPairs() {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pairs = new ArrayList<>();
        try {
            OverworldBiomeBuilder builder = new OverworldBiomeBuilder();
            Method addBiomes = OverworldBiomeBuilder.class.getDeclaredMethod("addBiomes", Consumer.class);
            addBiomes.setAccessible(true);
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> sink = pairs::add;
            addBiomes.invoke(builder, sink);
        } catch (Exception e) {
            throw new IllegalStateException("failed to enumerate overworld biomes via OverworldBiomeBuilder", e);
        }
        return pairs;
    }

    @SuppressWarnings("unchecked")
    private static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> netherPairs() {
        try {
            Object preset = MultiNoiseBiomeSourceParameterList.Preset.NETHER;
            Object provider = preset.getClass().getMethod("provider").invoke(preset);

            Method apply = provider.getClass().getMethod("apply", Function.class);
            apply.setAccessible(true);
            Climate.ParameterList<ResourceKey<Biome>> list =
                (Climate.ParameterList<ResourceKey<Biome>>) apply.invoke(provider, Function.identity());

            return new ArrayList<>(list.values());
        } catch (Exception e) {
            throw new IllegalStateException("failed to enumerate nether biomes via multi_noise preset", e);
        }
    }

    // apply replace then add over a key based pair list
    public static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> applyEdits(
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> base,
        List<DimensionBiomeEdit> edits
    ) {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pairs = new ArrayList<>(base);

        // replacements keep the original point, just swap the key
        for (DimensionBiomeEdit edit : edits) {
            if (edit instanceof DimensionBiomeEdit.Replace replace) {
                ResourceKey<Biome> target = nmsBiomeKey(replace.target());
                ResourceKey<Biome> replacement = nmsBiomeKey(replace.replacement());
                for (int i = 0; i < pairs.size(); i++) {
                    if (pairs.get(i).getSecond().equals(target)) {
                        pairs.set(i, Pair.of(pairs.get(i).getFirst(), replacement));
                    }
                }
            }
        }

        // additions append at their explicit point
        for (DimensionBiomeEdit edit : edits) {
            if (edit instanceof DimensionBiomeEdit.Add add) {
                Climate.ParameterPoint point = (Climate.ParameterPoint) add.point().toMinecraft();
                pairs.add(Pair.of(point, nmsBiomeKey(add.biome())));
            }
        }

        return pairs;
    }

    static ResourceKey<Biome> nmsBiomeKey(BiomeResourceKey key) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(key.namespace(), key.path()));
    }

    static String idOf(BiomeResourceKey key) {
        return key.namespace() + ":" + key.path();
    }

    public record DimensionDefaults(String dimensionType, String noiseSettings) {
    }
}