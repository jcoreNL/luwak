package org.jcore.luwak.function.checked;

import java.util.Objects;

import org.jcore.luwak.function.₵;

/**
 * A {@link ₵} that allows for checked exceptions.
 */
@FunctionalInterface
public interface _₵<T> {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument
	 */
	void accept(T t) throws Exception;

	default _₵<T> andThen(_₵<? super T> after) {
		Objects.requireNonNull(after);
		return (T t) -> { accept(t); after.accept(t); };
	}
}