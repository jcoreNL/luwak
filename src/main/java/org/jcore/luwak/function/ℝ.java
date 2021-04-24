package org.jcore.luwak.function;

import org.jcore.luwak.function.checked._ℝ;

public interface ℝ extends Runnable {
	static ℝ __(_ℝ f) {
		return () -> {
			try {
				f.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
