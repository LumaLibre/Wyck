package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.Activity;
import dev.wyck.wrapper.environment.BedRule;
import dev.wyck.wrapper.environment.MoonPhase;
import dev.wyck.wrapper.environment.TriState;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.AmbientParticle;
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

    static final EnvironmentAttribute.Converter<Object, MoonPhase> MOON_PHASE =
            value -> AttributeConverter.WIRE.get().convertEnum(value, "net.minecraft.world.level.MoonPhase");

    static final EnvironmentAttribute.Converter<Object, Activity> ACTIVITY =
            value -> AttributeConverter.WIRE.get().convertEnum(value, "net.minecraft.world.entity.schedule.Activity");

    static final EnvironmentAttribute.Converter<Object, BedRule> BED_RULE =
            value -> AttributeConverter.WIRE.get().convertBedRule(value);

    static final EnvironmentAttribute.Converter<Object, ParticleCatalog> AMBIENT_PARTICLES =
            value -> AttributeConverter.WIRE.get().convertAmbientParticles(value);

    static final EnvironmentAttribute.Converter<Object, AmbientParticle<?>> DRIPSTONE_PARTICLE =
            value -> AttributeConverter.WIRE.get().convertDripstoneParticle(value);

    static final EnvironmentAttribute.Converter<Object, TriState> EYEBLOSSOM_OPEN =
        value -> AttributeConverter.WIRE.get().convertEnum(value, "net.minecraft.util.TriState");

}