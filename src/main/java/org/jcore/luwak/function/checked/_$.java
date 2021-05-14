package org.jcore.luwak.function.checked;

import org.jcore.luwak.function.$;

/**
 * A {@link $} that allows for checked exceptions.
 */
@FunctionalInterface
public interface _$<T> {
	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	T get() throws Exception;
}