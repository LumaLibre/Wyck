package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.environment.sounds.BackgroundMusic;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.AmbientParticle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Converters bridging API wrapper types to their NMS counterparts.
 *
 * @since 2.1.0
 * @version 2.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
public interface AttributeConverter {

    WireProvider<AttributeConverter> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.environment.attribute.AttributeConverterImpl");

    /**
     * Converts a wrapper value (typically an NmsEnumTranslatable enum) to the NMS enum identified by the FQN.
     * @param wrapped the wrapper value to convert
     * @param nmsClassNames the fully qualified class names of the NMS enum to convert to.
     * @return the converted NMS enum value
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    <W extends NmsEnumTranslatable<W>> Object convertEnum(W wrapped, String... nmsClassNames);

    /**
     * Converts a BedRule to the NMS enum.
     * @param rule the BedRule to convert
     * @return the converted NMS enum value
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    Object convertBedRule(BedRule rule);

    /**
     * Converts a ParticleCatalog to the NMS enum.
     * @param catalog the ParticleCatalog to convert
     * @return the converted NMS enum value
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    Object convertAmbientParticles(ParticleCatalog catalog);

    /**
     * Converts a WrappedAmbientParticle to the NMS enum.
     * @param particle the WrappedAmbientParticle to convert
     * @return the converted NMS enum value
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    Object convertDripstoneParticle(AmbientParticle<?> particle);
}