package org.jcore.luwak.function;

import java.util.function.Consumer;

/**
 * A {@link Consumer} that allows for checked exceptions.
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param t the input argument
	 */
	void accept(T t) throws Exception;
}