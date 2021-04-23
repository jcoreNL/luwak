package org.jcore.luwak.function;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to the predicate
 *
 * @since 1.8
 */
@FunctionalInterface
public interface ℙ<T> extends Predicate<T> {
	default ℙ<T> and(ℙ<? super T> other) {
		return of(Predicate.super.and(other));
	}

	default ℙ<T> negate() {
		return of(Predicate.super.negate());
	}

	default ℙ<T> or(ℙ<? super T> other) {
		return of(Predicate.super.or(other));
	}

	static <T> ℙ<T> of(Predicate<T> p) {
		return p::test;
	}

	static <T> ℙ<T> isEqual(Object targetRef) {
		return (null == targetRef) ? Objects::isNull : targetRef::equals;
	}

	static <T> ℙ<T> not(ℙ<? super T> target) {
		Objects.requireNonNull(target);
		return (ℙ<T>) target.negate();
	}
}
