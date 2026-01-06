package me.outspending.biomesapi.wrapper.newa;

import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.wrapper.WrappedEnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentAttributeMapTransformer {


    private final AbstractWrappedEnvironmentAttribute<?>[] attributes;

    public EnvironmentAttributeMapTransformer(AbstractWrappedEnvironmentAttribute<?>... attributes) {
        this.attributes = attributes;
    }

    public EnvironmentAttributeMap transform() {
        EnvironmentAttributeMap.Builder builder = EnvironmentAttributeMap.builder();
        for (AbstractWrappedEnvironmentAttribute<?> wrappedAttribute : attributes) {
            addToBuilder(builder, wrappedAttribute);
        }
        return builder.build();
    }

    private <T> void addToBuilder(EnvironmentAttributeMap.Builder builder, AbstractWrappedEnvironmentAttribute<T> wrappedAttribute) {
        builder.set(wrappedAttribute.getAttribute(), wrappedAttribute.getValue());
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<AbstractWrappedEnvironmentAttribute<?>> attributes = new ArrayList<>();

        public Builder addWrappedAttribute(AbstractWrappedEnvironmentAttribute<?> attribute) {
            attributes.add(attribute);
            return this;
        }

        public <T> Builder addExposedAttribute(WrappedEnvironmentAttributes constant, T value) {
            AbstractWrappedEnvironmentAttribute<T> wrappedEnvironmentAttribute = (AbstractWrappedEnvironmentAttribute<T>) constant.getWrappedAttributeSupplier();
            wrappedEnvironmentAttribute.setValue(value);
            attributes.add(wrappedEnvironmentAttribute);
            return this;
        }

        public <T, K> Builder addHiddenAttribute(WrappedEnvironmentAttributes constant, K exposedValue) {
            AbstractWrappedEnvironmentAttribute<T> wrappedEnvironmentAttribute = (AbstractWrappedEnvironmentAttribute<T>) constant.getWrappedAttributeSupplier();
            wrappedEnvironmentAttribute.setValue(exposedValue);
            attributes.add(wrappedEnvironmentAttribute);
            return this;
        }

        public <V> Builder add(WrappedEnvironmentAttributes constant, V exposedValue) {
            AbstractWrappedEnvironmentAttribute<?> wrappedEnvironmentAttribute = constant.getWrappedAttributeSupplier();
            wrappedEnvironmentAttribute.setValue(exposedValue);
            attributes.add(wrappedEnvironmentAttribute);
            return this;
        }

        public EnvironmentAttributeMapTransformer build() {
            return new EnvironmentAttributeMapTransformer(attributes.toArray(new AbstractWrappedEnvironmentAttribute[0]));
        }
    }
}
