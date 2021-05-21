package io.github.jevanlingen.luwak.function;

import java.util.Objects;
import java.util.function.Consumer;

import io.github.jevanlingen.luwak.function.checked._₵;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 */
@FunctionalInterface
public interface ₵<T> extends Consumer<T> {
	/**
	 * Returns a composed {@code Consumer} that performs, in sequence, this
	 * operation followed by the {@code after} operation. If performing either
	 * operation throws an exception, it is relayed to the caller of the
	 * composed operation.  If performing this operation throws an exception,
	 * the {@code after} operation will not be performed.
	 *
	 * @param after the operation to perform after this operation
	 * @return a composed {@code Consumer} that performs in sequence this operation followed by the {@code after} operation
	 * @throws NullPointerException if {@code after} is null
	 */
	default Consumer<T> andThen(₵<? super T> after) {
		return of(Consumer.super.andThen(after));
	}

	/**
	 * Returns the {@code ₵} alias of a non-{@code null} consumer function.
	 *
	 * @param consumer the consumer function
	 * @param <T>      the type of the value
	 * @return a {@code ₵} alias
	 * @throws NullPointerException if consumer is {@code null}
	 */
	static <T> ₵<T> of(Consumer<T> consumer) {
		Objects.requireNonNull(consumer);
		return consumer::accept;
	}

	/**
	 * Converts a checked consumer to a normal consumer. If a checked exception is thrown,
	 * the exception will be thrown as a RuntimeException.
	 *
	 * @param checkedConsumer the consumer function
	 * @param <T>             the type of the value
	 * @return a {@code ₵} supplying function
	 * @throws NullPointerException if checkedConsumer is {@code null}
	 * @throws RuntimeException     if checkedConsumer throws an exception
	 */
	static <T> ₵<T> __(_₵<T> checkedConsumer) {
		Objects.requireNonNull(checkedConsumer);
		return arg -> {
			try {
				checkedConsumer.accept(arg);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}