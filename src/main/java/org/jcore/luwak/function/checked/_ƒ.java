package org.jcore.luwak.function.checked;

import java.util.Objects;

import org.jcore.luwak.function.ƒ;

/**
 * A {@link ƒ} that allows for checked exceptions.
 */
@FunctionalInterface
public interface _ƒ<T, R> {
	R apply(T t) throws Exception;

	default <V> _ƒ<V, R> compose(_ƒ<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}

	default <V> _ƒ<T, V> andThen(_ƒ<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}

	static <T> _ƒ<T, T> identity() {
		return t -> t;
	}
}
