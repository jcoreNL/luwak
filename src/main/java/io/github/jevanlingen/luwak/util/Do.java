package io.github.jevanlingen.luwak.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.jevanlingen.luwak.function.ƒ;
import io.github.jevanlingen.luwak.function.ℙ;
import io.github.jevanlingen.luwak.wrapper.Ø;

public final class Do {
	private Do() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/* ---------------------------------------------------- LIST ---------------------------------------------------- */

	/**
	 * Returns a unmodifiable list consisting of the results of applying the given
	 * function to the elements of this list.
	 *
	 * @param <T> The element type of the old list
	 * @param <R> The element type of the new list
	 * @param list the list to be mapped
	 * @param mapper a function to apply to each element
	 * @return the new list
	 * @throws NullPointerException if either list or mapper are null
	 */
	public static <T, R> List<R> map(final List<T> list, final ƒ<T, R> mapper) {
		return list.stream().map(mapper).collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Returns a unmodifiable list consisting of the elements of this list that match the given predicate.
	 *
	 * @param <T> The element type of the list
	 * @param list the list to be filtered
	 * @param predicate a predicate to apply to each element to determine if it should be included
	 * @return the new list
	 * @throws NullPointerException if either list or predicate are null
	 */
	public static <T> List<T> filter(final List<T> list, final ℙ<T> predicate) {
		return list.stream().filter(predicate).collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Returns whether any elements of this list match the provided
	 * predicate. May not evaluate the predicate on all elements if not
	 * necessary for determining the result. If the list is empty then
	 * {@code false} is returned and the predicate is not evaluated.
	 *
	 * @param <T> The element type of the list
	 * @param list the list to be matched
	 * @param predicate a predicate to apply to elements of this list
	 * @return {@code true} if any elements of the list match the provided predicate, otherwise {@code false}
	 * @throws NullPointerException if either list or predicate are null
	 */
	public static <T> boolean anyMatch(final List<T> list, final ℙ<T> predicate) {
		return list.stream().anyMatch(predicate);
	}

	/**
	 * Returns an {@code Ø} describing some element of the list, or an
	 * empty {@code Ø} if the list is empty.
	 *
	 * <p>The behavior of this operation is explicitly nondeterministic; it is
	 * free to select any element in the list. This is to allow for maximal
	 * performance in parallel operations; the cost is that multiple invocations
	 * on the same source may not return the same result.
	 *
	 * @param <T> The element type of the list
	 * @param list the list to be searched through
	 * @param predicate a predicate to apply to elements of this list
	 * @return an {@code Ø} describing some element of this list, or an empty {@code Ø} if the list is empty
	 * @throws NullPointerException if either list or predicate are null
	 */
	public static <T> Ø<T> findAny(final List<T> list, final ℙ<T> predicate) {
		return Ø.of(list.stream().filter(predicate).findAny());
	}

	/* ---------------------------------------------------- SET ----------------------------------------------------- */

	/**
	 * Returns a unmodifiable set consisting of the results of applying th\e given
	 * function to the elements of this set.
	 *
	 * @param <T> The element type of the old set
	 * @param <R> The element type of the new set
	 * @param set the set to be mapped
	 * @param mapper a function to apply to each element
	 * @return the new set
	 * @throws NullPointerException if either set or mapper are null
	 */
	public static <T, R> Set<R> map(final Set<T> set, final ƒ<T, R> mapper) {
		return set.stream().map(mapper).collect(Collectors.toUnmodifiableSet());
	}

	/**
	 * Returns a unmodifiable set consisting of the elements of this set that match the given predicate.
	 *
	 * @param <T> The element type of the set
	 * @param set the set to be filtered
	 * @param predicate a predicate to apply to each element to determine if it should be included
	 * @return the new set
	 * @throws NullPointerException if either set or predicate are null
	 */
	public static <T> Set<T> filter(final Set<T> set, final ℙ<T> predicate) {
		return set.stream().filter(predicate).collect(Collectors.toUnmodifiableSet());
	}

	/**
	 * Returns whether any elements of this set match the provided
	 * predicate. May not evaluate the predicate on all elements if not
	 * necessary for determining the result. If the set is empty then
	 * {@code false} is returned and the predicate is not evaluated.
	 *
	 * @param <T> The element type of the set
	 * @param set the set to be matched
	 * @param predicate a predicate to apply to elements of this set
	 * @return {@code true} if any elements of the set match the provided predicate, otherwise {@code false}
	 * @throws NullPointerException if either set or predicate are null
	 */
	public static <T> boolean anyMatch(final Set<T> set, final ℙ<T> predicate) {
		return set.stream().anyMatch(predicate);
	}

	/**
	 * Returns an {@code Ø} describing some element of the set, or an
	 * empty {@code Ø} if the set is empty.
	 *
	 * <p>The behavior of this operation is explicitly nondeterministic; it is
	 * free to select any element in the set. This is to allow for maximal
	 * performance in parallel operations; the cost is that multiple invocations
	 * on the same source may not return the same result.
	 *
	 * @param <T> The element type of the set
	 * @param set the set to be searched through
	 * @param predicate a predicate to apply to elements of this set
	 * @return an {@code Ø} describing some element of this set, or an empty {@code Ø} if the set is empty
	 * @throws NullPointerException if either set or predicate are null
	 */
	public static <T> Ø<T> findAny(final Set<T> set, final ℙ<T> predicate) {
		return Ø.of(set.stream().filter(predicate).findAny());
	}

	/* --------------------------------------------------- STREAM --------------------------------------------------- */

	/**
	 * Returns an {@code Ø} describing some element of the stream, or an
	 * empty {@code Ø} if the stream is empty.
	 *
	 * <p>The behavior of this operation is explicitly nondeterministic; it is
	 * free to select any element in the set. This is to allow for maximal
	 * performance in parallel operations; the cost is that multiple invocations
	 * on the same source may not return the same result.
	 *
	 * @param <T> The element type of the set
	 * @param stream the stream to be searched through
	 * @param predicate a predicate to apply to elements of this stream
	 * @return an {@code Ø} describing some element of this set, or an empty {@code Ø} if the set is empty
	 * @throws NullPointerException if either set or predicate are null
	 */
	public static <T> Ø<T> findAny(final Stream<T> stream, final ℙ<T> predicate) {
		return Ø.of(stream.filter(predicate).findAny());
	}
}
