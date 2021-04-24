package org.jcore.luwak.function;

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
public interface ƒ<T, R> extends Function<T,R> {
	default <V> ƒ<V, R> compose(ƒ<? super V, ? extends T> before) {
		return of(Function.super.compose(before));
	}

	default <V> ƒ<T, V> andThen(ƒ<? super R, ? extends V> after) {
		return of(Function.super.andThen(after));
	}

	static <T, R> ƒ<T, R> of(Function<T, R> f) {
		return f::apply;
	}

	static <T> ƒ<T, T> identity() {
		return t -> t;
	}

	static <T, R> ƒ<T, R> __(_ƒ<T, R> f) {
		return arg -> {
			try {
				return f.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
