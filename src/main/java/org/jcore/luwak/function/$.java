package org.jcore.luwak.function;

import java.util.function.Supplier;

import org.jcore.luwak.function.checked._$;

/**
 * Represents a supplier of results.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 *
 * @since 1.8
 */
@FunctionalInterface
public interface $<T> extends Supplier<T> {
	static <T> $<T> of(Supplier<T> f) {
		return f::get;
	}

	static <T> $<T> __(_$<T> f) {
		return () -> {
			try {
				return f.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
