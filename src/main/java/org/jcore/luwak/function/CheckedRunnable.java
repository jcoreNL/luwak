package org.jcore.luwak.function;

/**
 * A {@link Runnable} that allows for checked exceptions.
 */
@FunctionalInterface
public interface CheckedRunnable {
	/**
	 * Run this runnable.
	 */
	void run() throws Exception;
}