package dev.wyck.test.carver;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record StarConfig(
    int points,
    double outerRadius,
    double innerRadius,
    int topY,
    int bottomY,
    double twistPerBlock
) {

    public static StarConfig defaults() {
        return new StarConfig(5, 9.0D, 3.5D, 110, 40, 0.03D);
    }
}