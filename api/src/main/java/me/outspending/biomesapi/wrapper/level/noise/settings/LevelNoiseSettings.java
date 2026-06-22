package me.outspending.biomesapi.wrapper.level.noise.settings;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the inner {@code NoiseSettings} shape of noise generator settings.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelNoiseSettings extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.settings.LevelNoiseSettingsFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelNoiseSettings create(int minY, int height, int sizeHorizontal, int sizeVertical);
    }

    LevelNoiseSettings OVERWORLD = of(-64, 384, 1, 2);
    LevelNoiseSettings NETHER = of(0, 128, 1, 2);
    LevelNoiseSettings END = of(0, 128, 2, 1);
    LevelNoiseSettings CAVES = of(-64, 192, 1, 2);
    LevelNoiseSettings FLOATING_ISLANDS = of(0, 256, 2, 1);

    /**
     * The lowest Y level the noise generates at.
     *
     * @return the minimum Y
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int minY();

    /**
     * The total vertical span the noise generates over.
     *
     * @return the height
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int height();

    /**
     * The horizontal noise cell size, in quarts.
     *
     * @return the horizontal size
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int sizeHorizontal();

    /**
     * The vertical noise cell size, in quarts.
     *
     * @return the vertical size
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int sizeVertical();

    /**
     * Creates a noise settings shape.
     *
     * @apiNote The values must satisfy the vanilla constraints, height a
     * multiple of 16, {@code minY + height <= 2032}, and {@code minY} a multiple of 16.
     *
     * @param minY the lowest Y level
     * @param height the total vertical span
     * @param sizeHorizontal the horizontal cell size, in quarts
     * @param sizeVertical the vertical cell size, in quarts
     * @return a new noise settings shape
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelNoiseSettings of(int minY, int height, int sizeHorizontal, int sizeVertical) {
        Preconditions.checkArgument(height % 16 == 0, "height must be a multiple of 16");
        Preconditions.checkArgument(minY + height <= 2032, "minY + height must be <= 2032");
        Preconditions.checkArgument(minY % 16 == 0, "minY must be a multiple of 16");
        return WIRE.get().create(minY, height, sizeHorizontal, sizeVertical);
    }
}