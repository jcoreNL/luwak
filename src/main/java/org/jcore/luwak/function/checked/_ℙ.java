package org.jcore.luwak.function.checked;

import java.util.Objects;
import java.util.function.Predicate;

import org.jcore.luwak.function.ℙ;

/**
 * A {@link ℙ} that allows for checked exceptions.
 */
@FunctionalInterface
public interface _ℙ<T> {

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param t the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 */
	boolean test(T t) throws Exception;

	default _ℙ<T> and(_ℙ<? super T> other) {
		Objects.requireNonNull(other);
		return (t) -> test(t) && other.test(t);
	}

	default _ℙ<T> negate() {
		return (t) -> !test(t);
	}

	default _ℙ<T> or(_ℙ<? super T> other) {
		Objects.requireNonNull(other);
		return (t) -> test(t) || other.test(t);
	}

	static <T> _ℙ<T> of(Predicate<T> p) {
		return p::test;
	}

	static <T> _ℙ<T> isEqual(Object targetRef) {
		return (null == targetRef) ? Objects::isNull : targetRef::equals;
	}

	static <T> _ℙ<T> not(_ℙ<? super T> target) {
		Objects.requireNonNull(target);
		return (_ℙ<T>) target.negate();
	}
}
