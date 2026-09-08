package dev.wyck.codegen;

import com.palantir.javapoet.ClassName;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record ReferenceSpec(
        ClassName outputClass,
        ClassName typeClass,
        Class<?> registryType,
        Function<Object, Identifier> registryLookup,
        List<Class<?>> sourceClasses,
        String javadoc,
        String since,
        @Nullable String keyChain,
        @Nullable Predicate<Field> fieldFilter,
        boolean asInterface
) implements GeneratorSpec {

    public ReferenceSpec(
            ClassName outputClass,
            ClassName typeClass,
            Class<?> registryType,
            Function<Object, Identifier> registryLookup,
            List<Class<?>> sourceClasses,
            String javadoc,
            String since,
            @Nullable String keyChain,
            @Nullable Predicate<Field> fieldFilter
    ) {
        this(outputClass, typeClass, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, fieldFilter, false);
    }

    public ReferenceSpec(
            ClassName outputClass,
            ClassName typeClass,
            Class<?> registryType,
            Function<Object, Identifier> registryLookup,
            List<Class<?>> sourceClasses,
            String javadoc,
            String since,
            @Nullable String keyChain
    ) {
        this(outputClass, typeClass, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, null, false);
    }

    public ReferenceSpec(
            ClassName outputClass,
            ClassName typeClass,
            Class<?> registryType,
            Function<Object, Identifier> registryLookup,
            List<Class<?>> sourceClasses,
            String javadoc,
            String since
    ) {
        this(outputClass, typeClass, registryType, registryLookup, sourceClasses, javadoc, since, null, null, false);
    }

    /**
     * Emits {@code interface} instead of {@code final class}, for constants meant to be
     * inherited by a hand-written interface (e.g. {@code Easing extends Easings}).
     */
    public ReferenceSpec asConstantsInterface() {
        return new ReferenceSpec(outputClass, typeClass, registryType, registryLookup, sourceClasses, javadoc, since, keyChain, fieldFilter, true);
    }
}