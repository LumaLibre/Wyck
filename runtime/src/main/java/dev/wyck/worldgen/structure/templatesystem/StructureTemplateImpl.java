package dev.wyck.worldgen.structure.templatesystem;

import dev.wyck.keys.ResourceKey;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.net.URL;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record StructureTemplateImpl(
    @Override ResourceKey key,
    @Override Optional<URL> source
) implements StructureTemplate {

    @Override
    public Object toMinecraft() {
        return this.key.toMinecraft();
    }

    @Override
    @SuppressWarnings("ConstantValue") // getServer is null until the server is constructed
    public StructureTemplate register() {
        URL file = this.source.orElseThrow(() -> new IllegalStateException(
            "template " + this.key.asString() + " has no source file to register; create it with StructureTemplate.of(key, file)"
        ));
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            throw new IllegalStateException("templates can only be registered once the server exists, onLoad or later");
        }
        WyckTemplateSource.installed(server).register(server.getStructureTemplateManager(), this.key.identifier(), file);
        return this;
    }
}
