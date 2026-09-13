package dev.wyck.worldgen.structure.templatesystem;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

/**
 * A structure template: the NBT file a jigsaw piece or a template feature stamps into the world.
 *
 * @see <a href="https://minecraft.wiki/w/Structure_file">Structure file</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface StructureTemplate extends Wrapper, Keyed, Registerable<StructureTemplate> {

    /**
     * The identifier of the template.
     * @return the identifier of the template
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    @Override
    ResourceKey key();

    /**
     * The NBT file this template is loaded from when registered.
     * @return the source file, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<URL> source();

    /**
     * Loads this template's source file into the server's template manager under its key.
     *
     * <p>Call this once the server exists.{@code onLoad} or later and before any structure
     * that places the template generates.</p>
     *
     * @return this template
     * @throws IllegalStateException if this template has no source file, or no server is running yet
     * @throws UncheckedIOException if the source file cannot be read as a structure template
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    StructureTemplate register();

    /**
     * Names a template Minecraft already knows about, from a data pack or the world's
     * {@code generated} folder.
     * @param key the identifier of the template
     * @return a handle onto that template
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureTemplate of(ResourceKey key) {
        return create(key, null);
    }

    /**
     * Creates a template loaded from an NBT file on disk, such as one saved with a structure block.
     * @param key the identifier to register the template under
     * @param nbtFile the gzip-compressed {@code .nbt} file
     * @return a template that can be {@link #register() registered}
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureTemplate of(ResourceKey key, Path nbtFile) {
        try {
            return create(key, nbtFile.toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("not a loadable file: " + nbtFile, e);
        }
    }

    /**
     * Creates a template loaded from an NBT resource, such as one inside a plugin jar obtained with
     * {@link Class#getResource(String)}.
     * @param key the identifier to register the template under
     * @param nbtResource the gzip-compressed {@code .nbt} resource
     * @return a template that can be {@link #register() registered}
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureTemplate of(ResourceKey key, URL nbtResource) {
        return create(key, nbtResource);
    }

    /**
     * Names the template stored under the given identifier in the {@code minecraft} namespace.
     * @param path the path of the template
     * @return a handle onto that template
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureTemplate minecraft(String path) {
        return of(ResourceKey.minecraft(path));
    }

    private static StructureTemplate create(ResourceKey key, @Nullable URL source) {
        record Holder() {
            static final ConstructWireProvider<StructureTemplate> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.StructureTemplateImpl");
        }
        return Holder.WIRE.construct(key, Optional.ofNullable(source));
    }
}
