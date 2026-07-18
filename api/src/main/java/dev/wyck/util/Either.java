package dev.wyck.util;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Either one type or the other.
 * @apiNote DFU's Either will replace this Either when Codecs are available in Wyck.
 * @param <L> the left type
 * @param <R> the right type
 * @since 3.1.0
 */
@NullMarked
@AsOf("3.1.0")
public sealed interface Either<L, R> permits Either.Left, Either.Right { // can be wrappable -> DFU Either

    /**
     * The left value.
     * @param value the value to wrap
     * @param <L> the type of the value
     * @param <R> the type of the value
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    record Left<L, R>(L value) implements Either<L, R> {}

    /**
     * The right value.
     * @param value the value to wrap
     * @param <L> the type of the value
     * @param <R> the type of the value
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    record Right<L, R>(R value) implements Either<L, R> {}

    /**
     * Creates a left value.
     * @param value the value to wrap
     * @return a left value
     * @param <L> the type of the value
     * @param <R> the type of the value
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates a right value.
     * @param value the value to wrap
     * @return a right value
     * @param <L> the type of the value
     * @param <R> the type of the value
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Collapses both arms into a single result.
     * @param ifLeft mapping applied when this is a left value
     * @param ifRight mapping applied when this is a right value
     * @return the mapped result
     * @param <U> the result type
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default <U> U fold(Function<? super L, ? extends U> ifLeft, Function<? super R, ? extends U> ifRight) {
        return switch (this) {
            case Left<L, R> l -> ifLeft.apply(l.value());
            case Right<L, R> r -> ifRight.apply(r.value());
        };
    }

    /**
     * Gets the left value if this is a left.
     * @return the left value, if present
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Optional<L> left() {
        if (this instanceof Left<L, R>(L value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    /**
     * Gets the right value if this is a right.
     * @return the right value, if present
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Optional<R> right() {
        if (this instanceof Right<L, R>(R value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    /**
     * Returns a left value holding {@code value}, discarding any right value this either currently holds.
     *
     * <p>Useful when an {@code Either} backs a mutable field (e.g. a builder): a "left" setter can force the
     * value onto its left arm regardless of which arm it currently holds. The caller supplies the already-merged
     * left value, since {@code Either} cannot know how to combine two lefts.</p>
     *
     * @param value the new left value
     * @return a left value holding {@code value}
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> leftOverride(L value) {
        return Either.left(value);
    }

    /**
     * Returns a right value holding {@code value}, discarding any left value this either currently holds.
     *
     * @param value the new right value
     * @return a right value holding {@code value}
     * @see #leftOverride(Object)
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> rightOverride(R value) {
        return Either.right(value);
    }

    /**
     * Left or else.
     * @param newValue the value to use if this is a right
     * @return this either if it is a left, or a new left with the given value if it is a right
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> leftOrElse(L newValue) {
        return this instanceof Left<L, R> ? this : Either.left(newValue);
    }

    /**
     * Right or else.
     * @param newValue the value to use if this is a left
     * @return this either if it is a right, or a new right with the given value if it is a left
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> rightOrElse(R newValue) {
        return this instanceof Right<L, R> ? this : Either.right(newValue);
    }

    /**
     * Consumes the left value if present, returning this either.
     * @param consumer the consumer to apply to the left value
     * @return this either
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> consumeLeft(Consumer<? super L> consumer) {
        if (this instanceof Left<L, R>(L value)) {
            consumer.accept(value);
        }
        return this;
    }

    /**
     * Consumes the right value if present, returning this either.
     * @param consumer the consumer to apply to the right value
     * @return this either
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Either<L, R> consumeRight(Consumer<? super R> consumer) {
        if (this instanceof Right<L, R>(R value)) {
            consumer.accept(value);
        }
        return this;
    }

    /**
     * Checks if this either is empty.
     * @return true if this either is empty
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default boolean empty() {
        return left().isEmpty() && right().isEmpty();
    }
}