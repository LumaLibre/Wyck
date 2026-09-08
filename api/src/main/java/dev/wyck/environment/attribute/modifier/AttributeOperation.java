package dev.wyck.environment.attribute.modifier;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import java.lang.Override;
import java.lang.String;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Operations for environment attribute maps and timelines.
 * </p>
 *
 *
 * @since 3.2.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.2.0")
@Generated("2026-09-08T15:00:53.426148Z")
public enum AttributeOperation implements WrappedEnumerator<AttributeOperation> {
    OVERRIDE("override"),

    ALPHA_BLEND("alpha_blend"),

    ADD("add"),

    SUBTRACT("subtract"),

    MULTIPLY("multiply"),

    BLEND_TO_GRAY("blend_to_gray"),

    MINIMUM("minimum"),

    MAXIMUM("maximum"),

    AND("and"),

    NAND("nand"),

    OR("or"),

    NOR("nor"),

    XOR("xor"),

    XNOR("xnor");

    public static final KeyedEnumTranslator<AttributeOperation> TRANSLATOR = KeyedEnumTranslator.byKey(AttributeOperation::getKey, AttributeOperation.values());

    private final String key;

    @AsOf("3.2.0")
    AttributeOperation(String key) {
        this.key = key;
    }

    @AsOf("3.2.0")
    @Override
    public KeyedEnumTranslator<AttributeOperation> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this OperationId
     *  @return the vanilla key for this enum value
     *  @since 3.2.0
     */
    @AsOf("3.2.0")
    public String getKey() {
        return this.key;
    }
}
