package org.jcore.luwak.function;

import java.util.Objects;
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
 */
@FunctionalInterface
public interface $<T> extends Supplier<T> {
	/**
	 * Returns the {@code $} alias of a non-{@code null} supplying function.
	 *
	 * @param supplier the supplying function
	 * @param <T>      the type of the value
	 * @return a {@code $} alias
	 * @throws NullPointerException if supplier is {@code null}
	 */
	static <T> $<T> of(Supplier<T> supplier) {
		Objects.requireNonNull(supplier);
		return supplier::get;
	}

	/**
	 * Converts a checked supplier to a normal supplier. If a checked exception is thrown,
	 * the exception will be thrown as a RuntimeException.
	 *
	 * @param checkedSupplier the supplying function
	 * @param <T>             the type of the value
	 * @return a {@code $} supplying function
	 * @throws NullPointerException if checkedSupplier is {@code null}
	 * @throws RuntimeException     if checkedSupplier throws an exception
	 */
	static <T> $<T> __(_$<T> checkedSupplier) {
		Objects.requireNonNull(checkedSupplier);
		return () -> {
			try {
				return checkedSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
