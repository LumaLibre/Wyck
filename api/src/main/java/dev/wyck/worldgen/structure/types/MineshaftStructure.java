package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A mineshaft.
 *
 * @see <a href="https://minecraft.wiki/w/Mineshaft">Mineshaft</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface MineshaftStructure extends DefinedStructure, Registerable<MineshaftStructure> {

    /**
     * The wood the mineshaft is built from.
     * @return the mineshaft type
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Type mineshaftType();

    @Override
    @AsOf("3.4.0")
    default MineshaftStructure withSettings(StructureSettings settings) {
        return of(resourceKey().orElse(null), settings, mineshaftType());
    }

    @Override
    @AsOf("3.4.0")
    default MineshaftStructure withResourceKey(ResourceKey resourceKey) {
        return of(resourceKey, settings(), mineshaftType());
    }

    @Override
    @AsOf("3.4.0")
    MineshaftStructure register();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new mineshaft.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param mineshaftType the wood the mineshaft is built from
     * @return a new mineshaft
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static MineshaftStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, Type mineshaftType) {
        record Holder() {
            static final ConstructWireProvider<MineshaftStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.MineshaftStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, mineshaftType);
    }

    /**
     * Creates a new builder for an oak mineshaft, vanilla's default.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings) {
        return new Builder(settings);
    }

    /**
     * The wood a mineshaft is built from.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    enum Type implements WrappedEnumerator<Type> {
        /** Oak, found everywhere but badlands. */
        NORMAL("NORMAL"),

        /** Dark oak, found in badlands. */
        MESA("MESA");

        public static final KeyedEnumTranslator<Type> TRANSLATOR = KeyedEnumTranslator.byKey(Type::getKey, Type.values());

        private final String key;

        @AsOf("3.4.0")
        Type(String key) {
            this.key = key;
        }

        @AsOf("3.4.0")
        @Override
        public KeyedEnumTranslator<Type> translator() {
            return TRANSLATOR;
        }

        /**
         * The vanilla name for this Type
         * @return the vanilla key for this enum value
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public String getKey() {
            return this.key;
        }
    }

    /**
     * Builder for {@link MineshaftStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private Type mineshaftType = Type.NORMAL;

        public Builder(StructureSettings settings) {
            this.settings = settings;
        }

        public Builder(MineshaftStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.mineshaftType = structure.mineshaftType();
        }

        /**
         * Sets the resource key of the structure.
         * @param resourceKey the resource key of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the settings every structure carries.
         * @param settings the settings of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder settings(StructureSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Sets the wood the mineshaft is built from.
         * @param mineshaftType the mineshaft type
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder mineshaftType(Type mineshaftType) {
            this.mineshaftType = mineshaftType;
            return this;
        }

        /**
         * Builds the structure.
         * @return the structure
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public MineshaftStructure build() {
            return of(resourceKey, settings, mineshaftType);
        }
    }
}
