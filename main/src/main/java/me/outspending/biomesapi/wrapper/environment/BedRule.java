package me.outspending.biomesapi.wrapper.environment;

import io.papermc.paper.adventure.AdventureComponent;
import me.outspending.biomesapi.api.annotations.AsOf;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A record representing bed rules in a biome environment.
 * @param canSleep if players can sleep in the bed
 * @param canSetSpawn if players can set their spawn point using the bed
 * @param explodes if the bed explodes when used in this environment
 * @param errorMessage an optional error message displayed when a player tries to use the bed inappropriately
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@AsOf("1.1.0")
public record BedRule(Rule canSleep, Rule canSetSpawn, boolean explodes, @Nullable Component errorMessage) {

    /**
     * Converts the Adventure Component error message to a Minecraft {@link net.minecraft.network.chat.Component}.
     * @return an Optional containing the NMS Component if errorMessage is not null, otherwise an empty Optional
     */
    @AsOf("1.1.0")
    public Optional<net.minecraft.network.chat.Component> delegateErrorMessage() {
        if (errorMessage == null) {
            return Optional.empty();
        }
        AdventureComponent adventureComponent = new AdventureComponent(errorMessage);
        return Optional.of(adventureComponent.deepConverted());
    }


    /**
     * Converts this BedRule to its corresponding Minecraft {@link net.minecraft.world.attribute.BedRule}.
     * @return the Minecraft BedRule representation of this BedRule
     */
    @AsOf("1.1.0")
    public net.minecraft.world.attribute.BedRule delegateBedRule() {
        return new net.minecraft.world.attribute.BedRule(
                canSleep.getDelegateRule(),
                canSetSpawn.getDelegateRule(),
                explodes,
                this.delegateErrorMessage()
        );
    }

    /**
     * An enum representing the rules for bed usage in a biome environment.
     * Corresponds to the BedRule.Rule enum in Minecraft.
     * @version 1.1.0
     * @since 1.1.0
     * @author Jsinco
     */
    @AsOf("1.1.0")
    public enum Rule {

        /**
         * Beds can always be slept in.
         */
        ALWAYS(net.minecraft.world.attribute.BedRule.Rule.ALWAYS),
        /**
         * Beds can be slept in only when it is dark.
         */
        WHEN_DARK(net.minecraft.world.attribute.BedRule.Rule.WHEN_DARK),
        /**
         * Beds can never be slept in.
         */
        NEVER(net.minecraft.world.attribute.BedRule.Rule.NEVER);

        private final net.minecraft.world.attribute.BedRule.Rule delegateRule;

        @AsOf("1.1.0")
        Rule(net.minecraft.world.attribute.BedRule.Rule delegateRule) {
            this.delegateRule = delegateRule;
        }

        /**
         * Gets the corresponding Minecraft BedRule.Rule.
         * @return the Minecraft BedRule.Rule
         */
        @AsOf("1.1.0")
        public net.minecraft.world.attribute.BedRule.Rule getDelegateRule() {
            return delegateRule;
        }
    }


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private Rule canSleep = Rule.WHEN_DARK;
        private Rule canSetSpawn = Rule.ALWAYS;
        private boolean explodes = false;
        private Component errorMessage = Component.text("You can only sleep at night or during thunderstorms");

        public Builder setCanSleep(@NotNull Rule canSleep) {
            this.canSleep = canSleep;
            return this;
        }

        public Builder setCanSetSpawn(@NotNull Rule canSetSpawn) {
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
