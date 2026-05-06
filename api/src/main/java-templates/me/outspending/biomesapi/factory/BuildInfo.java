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
    /** The version of BiomesAPI. */
    @AsOf("2.0.0")
    public static final String VERSION = "${version}";
    /** Non-sensitive FastStats metrics token. */
    @AsOf("2.1.0")
    public static final String METRICS_TOKEN = "1fad62f4a237fd41d17d1d328ccd12bd";
}
