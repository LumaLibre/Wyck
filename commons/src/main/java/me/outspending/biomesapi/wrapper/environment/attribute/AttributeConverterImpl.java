package me.outspending.biomesapi.wrapper.environment.attribute;

import io.papermc.paper.adventure.AdventureComponent;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleCatalog;
import me.outspending.biomesapi.wrapper.environment.particle.ResolvedAmbientParticle;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedAmbientParticle;
import net.kyori.adventure.text.Component;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.attribute.AmbientParticle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;
import java.util.Optional;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class AttributeConverterImpl implements AttributeConverter {


    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <W extends NmsEnumTranslatable<W>> Object convertEnum(W wrapped, String... nmsClassNames) {
        for (String nmsClassName : nmsClassNames) {
            try {
                Class<?> nmsClass = Class.forName(nmsClassName);
                if (!nmsClass.isEnum()) {
                    throw new IllegalArgumentException(nmsClassName + " is not an enum");
                }
                return (wrapped).toNms((Class) nmsClass);
            } catch (ClassNotFoundException e) {
                // Try the next class name
            }
        }
        throw new IllegalStateException("NMS enum class not found: " + Arrays.toString(nmsClassNames));
    }

    @Override
    public Object convertBedRule(BedRule rule) {
        return new net.minecraft.world.attribute.BedRule(
                rule.canSleep().toNms(net.minecraft.world.attribute.BedRule.Rule.class),
                rule.canSetSpawn().toNms(net.minecraft.world.attribute.BedRule.Rule.class),
                rule.explodes(),
                errorMessageToNms(rule.errorMessage())
        );
    }

    @Override
    public Object convertAmbientParticles(ParticleCatalog catalog) {
        return catalog.resolveParticles().stream()
                .map(AttributeConverterImpl::resolvedToNms)
                .toList();
    }

    @Override
    public Object convertDripstoneParticle(WrappedAmbientParticle<?> particle) {
        return particle.toParticleOptions().nms();
    }

    private static AmbientParticle resolvedToNms(ResolvedAmbientParticle resolved) {
        return new AmbientParticle((ParticleOptions) resolved.options().nms(), resolved.probability());
    }

    private static Optional<net.minecraft.network.chat.Component> errorMessageToNms(Component errorMessage) {
        if (errorMessage == null) {
            return Optional.empty();
        }
        return Optional.of(new AdventureComponent(errorMessage).deepConverted());
    }
}