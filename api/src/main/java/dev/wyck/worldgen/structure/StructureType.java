package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import java.lang.Override;
import java.lang.String;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references to the built-in structure types, the algorithms in the {@code STRUCTURE_TYPE} registry.
 * </p>
 *
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.4.0")
@Generated("2026-09-13T05:38:38.766445Z")
public enum StructureType implements WrappedConstant<StructureType> {
    BURIED_TREASURE("buried_treasure"),
    DESERT_PYRAMID("desert_pyramid"),
    END_CITY("end_city"),
    FORTRESS("fortress"),
    IGLOO("igloo"),
    JIGSAW("jigsaw"),
    JUNGLE_TEMPLE("jungle_temple"),
    MINESHAFT("mineshaft"),
    NETHER_FOSSIL("nether_fossil"),
    OCEAN_MONUMENT("ocean_monument"),
    OCEAN_RUIN("ocean_ruin"),
    RUINED_PORTAL("ruined_portal"),
    SHIPWRECK("shipwreck"),
    STRONGHOLD("stronghold"),
    SWAMP_HUT("swamp_hut"),
    WOODLAND_MANSION("woodland_mansion");

    public static final RegisteredConstantTranslator<StructureType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.STRUCTURE_TYPE, StructureType::resourceKey, StructureType.values());

    private final String key;

    @AsOf("3.4.0")
    StructureType(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public RegisteredConstantTranslator<StructureType> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla registry path for this activity.
     * @return the registry path for this activity
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String key() {
        return this.key;
    }

    @AsOf("3.4.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }
}
