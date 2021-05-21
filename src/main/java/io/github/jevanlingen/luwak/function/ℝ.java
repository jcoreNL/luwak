package io.github.jevanlingen.luwak.function;

import java.util.Objects;

import io.github.jevanlingen.luwak.function.checked._ℝ;

public interface ℝ extends Runnable {
	/**
	 * Converts a checked runnable to a normal runnable. If a checked exception is thrown,
	 * the exception will be thrown as a RuntimeException.
	 *
	 * @param checkedRunnable the supplied runnable
	 * @return a {@code ℙ} runnable
	 * @throws NullPointerException if checkedRunnable is {@code null}
	 * @throws RuntimeException     if checkedRunnable throws an exception
	 */
	static ℝ __(_ℝ checkedRunnable) {
		Objects.requireNonNull(checkedRunnable);
		return () -> {
			try {
				checkedRunnable.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
