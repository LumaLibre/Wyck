package me.outspending.biomesapi.registry.level.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.factory.WireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * An interface for registering and modifying custom dimension types on a Minecraft server.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface DimensionRegistry {

    @ApiStatus.Internal
    WireProvider<DimensionRegistry> WIRE = WireProvider.create("me.outspending.biomesapi.*.registry.level.dimension.DimensionTypeRegistry");

    /**
     * @return the current DimensionRegistry instance.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DimensionRegistry registry() {
        return WIRE.get();
    }

    /**
     * Builds a {@link Dimension} into an NMS dimension type.
     * Performs no registration and touches no server state, so it is safe during bootstrap.
     *
     * @param dimension the dimension to build
     * @return the built NMS dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Object buildDelegate(Dimension dimension);

    /**
     * Registers a custom dimension type to the server's dimension_type registry.
     *
     * @param dimension the dimension to register
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void register(Dimension dimension);

    /**
     * Modifies a dimension type.
     * @param dimension the dimension to modify
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void modify(Dimension dimension);

    /**
     * Modifies a dimension type.
     * @param key the key of the dimension to modify
     * @param newData the new data for the dimension
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void modify(ResourceKey key, Dimension newData);
}