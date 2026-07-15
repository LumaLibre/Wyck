package dev.wyck.exceptions;

import dev.wyck.annotations.AsOf;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.worldgen.chunk.ChunkGenerator;

/**
 * Thrown when {@link LevelStemEditor#dimension(Dimension)} or
 * {@link LevelStemEditor#chunkGenerator(ChunkGenerator)} is applied with
 * a replacement whose vertical geometry differs from the existing level.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@AsOf("3.0.0")
public class BadDimensionTypeGeometry extends RuntimeException {

    @AsOf("3.0.0")
    public static BadDimensionTypeGeometry dimensionType(String dimId, int oldMinY, int newMinY, int oldHeight, int newHeight, int oldLogicalHeight, int newLogicalHeight) {
        return new BadDimensionTypeGeometry(
            "refusing to swap dimension type for " + dimId + ": vertical geometry differs ("
                + "minY " + oldMinY + " -> " + newMinY + ", "
                + "height " + oldHeight + " -> " + newHeight + ", "
                + "logicalHeight " + oldLogicalHeight + " -> " + newLogicalHeight + ")."
        );
    }

    @AsOf("3.0.0")
    public static BadDimensionTypeGeometry chunkGenerator(String dimId, int levelMinY, int genMinY, int levelHeight, int genHeight) {
        return new BadDimensionTypeGeometry(
            "refusing to swap chunk generator for " + dimId + ": vertical geometry differs ("
                + "minY " + levelMinY + " -> " + genMinY + ", "
                + "height " + levelHeight + " -> " + genHeight + "). "
                + "The generator cannot change a populated level's baked geometry."
        );
    }

    private BadDimensionTypeGeometry(String message) {
        super(message);
    }
}