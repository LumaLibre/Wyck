package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.unsafe.NmsEnumTranslatable;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedAmbientParticle;
import org.jetbrains.annotations.ApiStatus;

@AsOf("2.1.0")
@ApiStatus.Internal
public interface AttributeConverter {

    WireProvider<AttributeConverter> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.environment.attribute.AttributeConverterImpl");

    /** Converts a wrapper value (typically an NmsEnumTranslatable enum) to the NMS enum identified by the FQN. */
    @AsOf("2.1.0")
    <W extends NmsEnumTranslatable<W>> Object convertEnum(W wrapped, String nmsClassName);

    @AsOf("2.1.0")
    Object convertBedRule(BedRule rule);

    @AsOf("2.1.0")
    Object convertAmbientParticles(ParticleCatalog catalog);

    @AsOf("2.1.0")
    Object convertDripstoneParticle(WrappedAmbientParticle<?> particle);
}