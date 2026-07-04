package dev.wyck.util;

public record FloatRange(float min, float max) {
    public void validate(float value) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("Value must be between " + min + " and " + max);
        }
    }

    public void validate(float value, String name) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("'" + name + "' must be between " + min + " and " + max);
        }
    }

    public static FloatRange of(float min, float max) {
        return new FloatRange(min, max);
    }
}
