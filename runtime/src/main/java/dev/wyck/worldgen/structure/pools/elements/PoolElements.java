package dev.wyck.worldgen.structure.pools.elements;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
final class PoolElements {

    private static final Constructor<SinglePoolElement> SINGLE = constructor(SinglePoolElement.class);
    private static final Constructor<LegacySinglePoolElement> LEGACY = constructor(LegacySinglePoolElement.class);

    static SinglePoolElement single(Identifier template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings) {
        return construct(SINGLE, template, processors, projection, overrideLiquidSettings);
    }

    static LegacySinglePoolElement legacy(Identifier template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings) {
        return construct(LEGACY, template, processors, projection, overrideLiquidSettings);
    }

    private static <T> T construct(Constructor<T> constructor, Identifier template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings) {
        try {
            return constructor.newInstance(Either.<Identifier, StructureTemplate>left(template), processors, projection, overrideLiquidSettings);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("could not construct " + constructor.getDeclaringClass().getSimpleName(), e);
        }
    }

    private static <T> Constructor<T> constructor(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(Either.class, Holder.class, StructureTemplatePool.Projection.class, Optional.class);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(type.getName() + " has no (template, processors, projection, liquid settings) constructor", e);
        }
    }
}
