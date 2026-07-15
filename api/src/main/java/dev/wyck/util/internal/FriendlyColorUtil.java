package dev.wyck.util.internal;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Internal utility class for handling hex colors.
 * Please be aware that methods in this class can
 * change without warning.
 */
@AsOf("3.0.0")
@ApiStatus.Internal
public final class FriendlyColorUtil {

    @AsOf("3.0.0")
    public static @Nullable Integer hexOrNull(@Nullable String color) {
        if (color == null || color.isEmpty()) return null;
        final String formatted = color.startsWith("#") ? color.substring(1) : color;
        return Integer.parseInt(formatted, 16);
    }

    @AsOf("3.0.0")
    public static int hex(@NonNull String hex) {
        Preconditions.checkNotNull(hex, "Hex color string cannot be null");
        Preconditions.checkArgument(!hex.isEmpty(), "Hex color string cannot be empty");
        if (hex.startsWith("#")) hex = hex.substring(1);
        return (int) Long.parseLong(hex, 16);
    }

    @AsOf("3.0.0")
    public static @Nullable String toHex(Integer color) {
        if (color == null) return null;
        return String.format("#%06X", 0xFFFFFF & color);
    }

}
