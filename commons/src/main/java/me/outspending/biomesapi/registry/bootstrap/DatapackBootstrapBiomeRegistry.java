package me.outspending.biomesapi.registry.bootstrap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.registrar.RegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.biome.RegisteredBiomes;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.util.ThrowingRunnable;
import net.minecraft.SharedConstants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of {@link BootstrapBiomeRegistry} that registers custom biomes through Paper's
 * supported datapack pipeline the only contract-backed way to enter the biome registry before it
 * freezes. No {@code PaperRegistries}, no {@code Unsafe}, no JVM flags, no hand-fighting the load
 * future for a registry handle (the tag/compose windows don't expose a writable biome registry).
 *
 * <p>Flow: collect biomes via {@link #queue}, then on the {@code DATAPACK_DISCOVERY} lifecycle
 * event (fires during load, before registries are built from packs) generate an in-memory datapack:
 * each queued biome is built to an NMS {@link Biome} via the existing programmatic builder, encoded
 * to JSON with the biome codec, and written to {@code data/<ns>/worldgen/biome/<name>.json}.
 *
 * @version 2.3.0
 * @since 2.3.0
 */
@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class DatapackBootstrapBiomeRegistry implements BootstrapBiomeRegistry {

    // TODO: Abstract this out to some interfaces to use elsewhere in API

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PACK_ID_FORMAT = "biomesapi_%s";

    private final List<CustomBiome> pending = new ArrayList<>();
    private boolean installed = false;
    private @Nullable String packId;
    private @Nullable Throwable deferred;

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry queue(CustomBiome biome) {
        this.pending.add(biome);
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry install(BootstrapContext context) {
        Preconditions.checkState(!this.installed, "already installed");
        this.installed = true;
        this.packId = String.format(PACK_ID_FORMAT, context.getPluginMeta().getName().toLowerCase(Locale.ROOT).replace(' ', '-'));

        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(this::discover)
        );
        return this;
    }

    @AsOf("2.3.0")
    public BootstrapBiomeRegistry deferring(ThrowingRunnable action) {
        if (this.deferred == null) {
            try {
                action.run();
            } catch (Throwable t) {
                if (this.deferred == null) {
                    this.deferred = t;
                } else {
                    this.deferred.addSuppressed(t);
                }
            }
        }
        return this;
    }

    /**
     * Generates the biome datapack and registers it with Paper. Rethrows on failure so datapack
     * load aborts and the server refuses to start.
     */
    private void discover(RegistrarEvent<DatapackRegistrar> event) {
        if (this.deferred != null) {
            throw new RuntimeException("Deferred custom biome failure; aborting startup", this.deferred);
        }
        if (this.pending.isEmpty()) {
            return;
        }
        try {
            Preconditions.checkNotNull(this.packId, "install() not called");
            Path packRoot = writePack();
            event.registrar().discoverPack(packRoot.toUri(), packId);
            this.pending.clear();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to register custom biomes via datapack; aborting", t);
        }
    }

    /**
     * Writes a temporary datapack containing one JSON file per queued biome, plus pack.mcmeta.
     * Returns the pack root directory.
     */
    private Path writePack() throws Exception {
        Path root = Files.createTempDirectory("biomesapi-pack-");

        // pack.mcmeta. This MC version uses the versioned schema: alongside pack_format it requires
        // min_format and max_format as [major, minor] arrays. We declare support for exactly the
        // running format (minor 0).
        int format = serverDataPackFormat();
        com.google.gson.JsonArray formatRange = new com.google.gson.JsonArray();
        formatRange.add(format);
        formatRange.add(0);

        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", format);
        pack.add("min_format", formatRange);
        pack.add("max_format", formatRange);
        pack.addProperty("description", "BiomesAPI generated biomes");
        JsonObject meta = new JsonObject();
        meta.add("pack", pack);
        Files.writeString(root.resolve("pack.mcmeta"), GSON.toJson(meta));

        // Built-in registries are enough to encode biomes with EMPTY generation settings (sounds,
        // particles, entity spawns all live there). RegistryOps lets the codec resolve those refs.
        RegistryAccess access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, access);
        BiomeRegistry builder = BiomeRegistry.registry();

        for (CustomBiome biome : this.pending) {
            BiomeResourceKey rk = biome.getResourceKey();
            Biome nms = (Biome) builder.buildDelegate(biome);

            DataResult<JsonElement> result = Biome.DIRECT_CODEC.encodeStart(ops, nms);
            if (result.error().isPresent()) {
                throw new IllegalStateException("Biome codec encode failed for "
                    + rk.namespace() + ":" + rk.path() + " -> " + result.error().get().message());
            }
            JsonElement json = result.result().orElseThrow();

            Path biomeFile = root.resolve("data")
                .resolve(rk.namespace())
                .resolve("worldgen")
                .resolve("biome")
                .resolve(rk.path() + ".json");
            Files.createDirectories(biomeFile.getParent());
            Files.writeString(biomeFile, GSON.toJson(json));

            RegisteredBiomes.appendBiome(biome);
        }
        return root;
    }

    /**
     * Server-data pack_format int for the running version. On this version
     * {@code WorldVersion.datapackVersion()} returns a {@code PackFormat} object; we read its
     * (first no-arg) int accessor, which is the major/format number written into pack.mcmeta.
     */
    private static int serverDataPackFormat() {
        try {
            Object version = SharedConstants.getCurrentVersion();
            Object packFormat = version.getClass().getMethod("datapackVersion").invoke(version);

            for (var m : packFormat.getClass().getMethods()) {
                if (m.getParameterCount() == 0
                    && (m.getReturnType() == int.class || m.getReturnType() == Integer.class)
                    && !m.getName().equals("hashCode")) {
                    return ((Number) m.invoke(packFormat)).intValue();
                }
            }
            throw new IllegalStateException("PackFormat exposes no int accessor: " + packFormat.getClass().getName());
        } catch (Throwable t) {
            throw new IllegalStateException("Could not determine server data pack_format", t);
        }
    }
}