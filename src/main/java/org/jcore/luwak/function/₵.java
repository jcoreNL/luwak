package org.jcore.luwak.function;

import java.util.function.Consumer;

import org.jcore.luwak.function.checked._₵;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 *
 * @since 1.8
 */
@FunctionalInterface
public interface ₵<T> extends Consumer<T> {
	default Consumer<T> andThen(₵<? super T> after) {
		return of(Consumer.super.andThen(after));
	}

	static <T> ₵<T> of(Consumer<T> f) {
		return f::accept;
	}

	static <T> ₵<T> __(_₵<T> f) {
		return arg -> {
			try {
				f.accept(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}