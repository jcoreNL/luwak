package io.github.jevanlingen.luwak.function.checked;

import io.github.jevanlingen.luwak.function.ℝ;

/**
 * A {@link ℝ} that allows for checked exceptions.
 */
@FunctionalInterface
public interface _ℝ {
	/**
	 * Run this runnable.
	 */
	void run() throws Exception;
}