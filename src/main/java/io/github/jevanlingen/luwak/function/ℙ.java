package io.github.jevanlingen.luwak.function;

import java.util.Objects;
import java.util.function.Predicate;

import io.github.jevanlingen.luwak.function.checked._ℙ;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to the predicate
 */
@FunctionalInterface
public interface ℙ<T> extends Predicate<T> {
	/**
	 * Returns a composed predicate that represents a short-circuiting logical
	 * OR of this predicate and another.  When evaluating the composed
	 * predicate, if this predicate is {@code true}, then the {@code other}
	 * predicate is not evaluated.
	 *
	 * <p>Any exceptions thrown during evaluation of either predicate are relayed
	 * to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other a predicate that will be logically-ORed with this predicate
	 * @return a composed predicate that represents the short-circuiting logical
	 * OR of this predicate and the {@code other} predicate
	 * @throws NullPointerException if other is null
	 */
	default ℙ<T> and(ℙ<? super T> other) {
		return of(Predicate.super.and(other));
	}

	/**
	 * Returns a predicate that represents the logical negation of this predicate.
	 *
	 * @return a predicate that represents the logical negation of this predicate
	 */
	default ℙ<T> negate() {
		return of(Predicate.super.negate());
	}

	/**
	 * Returns a composed predicate that represents a short-circuiting logical
	 * OR of this predicate and another.  When evaluating the composed
	 * predicate, if this predicate is {@code true}, then the {@code other}
	 * predicate is not evaluated.
	 *
	 * <p>Any exceptions thrown during evaluation of either predicate are relayed
	 * to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other a predicate that will be logically-ORed with this predicate
	 * @return a composed predicate that represents the short-circuiting logical
	 * OR of this predicate and the {@code other} predicate
	 * @throws NullPointerException if other is null
	 */
	default ℙ<T> or(ℙ<? super T> other) {
		return of(Predicate.super.or(other));
	}

	/**
	 * Returns the {@code ℙ} alias of a non-{@code null} predicate.
	 *
	 * @param predicate the predicate
	 * @param <T>       the type of arguments to the specified predicate
	 * @return a {@code predicate} alias
	 * @throws NullPointerException if predicate is {@code null}
	 */
	static <T> ℙ<T> of(Predicate<T> predicate) {
		Objects.requireNonNull(predicate);
		return predicate::test;
	}

	/**
	 * Returns a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}.
	 *
	 * @param <T>       the type of arguments to the predicate
	 * @param targetRef the object reference with which to compare for equality, which may be {@code null}
	 * @return a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}
	 */
	static <T> ℙ<T> isEqual(Object targetRef) {
		return (null == targetRef) ? Objects::isNull : targetRef::equals;
	}

	/**
	 * Returns a predicate that is the negation of the supplied predicate.
	 *
	 * @param <T>    the type of arguments to the specified predicate
	 * @param target predicate to negate
	 * @return a predicate that negates the results of the supplied predicate
	 * @throws NullPointerException if target is null
	 */
	@SuppressWarnings("unchecked")
	static <T> ℙ<T> not(ℙ<? super T> target) {
		Objects.requireNonNull(target);
		return (ℙ<T>) target.negate();
	}

	/**
	 * Converts a checked predicate to a normal predicate. If a checked exception is thrown,
	 * the exception will be thrown as a RuntimeException.
	 *
	 * @param checkedPredicate the supplying function
	 * @param <T>              the type of arguments to the specified predicate
	 * @return a {@code ℙ} predicate
	 * @throws NullPointerException if checkedPredicate is {@code null}
	 * @throws RuntimeException     if checkedPredicate throws an exception
	 */
	static <T> ℙ<T> __(_ℙ<T> checkedPredicate) {
		Objects.requireNonNull(checkedPredicate);
		return arg -> {
			try {
				return checkedPredicate.test(arg);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
