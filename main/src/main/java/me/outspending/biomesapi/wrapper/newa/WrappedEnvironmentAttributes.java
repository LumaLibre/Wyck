package me.outspending.biomesapi.wrapper.newa;

import me.outspending.biomesapi.renderer.ParticleRenderer;
import me.outspending.biomesapi.wrapper.AmbientParticle;
import me.outspending.biomesapi.wrapper.MoonPhase;
import net.minecraft.world.attribute.AttributeType;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum WrappedEnvironmentAttributes {

    FOG_COLOR(EnvironmentAttributes.FOG_COLOR),
    FOG_START_DISTANCE(EnvironmentAttributes.FOG_START_DISTANCE),
    FOG_END_DISTANCE(EnvironmentAttributes.FOG_END_DISTANCE),
    SKY_FOG_END_DISTANCE(EnvironmentAttributes.SKY_FOG_END_DISTANCE),
    CLOUD_FOG_END_DISTANCE(EnvironmentAttributes.CLOUD_FOG_END_DISTANCE),
    WATER_FOG_COLOR(EnvironmentAttributes.WATER_FOG_COLOR),
    WATER_FOG_START_DISTANCE(EnvironmentAttributes.WATER_FOG_START_DISTANCE),
    WATER_FOG_END_DISTANCE(EnvironmentAttributes.WATER_FOG_END_DISTANCE),
    SKY_COLOR(EnvironmentAttributes.SKY_COLOR),
    SUNRISE_SUNSET_COLOR(EnvironmentAttributes.SUNRISE_SUNSET_COLOR),
    CLOUD_COLOR(EnvironmentAttributes.CLOUD_COLOR),
    CLOUD_HEIGHT(EnvironmentAttributes.CLOUD_HEIGHT),
    SUN_ANGLE(EnvironmentAttributes.SUN_ANGLE),
    MOON_ANGLE(EnvironmentAttributes.MOON_ANGLE),
    STAR_ANGLE(EnvironmentAttributes.STAR_ANGLE),
    MOON_PHASE(EnvironmentAttributes.MOON_PHASE, MoonPhase.class, MoonPhase::getDelegatePhase),
    STAR_BRIGHTNESS(EnvironmentAttributes.STAR_BRIGHTNESS),
    SKY_LIGHT_COLOR(EnvironmentAttributes.SKY_LIGHT_COLOR),
    SKY_LIGHT_FACTOR(EnvironmentAttributes.SKY_LIGHT_FACTOR),
    DEFAULT_DRIPSTONE_PARTICLE(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, AmbientParticle.class, AmbientParticle::getSimpleParticle),
    AMBIENT_PARTICLES(EnvironmentAttributes.AMBIENT_PARTICLES, ParticleRenderer.class, ParticleRenderer::getDelegateParticles),
    ;

    private final Supplier<AbstractWrappedEnvironmentAttribute<?>> wrappedAttributeSupplier;
    private final Class<?> delegateAttributeType;
    private final Class<?> exposedAttributeType;

    <T> WrappedEnvironmentAttributes(EnvironmentAttribute<@NotNull T> attribute) {
        this.wrappedAttributeSupplier = () -> new ExposedEnvironmentAttribute<>(attribute);
        this.delegateAttributeType = attribute.defaultValue().getClass();
        this.exposedAttributeType = delegateAttributeType;
    }

    <T, K> WrappedEnvironmentAttributes(EnvironmentAttribute<@NotNull T> attribute, Class<K> exposedAttributeType, HiddenEnvironmentAttribute.Converter<T, K> converter) {
        this.wrappedAttributeSupplier = () -> new HiddenEnvironmentAttribute<>(attribute, converter, null);
        this.delegateAttributeType = attribute.defaultValue().getClass();
        this.exposedAttributeType = exposedAttributeType;
    }


    public AbstractWrappedEnvironmentAttribute<?> getWrappedAttributeSupplier() {
        return wrappedAttributeSupplier.get();
    }

    public Class<?> getDelegateAttributeType() {
        return delegateAttributeType;
    }

    public Class<?> getExposedAttributeType() {
        return exposedAttributeType;
    }


    public <T> Class<T> getDelegateAttributeTypeAs(Class<T> clazz) {
        return (Class<T>) delegateAttributeType;
    }

}
