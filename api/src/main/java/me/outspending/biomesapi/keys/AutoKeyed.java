package me.outspending.biomesapi.keys;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public interface AutoKeyed extends Keyed {
    @Override
    @SuppressWarnings("PatternValidation")
    default Key key() {
        return Key.key(ResourceKey.BIOMESAPI_NAMESPACE, this.getClass().getName());
    }
}
