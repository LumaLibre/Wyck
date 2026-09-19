package dev.wyck.environment.attribute;

import dev.wyck.keys.ResourceKey;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class FriendlyColorConverter implements EnvironmentAttribute.Converter<Integer, Object> {

    private static final Set<ResourceKey> ARGB_ATTRIBUTES = Set.of(
        ResourceKey.minecraft("visual/sunrise_sunset_color"),
        ResourceKey.minecraft("visual/cloud_color")
    );

    private final boolean argb;

    public FriendlyColorConverter(ResourceKey key) {
        this.argb = ARGB_ATTRIBUTES.contains(key);
    }

    @Override
    public Object convert(Integer color) {
        return this.argb ? ARGB.vector4fFromARGB32(color) : ARGB.vector3fFromRGB24(color);
    }
}
