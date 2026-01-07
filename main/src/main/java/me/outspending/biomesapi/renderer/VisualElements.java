package me.outspending.biomesapi.renderer;

import me.outspending.biomesapi.wrapper.environment.AmbientParticle;
import me.outspending.biomesapi.wrapper.environment.MoonPhase;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttribute;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;


public class VisualElements {

    // fogColor handled elsewhere
    private WrappedEnvironmentAttribute<Float, Float> fogStartDistance;
    private WrappedEnvironmentAttribute<Float, Float> fogEndDistance;
    private WrappedEnvironmentAttribute<Float, Float> skyFogEndDistance;
    private WrappedEnvironmentAttribute<Float, Float> cloudFogEndDistance;
    // waterFogColor handled elsewhere
    private WrappedEnvironmentAttribute<Float, Float> waterFogStartDistance;
    private WrappedEnvironmentAttribute<Float, Float> waterFogEndDistance;
    // skyColor handled elsewhere
    private WrappedEnvironmentAttribute<Integer, Integer> sunriseSunsetColor;
    private WrappedEnvironmentAttribute<Integer, Integer> cloudColor;
    private WrappedEnvironmentAttribute<Float, Float> cloudHeight;
    private WrappedEnvironmentAttribute<Float, Float> sunAngle;
    private WrappedEnvironmentAttribute<Float, Float> moonAngle;
    private WrappedEnvironmentAttribute<Float, Float> starAngle;
    private WrappedEnvironmentAttribute<net.minecraft.world.level.MoonPhase, MoonPhase> moonPhase;
    private WrappedEnvironmentAttribute<Float, Float> starBrightness;
    // skyLightColor handled elsewhere
    private WrappedEnvironmentAttribute<Float, Float> skyLightFactor;
    private WrappedEnvironmentAttribute<ParticleOptions, AmbientParticle> defaultDripstoneParticle;
    // ambientParticles handled elsewhere


    public VisualElements(Builder builder) {
        this.fogStartDistance = builder.fogStartDistance;
        this.fogEndDistance = builder.fogEndDistance;
        this.skyFogEndDistance = builder.skyFogEndDistance;
        this.cloudFogEndDistance = builder.cloudFogEndDistance;
        this.waterFogStartDistance = builder.waterFogStartDistance;
        this.waterFogEndDistance = builder.waterFogEndDistance;
        this.sunriseSunsetColor = builder.sunriseSunsetColor;
        this.cloudColor = builder.cloudColor;
        this.cloudHeight = builder.cloudHeight;
        this.sunAngle = builder.sunAngle;
        this.moonAngle = builder.moonAngle;
        this.starAngle = builder.starAngle;
        this.moonPhase = builder.moonPhase;
        this.starBrightness = builder.starBrightness;
        this.skyLightFactor = builder.skyLightFactor;
        this.defaultDripstoneParticle = builder.defaultDripstoneParticle;
    }


    public void applyToEnvironment(EnvironmentAttributeMap map) {
        fogStartDistance.applyToEnvironment(map);
        fogEndDistance.applyToEnvironment(map);
        skyFogEndDistance.applyToEnvironment(map);
        cloudFogEndDistance.applyToEnvironment(map);
        waterFogStartDistance.applyToEnvironment(map);
        waterFogEndDistance.applyToEnvironment(map);
        sunriseSunsetColor.applyToEnvironment(map);
        cloudColor.applyToEnvironment(map);
        cloudHeight.applyToEnvironment(map);
        sunAngle.applyToEnvironment(map);
        moonAngle.applyToEnvironment(map);
        starAngle.applyToEnvironment(map);
        moonPhase.applyToEnvironment(map);
        starBrightness.applyToEnvironment(map);
        skyLightFactor.applyToEnvironment(map);
        defaultDripstoneParticle.applyToEnvironment(map);
    }


    public static class Builder {

        private WrappedEnvironmentAttribute<Float, Float> fogStartDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.FOG_START_DISTANCE);
        private WrappedEnvironmentAttribute<Float, Float> fogEndDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.FOG_END_DISTANCE);
        private WrappedEnvironmentAttribute<Float, Float> skyFogEndDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.SKY_FOG_END_DISTANCE);
        private WrappedEnvironmentAttribute<Float, Float> cloudFogEndDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.CLOUD_FOG_END_DISTANCE);
        private WrappedEnvironmentAttribute<Float, Float> waterFogStartDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.WATER_FOG_START_DISTANCE);
        private WrappedEnvironmentAttribute<Float, Float> waterFogEndDistance = WrappedEnvironmentAttribute.of(EnvironmentAttributes.WATER_FOG_END_DISTANCE);
        private WrappedEnvironmentAttribute<Integer, Integer> sunriseSunsetColor = WrappedEnvironmentAttribute.of(EnvironmentAttributes.SUNRISE_SUNSET_COLOR);
        private WrappedEnvironmentAttribute<Integer, Integer> cloudColor = WrappedEnvironmentAttribute.of(EnvironmentAttributes.CLOUD_COLOR);
        private WrappedEnvironmentAttribute<Float, Float> cloudHeight = WrappedEnvironmentAttribute.of(EnvironmentAttributes.CLOUD_HEIGHT);
        private WrappedEnvironmentAttribute<Float, Float> sunAngle = WrappedEnvironmentAttribute.of(EnvironmentAttributes.SUN_ANGLE);
        private WrappedEnvironmentAttribute<Float, Float> moonAngle = WrappedEnvironmentAttribute.of(EnvironmentAttributes.MOON_ANGLE);
        private WrappedEnvironmentAttribute<Float, Float> starAngle = WrappedEnvironmentAttribute.of(EnvironmentAttributes.STAR_ANGLE);
        private WrappedEnvironmentAttribute<net.minecraft.world.level.MoonPhase, MoonPhase> moonPhase = WrappedEnvironmentAttribute.of(EnvironmentAttributes.MOON_PHASE, MoonPhase::getDelegatePhase);
        private WrappedEnvironmentAttribute<Float, Float> starBrightness = WrappedEnvironmentAttribute.of(EnvironmentAttributes.STAR_BRIGHTNESS);
        private WrappedEnvironmentAttribute<Float, Float> skyLightFactor = WrappedEnvironmentAttribute.of(EnvironmentAttributes.SKY_LIGHT_FACTOR);
        private WrappedEnvironmentAttribute<ParticleOptions, AmbientParticle> defaultDripstoneParticle = WrappedEnvironmentAttribute.of(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, AmbientParticle::getSimpleParticle);

        public Builder fogStartDistance(float distance) {
            this.fogStartDistance.setValue(distance);
            return this;
        }

        public Builder fogEndDistance(float distance) {
            this.fogEndDistance.setValue(distance);
            return this;
        }

        public Builder skyFogEndDistance(float distance) {
            this.skyFogEndDistance.setValue(distance);
            return this;
        }

        public Builder cloudFogEndDistance(float distance) {
            this.cloudFogEndDistance.setValue(distance);
            return this;
        }

        public Builder waterFogStartDistance(float distance) {
            this.waterFogStartDistance.setValue(distance);
            return this;
        }

        public Builder waterFogEndDistance(float distance) {
            this.waterFogEndDistance.setValue(distance);
            return this;
        }

        public Builder sunriseSunsetColor(int color) {
            this.sunriseSunsetColor.setValue(color);
            return this;
        }

        public Builder cloudColor(int color) {
            this.cloudColor.setValue(color);
            return this;
        }

        public Builder cloudHeight(float height) {
            this.cloudHeight.setValue(height);
            return this;
        }

        public Builder sunAngle(float angle) {
            this.sunAngle.setValue(angle);
            return this;
        }

        public Builder moonAngle(float angle) {
            this.moonAngle.setValue(angle);
            return this;
        }

        public Builder starAngle(float angle) {
            this.starAngle.setValue(angle);
            return this;
        }

        public Builder moonPhase(MoonPhase phase) {
            this.moonPhase.setValue(phase);
            return this;
        }

        public Builder starBrightness(float brightness) {
            this.starBrightness.setValue(brightness);
            return this;
        }

        public Builder skyLightFactor(float factor) {
            this.skyLightFactor.setValue(factor);
            return this;
        }

        public Builder defaultDripstoneParticle(AmbientParticle particle) {
            this.defaultDripstoneParticle.setValue(particle);
            return this;
        }

        public VisualElements build() {
            return new VisualElements(this);
        }
    }



}
