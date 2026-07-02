package dev.wyck.wrapper.environment.attribute;

import io.papermc.paper.adventure.AdventureComponent;
import dev.wyck.annotations.WireFactory;
import dev.wyck.wrapper.internal.NmsEnumTranslatable;
import dev.wyck.wrapper.environment.BedRule;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.AmbientParticle;
import net.minecraft.core.particles.ParticleOptions;
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
        Optional<net.minecraft.network.chat.Component> errorMessage = rule.errorMessage() != null ? Optional.of(new AdventureComponent(rule.errorMessage()).deepConverted()) : Optional.empty();
        return new net.minecraft.world.attribute.BedRule(
                rule.canSleep().toNms(net.minecraft.world.attribute.BedRule.Rule.class),
                rule.canSetSpawn().toNms(net.minecraft.world.attribute.BedRule.Rule.class),
                rule.explodes(),
                errorMessage
        );
    }

    @Override
    public Object convertAmbientParticles(ParticleCatalog catalog) {
        return catalog.resolveParticles().stream()
            .map(resolved ->
                new net.minecraft.world.attribute.AmbientParticle((ParticleOptions) resolved.options().nms(), resolved.probability())
            )
            .toList();
    }

    @Override
    public Object convertDripstoneParticle(AmbientParticle<?> particle) {
        return particle.toParticleOptions().nms();
    }

}