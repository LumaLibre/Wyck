package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.Activity;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.MoonPhase;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedAmbientParticle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Converters bridging API wrapper types to their NMS counterparts.
 */
@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
final class AttributeConverters {

    private AttributeConverters() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static final WrappedEnvironmentAttribute.Converter<Object, MoonPhase> MOON_PHASE =
            value -> AttributeConverter.WIRE.get().convertEnum(value, "net.minecraft.world.level.MoonPhase");

    static final WrappedEnvironmentAttribute.Converter<Object, Activity> ACTIVITY =
            value -> AttributeConverter.WIRE.get().convertEnum(value, "net.minecraft.world.entity.schedule.Activity");

    static final WrappedEnvironmentAttribute.Converter<Object, BedRule> BED_RULE =
            value -> AttributeConverter.WIRE.get().convertBedRule(value);

    static final WrappedEnvironmentAttribute.Converter<Object, ParticleCatalog> AMBIENT_PARTICLES =
            value -> AttributeConverter.WIRE.get().convertAmbientParticles(value);

    static final WrappedEnvironmentAttribute.Converter<Object, WrappedAmbientParticle<?>> DRIPSTONE_PARTICLE =
            value -> AttributeConverter.WIRE.get().convertDripstoneParticle(value);
}