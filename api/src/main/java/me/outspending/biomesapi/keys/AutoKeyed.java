package me.outspending.biomesapi.keys;

import io.papermc.paper.ServerBuildInfo;
import me.outspending.biomesapi.serialization.StringRepresentable;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public interface AutoKeyed extends Keyed {

    default String namespace() {
        return ResourceKey.MINECRAFT_NAMESPACE;
    }

    @Override
    @SuppressWarnings("PatternValidation")
    default Key key() {
        return Key.key(this.namespace(), StringRepresentable.asLowerSnakeCase(this.getClass().getSimpleName()));
    }
}
