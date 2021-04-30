package org.jcore.luwak.function;

import java.util.Objects;
import java.util.function.Function;

import org.jcore.luwak.function.checked._ƒ;

/**
 * Represents a function that accepts one argument and produces a result.
 * This is a functional interface whose functional method is apply(Object).
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ƒ<T, R> extends Function<T, R> {
	/**
	 * Returns a composed function that first applies the {@code before}
	 * function to its input, and then applies this function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param <V>    the type of input to the {@code before} function, and to the composed function
	 * @param before the function to apply before this function is applied
	 * @return a composed function that first applies the {@code before} function and then applies this function
	 * @throws NullPointerException if before is null
	 * @see #andThen(ƒ)
	 */
	default <V> ƒ<V, R> compose(ƒ<? super V, ? extends T> before) {
		return of(Function.super.compose(before));
	}

	/**
	 * Returns a composed function that first applies this function to
	 * its input, and then applies the {@code after} function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param <V>   the type of output of the {@code after} function, and of the composed function
	 * @param after the function to apply after this function is applied
	 * @return a composed function that first applies this function and then applies the {@code after} function
	 * @throws NullPointerException if after is null
	 * @see #compose(ƒ)
	 */
	default <V> ƒ<T, V> andThen(ƒ<? super R, ? extends V> after) {
		return of(Function.super.andThen(after));
	}

	/**
	 * Returns the {@code ƒ} alias of a non-{@code null} function.
	 *
	 * @param function the function
	 * @param <T>      the type of the input to the function
	 * @param <R>      the type of the result of the function
	 * @return a {@code ƒ} alias
	 * @throws NullPointerException if function is {@code null}
	 */
	static <T, R> ƒ<T, R> of(Function<T, R> function) {
		Objects.requireNonNull(function);
		return function::apply;
	}

	/**
	 * Returns a function that always returns its input argument.
	 *
	 * @param <T> the type of the input and output objects to the function
	 * @return a function that always returns its input argument
	 */
	static <T> ƒ<T, T> identity() {
		return t -> t;
	}

	/**
	 * Converts a checked function to a normal function. If a checked exception is thrown,
	 * the exception will be thrown as a RuntimeException.
	 *
	 * @param checkedFunction the function
	 * @param <T>             the type of the input to the function
	 * @param <R>             the type of the result of the function
	 * @return a {@code ƒ} function
	 * @throws NullPointerException if checkedFunction is {@code null}
	 * @throws RuntimeException     if checkedFunction throws an exception
	 */
	static <T, R> ƒ<T, R> __(_ƒ<T, R> checkedFunction) {
		Objects.requireNonNull(checkedFunction);
		return arg -> {
			try {
				return checkedFunction.apply(arg);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
