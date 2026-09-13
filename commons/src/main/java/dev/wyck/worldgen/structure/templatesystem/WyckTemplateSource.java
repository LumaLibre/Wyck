package dev.wyck.worldgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import dev.wyck.util.internal.InternalReflectUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.loader.TemplateSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Prevents minecraft:reload removing wyck templated structures.
 */
@NullMarked
@ApiStatus.Internal
final class WyckTemplateSource extends TemplateSource {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Field SOURCES = InternalReflectUtil.field(StructureTemplateManager.class, "sources");

    private final Map<Identifier, URL> templates = new ConcurrentHashMap<>();

    private WyckTemplateSource(MinecraftServer server) {
        super(server.getFixerUpper(), server.registryAccess().lookupOrThrow(Registries.BLOCK));
    }

    static synchronized WyckTemplateSource installed(MinecraftServer server) {
        StructureTemplateManager manager = server.getStructureManager();
        List<TemplateSource> sources = InternalReflectUtil.get(SOURCES, manager);
        for (TemplateSource source : sources) {
            if (source instanceof WyckTemplateSource installed) {
                return installed;
            }
        }

        WyckTemplateSource created = new WyckTemplateSource(server);
        InternalReflectUtil.set(SOURCES, manager, ImmutableList.<TemplateSource>builder().add(created).addAll(sources).build());
        return created;
    }

    void register(StructureTemplateManager manager, Identifier id, URL file) {
        AtomicReference<@Nullable Throwable> failure = new AtomicReference<>();
        Optional<StructureTemplate> loaded = this.load(file::openStream, false, failure::set);
        if (loaded.isEmpty()) {
            Throwable cause = failure.get();
            String message = cause == null
                ? "structure template file not found: " + file
                : "could not read " + file + " as a structure template";
            throw new UncheckedIOException(new IOException(message, cause));
        }

        this.templates.put(id, file);
        // replaces a cached miss from before registration, or an older version of the template
        manager.structureRepository.put(id, loaded);
    }

    @Override
    public Optional<StructureTemplate> load(Identifier id) {
        URL file = this.templates.get(id);
        if (file == null) {
            return Optional.empty();
        }
        return this.load(file::openStream, false, e -> LOGGER.error("Couldn't load registered structure template {} from {}", id, file, e));
    }

    @Override
    public Stream<Identifier> list() {
        return this.templates.keySet().stream();
    }
}
