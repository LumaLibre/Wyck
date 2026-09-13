package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * The reducer a {@link StructurePlacement} applies to its {@link StructurePlacement#frequency()}.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public enum FrequencyReduction implements WrappedEnumerator<FrequencyReduction> {
    DEFAULT("DEFAULT"),
    LEGACY_TYPE_1("LEGACY_TYPE_1"),
    LEGACY_TYPE_2("LEGACY_TYPE_2"),
    LEGACY_TYPE_3("LEGACY_TYPE_3");

    public static final KeyedEnumTranslator<FrequencyReduction> TRANSLATOR = KeyedEnumTranslator.byKey(FrequencyReduction::getKey, FrequencyReduction.values());

    private final String key;

    @AsOf("3.4.0")
    FrequencyReduction(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public KeyedEnumTranslator<FrequencyReduction> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla name for this FrequencyReduction
     * @return the vanilla key for this enum value
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String getKey() {
        return this.key;
    }
}
