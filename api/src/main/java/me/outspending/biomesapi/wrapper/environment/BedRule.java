package me.outspending.biomesapi.wrapper.environment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A record representing bed rules in a biome environment.
 * @param canSleep if players can sleep in the bed
 * @param canSetSpawn if players can set their spawn climatePoint using the bed
 * @param explodes if the bed explodes when used in this environment
 * @param errorMessage an optional error message displayed when a player tries to use the bed inappropriately
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("1.1.0")
public record BedRule(Rule canSleep, Rule canSetSpawn, boolean explodes, @Nullable Component errorMessage) {

    public static final Codec<BedRule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Rule.CODEC.fieldOf("can_sleep").forGetter(BedRule::canSleep),
        Rule.CODEC.fieldOf("can_set_spawn").forGetter(BedRule::canSetSpawn),
        Codec.BOOL.fieldOf("explodes").forGetter(BedRule::explodes),
        Codecs.COMPONENT_CODEC.optionalFieldOf("error_message").forGetter(rule ->
            Optional.ofNullable(rule.errorMessage()))
    ).apply(instance, (canSleep, canSetSpawn, explodes, errorMessage) ->
        new BedRule(canSleep, canSetSpawn, explodes, errorMessage.orElse(null))));


    /**
     * An enum representing the rules for bed usage in a biome environment.
     * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS BedRule.Rule value.
     *
     * @version 1.1.0
     * @since 1.1.0
     * @author Jsinco
     */
    @AsOf("1.1.0")
    public enum Rule implements NmsEnumTranslatable<Rule> {

        /** Beds can always be slept in. */
        ALWAYS("always"),
        /** Beds can be slept in only when it is dark. */
        WHEN_DARK("when_dark"),
        /** Beds can never be slept in. */
        NEVER("never");

        public static final KeyedEnumTranslator<Rule> TRANSLATOR = KeyedEnumTranslator.byKey(Rule::getKey, Rule.values());
        public static final Codec<Rule> CODEC = TRANSLATOR.codec();

        private final String key;

        @AsOf("1.1.0")
        Rule(String key) {
            this.key = key;
        }

        /**
         * Returns the vanilla key for this rule (e.g. "always", "when_dark", "never").
         * The impl module uses this key to resolve the underlying Minecraft BedRule.Rule value.
         * @return The vanilla key for this enum value.
         */
        @AsOf("1.1.0")
        public String getKey() {
            return key;
        }

        @Override
        public KeyedEnumTranslator<Rule> translator() {
            return TRANSLATOR;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Rule canSleep = Rule.WHEN_DARK;
        private Rule canSetSpawn = Rule.ALWAYS;
        private boolean explodes = false;
        private @Nullable Component errorMessage = Component.text("You can only sleep at night or during thunderstorms");

        public Builder setCanSleep(Rule canSleep) {
            this.canSleep = canSleep;
            return this;
        }

        public Builder setCanSetSpawn(Rule canSetSpawn) {
            this.canSetSpawn = canSetSpawn;
            return this;
        }

        public Builder setExplodes(boolean explodes) {
            this.explodes = explodes;
            return this;
        }

        public Builder setErrorMessage(@Nullable Component errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public BedRule build() {
            return new BedRule(canSleep, canSetSpawn, explodes, errorMessage);
        }
    }
}