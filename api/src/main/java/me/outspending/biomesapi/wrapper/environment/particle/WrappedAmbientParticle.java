package me.outspending.biomesapi.wrapper.environment.particle;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.options.BlockParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ColorParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustTransitionParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ItemParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ParticleOptionsFactory;
import me.outspending.biomesapi.wrapper.environment.particle.options.PowerParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SculkChargeParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.SpellParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.TrailParticle;
import me.outspending.biomesapi.wrapper.environment.particle.options.VibrationParticle;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * A wrapper for ambient particles in a biome, including their type, probability, and optional data.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @author Jsinco
 * @param <T> The type of ParticleOptions associated with the particle.
 */
@NullMarked
@AsOf("1.1.0")
public class WrappedAmbientParticle<T> {

    public static final Codec<WrappedAmbientParticle<?>> CODEC = WrappedParticleTypes.CODEC.dispatch(
        "type",
        WrappedAmbientParticle::getType,
        type -> {
            if (type.isSimple()) {
                return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.FLOAT.fieldOf("probability").forGetter(WrappedAmbientParticle::getProbability)
                ).apply(instance, probability -> WrappedAmbientParticle.of(type, probability)));
            }

            @SuppressWarnings("unchecked")
            Codec<ParticleData> dataCodec = (Codec<ParticleData>) codecFor(type);
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.FLOAT.fieldOf("probability").forGetter(WrappedAmbientParticle::getProbability),
                dataCodec.fieldOf("data").forGetter(WrappedAmbientParticle::getParticleData)
            ).apply(instance, (probability, data) -> WrappedAmbientParticle.of(type, probability, data)));
        }
    );

    // TODO: Replace this with some lazy reflection
    private static final Map<Class<? extends ParticleData>, Codec<? extends ParticleData>> CODECS_BY_CLASS = Map.of(
        BlockParticle.class, BlockParticle.CODEC,
        ColorParticle.class, ColorParticle.CODEC,
        DustParticle.class, DustParticle.CODEC,
        DustTransitionParticle.class, DustTransitionParticle.CODEC,
        ItemParticle.class, ItemParticle.CODEC,
        PowerParticle.class, PowerParticle.CODEC,
        SculkChargeParticle.class, SculkChargeParticle.CODEC,
        SpellParticle.class, SpellParticle.CODEC,
        TrailParticle.class, TrailParticle.CODEC,
        VibrationParticle.class, VibrationParticle.CODEC
    );

    private final WrappedParticleTypes ambientParticle;
    private final float probability;
    private final @Nullable ParticleData particleData;

    /**
     * Creates a WrappedAmbientParticle with the specified type, probability, and optional particle data.
     * @param ambientParticle The wrapped particle type.
     * @param probability The probability of the particle.
     * @param particleData The optional particle data.
     */
    @AsOf("1.1.0")
    public WrappedAmbientParticle(WrappedParticleTypes ambientParticle, float probability, @Nullable ParticleData particleData) {
        Preconditions.checkArgument(ambientParticle.isSimple() == (particleData == null), "Simple particles must not have particle data; complex particles must have particle data.");

        this.ambientParticle = ambientParticle;
        this.probability = probability;
        this.particleData = particleData;
    }

    /**
     * Creates a WrappedAmbientParticle for a simple particle type without additional data.
     * @param ambientParticle The wrapped particle type.
     * @param probability The probability of the particle.
     */
    @AsOf("1.1.0")
    public WrappedAmbientParticle(WrappedParticleTypes ambientParticle, float probability) {
        Preconditions.checkArgument(ambientParticle.isSimple(), "Particle data must be provided for complex particle types.");

        this.ambientParticle = ambientParticle;
        this.probability = probability;
        this.particleData = null;
    }

    /**
     * Delegates the wrapped ambient particle to a Minecraft ParticleOptions.
     * @return The corresponding Minecraft ParticleOptions.
     */
    @AsOf("1.1.0")
    public ParticleOptionsHandle toParticleOptions() {
        ParticleTypeHandle particleType = ambientParticle.getParticleType();
        if (ambientParticle.isSimple()) {
            return ParticleOptionsFactory.WIRE.get().simple(particleType);
        }

        Preconditions.checkNotNull(particleData, "Particle data must not be null for complex particle types.");
        return particleData.apply(particleType);
    }

    /**
     * Gets the probability of the particle.
     * @return The probability of the particle.
     */
    @AsOf("1.1.0")
    public float getProbability() {
        return probability;
    }

    /**
     * Gets the wrapped ambient particle type.
     * @return The wrapped ambient particle type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public WrappedParticleTypes getType() {
        return ambientParticle;
    }

    /**
     * Gets the particle data, if any.
     * @return The particle data, or null if none.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public @Nullable ParticleData getParticleData() {
        return particleData;
    }

    /**
     * Constructs a new WrappedAmbientParticle instance.
     * @param ambientParticle The ambient particle type.
     * @param probability The probability of the particle.
     * @param particleData The particle data.
     * @return A new WrappedAmbientParticle instance.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public static WrappedAmbientParticle<?> of(WrappedParticleTypes ambientParticle, float probability, @Nullable ParticleData particleData) {
        return new WrappedAmbientParticle<>(ambientParticle, probability, (ParticleData) particleData);
    }

    /**
     * Constructs a new WrappedAmbientParticle instance for a simple particle type.
     * @param ambientParticle The ambient particle type.
     * @param probability The probability of the particle.
     * @return A new WrappedAmbientParticle instance for a simple particle type.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public static WrappedAmbientParticle<?> of(WrappedParticleTypes ambientParticle, float probability) {
        return new WrappedAmbientParticle<>(ambientParticle, probability);
    }


    private static Codec<? extends ParticleData> codecFor(WrappedParticleTypes type) {
        Class<? extends ParticleData> dataClass = type.getParticleDataClass();
        Preconditions.checkArgument(dataClass != null, "simple particle type has no data codec: " + type);

        Codec<? extends ParticleData> codec = CODECS_BY_CLASS.get(dataClass);
        Preconditions.checkArgument(codec != null, "no codec registered for particle data " + dataClass.getName());

        return codec;
    }
}
