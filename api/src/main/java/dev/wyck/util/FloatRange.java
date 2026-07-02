package dev.wyck.util;

public record FloatRange(float min, float max) {

    public float clampMax(float value) {
        return Math.min(this.max, value);
    }
}
