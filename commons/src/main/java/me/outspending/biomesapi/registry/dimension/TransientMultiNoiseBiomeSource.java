package me.outspending.biomesapi.registry.dimension;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.V17;

/**
 * Builds a transient {@link MultiNoiseBiomeSource} subclass used to place a custom biome through a
 * {@link RuntimeDimensionEditor}. The generated subclass keeps the original preset in the inherited
 * {@code parameters} field, which is the only field the codec serializes, while a separate
 * {@code editedParameters} field holds the full list that generation reads. A save therefore writes
 * only the vanilla preset.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class TransientMultiNoiseBiomeSource {
    // This use to be implemented with ByteBuddy was re-written with ASM because BB is too fat

    private static final String GENERATED_SIMPLE_NAME = "GeneratedTransientMultiNoiseBiomeSource";

    private static final sun.misc.Unsafe UNSAFE;
    private static final Class<? extends MultiNoiseBiomeSource> GENERATED;
    private static final Field PARAMETERS_FIELD;
    private static final Field EDITED_PARAMETERS_FIELD;
    private static final Field POSSIBLE_BIOMES_FIELD;

    static {
        try {
            Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) theUnsafe.get(null);

            GENERATED = generateSubclass();

            // the inherited Either field on the stock class, the only field the codec touches
            PARAMETERS_FIELD = MultiNoiseBiomeSource.class.getDeclaredField("parameters");
            PARAMETERS_FIELD.setAccessible(true);

            EDITED_PARAMETERS_FIELD = GENERATED.getDeclaredField("editedParameters");
            EDITED_PARAMETERS_FIELD.setAccessible(true);

            POSSIBLE_BIOMES_FIELD = findPossibleBiomesField();
            POSSIBLE_BIOMES_FIELD.setAccessible(true);
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    private TransientMultiNoiseBiomeSource() {
    }

    /**
     * Builds a transient source carrying the vanilla preset for serialization and the edited list for
     * generation. The returned object is a real {@link MultiNoiseBiomeSource} subclass whose
     * {@code codec()} is the registered multi_noise codec and whose {@code parameters} field is the
     * preset, so a save writes the preset form and a missing custom biome can never brick the world.
     *
     * @param overworldPreset the registered overworld parameter list holder, drives serialization
     * @param editedParameters the full list including the custom biome, drives generation
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static MultiNoiseBiomeSource create(Holder<MultiNoiseBiomeSourceParameterList> overworldPreset, Climate.ParameterList<Holder<Biome>> editedParameters) {
        try {
            MultiNoiseBiomeSource instance = (MultiNoiseBiomeSource) UNSAFE.allocateInstance(GENERATED);

            PARAMETERS_FIELD.set(instance, Either.right(overworldPreset));
            EDITED_PARAMETERS_FIELD.set(instance, editedParameters);

            setPossibleBiomes(instance, editedParameters);
            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException("failed to build transient multi_noise source", t);
        }
    }

    /**
     * Generation read for {@code getNoiseBiome(TargetPoint)}, invoked by the generated subclass.
     * Mirrors the stock method but resolves against the edited list rather than the preset.
     *
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Holder<Biome> getNoiseBiome(Climate.ParameterList<Holder<Biome>> edited, Climate.TargetPoint target) {
        return edited.findValue(target);
    }

    /**
     * Generation read for {@code collectPossibleBiomes()}, invoked by the generated subclass. Streams
     * the edited list so the feature sort stays aware of the custom biome.
     *
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Stream<Holder<Biome>> collectPossibleBiomes(Climate.ParameterList<Holder<Biome>> edited) {
        return edited.values().stream().map(Pair::getSecond);
    }

    // emits the no constructor subclass with one field and two trampoline methods, then defines it in
    // this class's runtime package so it tracks any downstream relocation
    private static Class<? extends MultiNoiseBiomeSource> generateSubclass() throws Throwable {
        String selfInternal = Type.getInternalName(TransientMultiNoiseBiomeSource.class);
        String pkgInternal = selfInternal.substring(0, selfInternal.lastIndexOf('/') + 1);
        String generatedInternal = pkgInternal + GENERATED_SIMPLE_NAME;

        String superInternal = Type.getInternalName(MultiNoiseBiomeSource.class);

        String paramListDesc = Type.getDescriptor(Climate.ParameterList.class);
        String getNoiseBiomeOverrideDesc = Type.getMethodDescriptor(
            Type.getType(Holder.class), Type.getType(Climate.TargetPoint.class));
        String getNoiseBiomeHelperDesc = Type.getMethodDescriptor(
            Type.getType(Holder.class), Type.getType(Climate.ParameterList.class), Type.getType(Climate.TargetPoint.class));
        String collectOverrideDesc = Type.getMethodDescriptor(Type.getType(Stream.class));
        String collectHelperDesc = Type.getMethodDescriptor(
            Type.getType(Stream.class), Type.getType(Climate.ParameterList.class));


        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V17, ACC_PUBLIC | ACC_FINAL | ACC_SUPER, generatedInternal, null, superInternal, null);

        // the side list generation reads, never serialized by the multi_noise codec
        cw.visitField(ACC_PUBLIC, "editedParameters", paramListDesc, null, null).visitEnd();

        // getNoiseBiome(TargetPoint) -> TransientMultiNoiseBiomeSource.getNoiseBiome(editedParameters, target)
        MethodVisitor getNoise = cw.visitMethod(ACC_PUBLIC, "getNoiseBiome", getNoiseBiomeOverrideDesc, null, null);
        getNoise.visitCode();
        getNoise.visitVarInsn(ALOAD, 0);
        getNoise.visitFieldInsn(GETFIELD, generatedInternal, "editedParameters", paramListDesc);
        getNoise.visitVarInsn(ALOAD, 1);
        getNoise.visitMethodInsn(INVOKESTATIC, selfInternal, "getNoiseBiome", getNoiseBiomeHelperDesc, false);
        getNoise.visitInsn(ARETURN);
        getNoise.visitMaxs(0, 0);
        getNoise.visitEnd();

        // collectPossibleBiomes() -> TransientMultiNoiseBiomeSource.collectPossibleBiomes(editedParameters)
        MethodVisitor collect = cw.visitMethod(ACC_PUBLIC, "collectPossibleBiomes", collectOverrideDesc, null, null);
        collect.visitCode();
        collect.visitVarInsn(ALOAD, 0);
        collect.visitFieldInsn(GETFIELD, generatedInternal, "editedParameters", paramListDesc);
        collect.visitMethodInsn(INVOKESTATIC, selfInternal, "collectPossibleBiomes", collectHelperDesc, false);
        collect.visitInsn(ARETURN);
        collect.visitMaxs(0, 0);
        collect.visitEnd();

        cw.visitEnd();

        Class<?> defined = MethodHandles.lookup().defineClass(cw.toByteArray());
        return defined.asSubclass(MultiNoiseBiomeSource.class);
    }

    private static void setPossibleBiomes(MultiNoiseBiomeSource instance, Climate.ParameterList<Holder<Biome>> editedParameters) throws IllegalAccessException {
        Set<Holder<Biome>> possible = editedParameters.values().stream()
            .map(Pair::getSecond)
            .distinct()
            .collect(ImmutableSet.toImmutableSet());

        Class<?> type = POSSIBLE_BIOMES_FIELD.getType();
        if (Supplier.class.isAssignableFrom(type)) {
            POSSIBLE_BIOMES_FIELD.set(instance, Suppliers.memoize(() -> possible));
        } else if (Set.class.isAssignableFrom(type)) {
            POSSIBLE_BIOMES_FIELD.set(instance, possible);
        } else {
            throw new IllegalStateException("unexpected possibleBiomes field type on BiomeSource, "
                + type.getName() + ", expected a Supplier or a Set, mappings changed");
        }
    }

    private static Field findPossibleBiomesField() {
        Field setField = null;
        for (Field f : BiomeSource.class.getDeclaredFields()) {
            if (Supplier.class.isAssignableFrom(f.getType())) {
                return f;
            }
            if (Set.class.isAssignableFrom(f.getType())) {
                setField = f;
            }
        }
        if (setField != null) {
            return setField;
        }
        throw new IllegalStateException("no possibleBiomes field on BiomeSource, expected a Supplier or Set");
    }
}