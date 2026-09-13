package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.structure.templatesystem.rule.BlockEntityModifier;
import dev.wyck.worldgen.structure.templatesystem.rule.PosRuleTest;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * One rule of a {@link RuleProcessor}: when the template block passes {@link #inputPredicate()}, the
 * world block it lands on passes {@link #locationPredicate()}, and its position passes
 * {@link #positionPredicate()}, the block is replaced with {@link #outputState()}.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ProcessorRule extends Wrapper {

    /**
     * The test the template block has to pass.
     * @return the input predicate
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    RuleTest inputPredicate();

    /**
     * The test the world block being replaced has to pass.
     * @return the location predicate
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    RuleTest locationPredicate();

    /**
     * The test the block's position within the template has to pass.
     * @return the position predicate
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    PosRuleTest positionPredicate();

    /**
     * The block placed when every test passes.
     * @return the output state
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BlockData outputState();

    /**
     * What happens to the placed block's block entity data.
     * @return the block entity modifier
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BlockEntityModifier blockEntityModifier();

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
     * Creates a new rule.
     * @param inputPredicate the test the template block has to pass
     * @param locationPredicate the test the world block being replaced has to pass
     * @param positionPredicate the test the block's position has to pass
     * @param outputState the block placed when every test passes
     * @param blockEntityModifier what happens to the placed block's block entity data
     * @return a new rule
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ProcessorRule of(RuleTest inputPredicate, RuleTest locationPredicate, PosRuleTest positionPredicate, BlockData outputState, BlockEntityModifier blockEntityModifier) {
        record Holder() {
            static final ConstructWireProvider<ProcessorRule> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.ProcessorRuleImpl");
        }
        return Holder.WIRE.construct(inputPredicate, locationPredicate, positionPredicate, outputState, blockEntityModifier);
    }

    /**
     * Creates a new builder that replaces any template block passing {@code inputPredicate} with
     * {@code outputState}, wherever it lands.
     * @param inputPredicate the test the template block has to pass
     * @param outputState the block placed when every test passes
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(RuleTest inputPredicate, BlockData outputState) {
        return new Builder(inputPredicate, outputState);
    }

    /**
     * Reads a Minecraft processor rule.
     * @param minecraftRule the rule to read
     * @return the decoded rule
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ProcessorRule decode(Object minecraftRule) {
        record Holder() {
            static final Decoder<ProcessorRule> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.templatesystem.ProcessorRuleDecoder");
        }
        return Holder.DECODER.decode(minecraftRule);
    }

    /**
     * Builder for {@link ProcessorRule}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private RuleTest inputPredicate;
        private RuleTest locationPredicate = RuleTest.alwaysTrue();
        private PosRuleTest positionPredicate = PosRuleTest.alwaysTrue();
        private BlockData outputState;
        private BlockEntityModifier blockEntityModifier = BlockEntityModifier.passthrough();

        public Builder(RuleTest inputPredicate, BlockData outputState) {
            this.inputPredicate = inputPredicate;
            this.outputState = outputState;
        }

        public Builder(ProcessorRule rule) {
            this.inputPredicate = rule.inputPredicate();
            this.locationPredicate = rule.locationPredicate();
            this.positionPredicate = rule.positionPredicate();
            this.outputState = rule.outputState();
            this.blockEntityModifier = rule.blockEntityModifier();
        }

        /**
         * Sets the test the template block has to pass.
         * @param inputPredicate the input predicate
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder inputPredicate(RuleTest inputPredicate) {
            this.inputPredicate = inputPredicate;
            return this;
        }

        /**
         * Sets the test the world block being replaced has to pass.
         * @param locationPredicate the location predicate
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder locationPredicate(RuleTest locationPredicate) {
            this.locationPredicate = locationPredicate;
            return this;
        }

        /**
         * Sets the test the block's position within the template has to pass.
         * @param positionPredicate the position predicate
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder positionPredicate(PosRuleTest positionPredicate) {
            this.positionPredicate = positionPredicate;
            return this;
        }

        /**
         * Sets the block placed when every test passes.
         * @param outputState the output state
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder outputState(BlockData outputState) {
            this.outputState = outputState;
            return this;
        }

        /**
         * Sets what happens to the placed block's block entity data.
         * @param blockEntityModifier the block entity modifier
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder blockEntityModifier(BlockEntityModifier blockEntityModifier) {
            this.blockEntityModifier = blockEntityModifier;
            return this;
        }

        /**
         * Builds the rule.
         * @return the rule
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ProcessorRule build() {
            return of(inputPredicate, locationPredicate, positionPredicate, outputState, blockEntityModifier);
        }
    }
}
