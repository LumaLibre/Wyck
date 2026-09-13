package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Replaces template blocks according to a list of rules, the first matching rule winning. It is how villages turn cobblestone mossy and zombie villages break their glass.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface RuleProcessor extends StructureProcessor {

    /**
     * The rules tried in order.
     * @return the rules tried in order
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<ProcessorRule> rules();

    /**
     * Creates a new processor.
     * @param rules the rules tried in order
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RuleProcessor of(List<ProcessorRule> rules) {
        record Holder() {
            static final ConstructWireProvider<RuleProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.RuleProcessorImpl");
        }
        return Holder.WIRE.construct(List.copyOf(rules));
    }
}
