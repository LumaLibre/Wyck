package me.outspending.biomesapi.factory;

import me.outspending.biomesapi.annotations.AsOf;

/**
 * Class containing build information about the BiomesAPI, such as the version.
 *
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.0.0")
public final class BuildInfo {
    /** The version of the BiomesAPI. */
    @AsOf("2.0.0")
    public static final String VERSION = "${version}";
}
