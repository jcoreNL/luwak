package org.jcore.luwak.function;

import java.util.function.Supplier;

/**
 * A {@link Supplier} that allows for checked exceptions.
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	T get() throws Exception;
}
