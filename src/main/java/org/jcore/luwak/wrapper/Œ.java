package org.jcore.luwak.wrapper;

import java.util.Objects;

import org.jcore.luwak.function.$;
import org.jcore.luwak.function.checked._$;
import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.function.₵;
import org.jcore.luwak.function.ℙ;

/**
 * Result class, holds either a value or an exception.
 *
 * @param <V> The value
 */
public abstract class Œ<V> {
	private Œ() {
	}

	/**
	 * If a result is successful, returns the value, otherwise returns {@code other}.
	 *
	 * @param other the value to be returned, if no value is present. May be {@code null}.
	 * @return the value, if successful, otherwise {@code other}
	 */
	public abstract V orElse(final V other);

	/**
	 * If a value is successful, returns the value, otherwise returns the result
	 * produced by the supplying function.
	 *
	 * @param supplier the supplying function that produces a value to be returned
	 * @return the value, if successful, otherwise the result produced by the supplying function
	 * @throws NullPointerException if no value is present and the supplying function is {@code null}
	 */
	public abstract V orElseGet(final $<V> supplier);

	/**
	 * If a value is successful, returns an {@code Œ} describing the result of applying the given mapping function to
	 * the value, otherwise returns a failure {@code Œ}.
	 *
	 * @param <U>    The type of the value returned from the mapping function
	 * @param mapper the mapping function to apply to a value
	 * @return a {@code Œ} describing the result of applying a mapping
	 * function to the value of this {@code Œ}, if a value is successful, otherwise a failure {@code Œ}.
	 * @throws NullPointerException if the mapping function is {@code null} or returns a {@code null} result
	 */
	public abstract <U> Œ<U> map(final ƒ<V, U> mapper);

	/**
	 * If a value is successful, returns the result of applying the given
	 * {@code Œ}-bearing mapping function to the value, otherwise returns
	 * a failure {@code Œ}.
	 *
	 * <p>This method is similar to {@link #map(ƒ)}, but the mapping
	 * function is one whose result is already an {@code Œ}, and if
	 * invoked, {@code flatMap} does not wrap it within an additional {@code Œ}.
	 *
	 * @param <U>    The type of value of the {@code Ø} returned by the mapping function
	 * @param mapper the mapping function to apply to a value, if present
	 * @return the result of applying an {@code Œ}-bearing mapping
	 * function to the value of this {@code Œ}, if a value is successful, otherwise a failure {@code Œ}.
	 * @throws NullPointerException if the mapping function is {@code null} or returns a {@code null} result
	 */
	public abstract <U> Œ<U> flatMap(final ƒ<V, Œ<U>> mapper);

	/**
	 * If a value is successful, performs the given action with the value, otherwise does nothing.
	 *
	 * @param action the action to be performed, if a value is successful
	 * @throws NullPointerException if the given action is {@code null}
	 */
	public abstract void ifSuccessful(₵<V> action);

	/**
	 * If a value is successful, performs the given action with the value, otherwise throw the failure exception.
	 *
	 * @param action the action to be performed, if a value is successful
	 * @throws NullPointerException if the given action is {@code null}
	 */
	public abstract void ifSuccessfulOrThrow(₵<V> action);

	/**
	 * If a value is successful, performs the given action with the value and return an empty {@code Ø} instance.
	 * Otherwise return a present {@code Ø} instance with the failure exception as its value.
	 *
	 * @param action the action to be performed, if a value is successful
	 * @throws NullPointerException if the given action is {@code null}
	 */
	public abstract Ø<RuntimeException> ifSuccessfulOrException(₵<V> action);

	/**
	 * If a value is successful, returns an {@code Œ} describing the value,
	 * otherwise returns an {@code Œ} produced by the supplying function.
	 *
	 * @param supplier the supplying function that produces an {@code Œ} to be returned
	 * @return returns an {@code Œ} describing the value of this {@code Œ}, if a value is successful,
	 * otherwise an {@code Œ} produced by the supplying function.
	 * @throws NullPointerException if the supplying function is {@code null} or produces a {@code null} result
	 */
	public Œ<V> or(final $<Œ<V>> supplier) {
		Objects.requireNonNull(supplier);
		return map(x -> this).orElseGet(() -> Objects.requireNonNull(supplier.get()));
	}

	/**
	 * If a value is successful, and the value matches the given predicate,
	 * returns an {@code Œ} describing the value, otherwise returns a failure {@code Œ}.
	 *
	 * @param predicate the predicate to apply to a value
	 * @return an {@code Œ} describing the value of this {@code Œ},
	 * if a value is successful and the value matches the given predicate, otherwise a failure {@code Œ}
	 * @throws NullPointerException if the predicate is {@code null}
	 */
	public Œ<V> filter(final ℙ<V> predicate) {
		Objects.requireNonNull(predicate);
		return flatMap(x -> predicate.test(x)
				? this
				: failure("Condition did not match"));
	}

	/**
	 * If a value is successful, and the value matches the given predicate,
	 * returns an {@code Œ} describing the value, otherwise returns a failure {@code Œ}.
	 *
	 * @param predicate    the predicate to apply to a value
	 * @param errorMessage the failure message if the value does not match the predicate
	 * @return an {@code Œ} describing the value of this {@code Œ},
	 * if a value is successful and the value matches the given predicate, otherwise a failure {@code Œ}
	 * @throws NullPointerException if the predicate is {@code null}
	 */
	public Œ<V> filter(final ℙ<V> predicate, String errorMessage) {
		return flatMap(x -> predicate.test(x)
				? this
				: failure(errorMessage));
	}

	/**
	 * If a value is successful, returns {@code true}, otherwise {@code false}.
	 *
	 * @return {@code true} if a value is successful, otherwise {@code false}
	 */
	public boolean isSuccessFul() {
		return isSuccessFul(a -> true);
	}

	/**
	 * If a value is successful, and the value matches the given predicate,
	 * returns {@code true}, otherwise {@code false}.
	 *
	 * @param predicate the predicate to apply to a value
	 * @return {@code true} if a value is successful and matches the given predicate, otherwise {@code false}
	 */
	public boolean isSuccessFul(final ℙ<V> predicate) {
		return map(predicate::test).orElse(false);
	}

	private static class Failure<V> extends Œ<V> {
		private final RuntimeException exception;

		private Failure(final String message) {
			this.exception = new IllegalStateException(message);
		}

		private Failure(final RuntimeException exception) {
			this.exception = exception;
		}

		private Failure(final Exception e) {
			this.exception = new IllegalStateException(e.getMessage(), e);
		}

		@Override
		public V orElse(V other) {
			return other;
		}

		@Override
		public V orElseGet($<V> supplier) {
			return supplier.get();
		}

		@Override
		public <U> Œ<U> map(ƒ<V, U> mapper) {
			Objects.requireNonNull(mapper);
			return failure(exception);
		}

		@Override
		public <U> Œ<U> flatMap(ƒ<V, Œ<U>> mapper) {
			Objects.requireNonNull(mapper);
			return failure(exception);
		}

		@Override
		public void ifSuccessful(₵<V> action) {
			Objects.requireNonNull(action);
		}

		@Override
		public void ifSuccessfulOrThrow(₵<V> action) {
			Objects.requireNonNull(action);
			throw exception;
		}

		@Override
		public Ø<RuntimeException> ifSuccessfulOrException(₵<V> action) {
			Objects.requireNonNull(action);
			return Ø.of(exception);
		}

		@Override
		public String toString() {
			return exception.getMessage() == null
					? "Failure (" + exception + ")"
					: "Failure (" + exception.getMessage() + ")";
		}
	}

	private static class Success<V> extends Œ<V> {
		private final V value;

		private Success(final V value) {
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public V orElse(V other) {
			return value;
		}

		@Override
		public V orElseGet($<V> supplier) {
			return value;
		}

		@Override
		public <U> Œ<U> map(ƒ<V, U> mapper) {
			try {
				return of(Objects.requireNonNull(mapper.apply(value)));
			}
			catch (Exception e) {
				return failure(e);
			}
		}

		@Override
		public <U> Œ<U> flatMap(ƒ<V, Œ<U>> mapper) {
			try {
				return Objects.requireNonNull(mapper.apply(value));
			}
			catch (Exception e) {
				return failure(e);
			}
		}

		@Override
		public void ifSuccessful(₵<V> action) {
			Objects.requireNonNull(action);
			action.accept(value);
		}

		@Override
		public void ifSuccessfulOrThrow(₵<V> action) {
			ifSuccessful(action);
		}

		@Override
		public Ø<RuntimeException> ifSuccessfulOrException(₵<V> action) {
			ifSuccessful(action);
			return Ø.empty();
		}

		@Override
		public String toString() {
			return "Success (" + value + ")";
		}
	}

	/**
	 * Returns a failure {@code Œ} instance.
	 *
	 * @param errorMessage the failure message
	 * @param <V>          The type of the failure
	 * @return a failure {@code Œ}
	 */
	public static <V> Œ<V> failure(final String errorMessage) {
		return new Failure<>(errorMessage);
	}

	/**
	 * Returns a failure {@code Œ} instance.
	 *
	 * @param exception the exception
	 * @param <V>       The type of the failure
	 * @return a failure {@code Œ}
	 * @throws NullPointerException if exception is {@code null}
	 */
	public static <V> Œ<V> failure(final Exception exception) {
		Objects.requireNonNull(exception);

		if (exception instanceof RuntimeException) {
			return new Failure<>((RuntimeException) exception);
		}

		return new Failure<>(exception);
	}

	/**
	 * Returns a {@code Œ} describing the given non-{@code null} value.
	 *
	 * @param value the value to describe, which must be non-{@code null}
	 * @param <V>   the type of the value
	 * @return a {@code Œ} with the successful value
	 * @throws NullPointerException if value is {@code null}
	 */
	public static <V> Œ<V> of(final V value) {
		return new Success<>(value);
	}

	/**
	 * Matches non-{@code null} value to given predicate, if so it returs a {@code Œ}
	 * describing the given non-{@code null} value, otherwise returns a failure {@code Œ}.
	 *
	 * @param predicate the predicate to apply to the value
	 * @param value     the value to describe, which must be non-{@code null}
	 * @param <V>       the type of the value
	 * @return a {@code Œ} with the result of the function, otherwise returns a failure {@code Œ}.
	 * @throws NullPointerException if predicate or value is {@code null}
	 */
	public static <V> Œ<V> of(ℙ<V> predicate, final V value) {
		return of(predicate, value, "Condition did not match");
	}

	/**
	 * Matches non-{@code null} value to given predicate, if so it returs a {@code Œ}
	 * describing the given non-{@code null} value, otherwise returns a failure {@code Œ} with given error message.
	 *
	 * @param predicate    the predicate to apply to the value
	 * @param value        the value to describe, which must be non-{@code null}
	 * @param errorMessage the failure message if the value is {@code null}
	 * @param <V>          the type of the value
	 * @return a {@code Œ} with the result of the function, otherwise returns a failure {@code Œ}.
	 * @throws NullPointerException if predicate or value is {@code null}
	 */
	public static <V> Œ<V> of(ℙ<V> predicate, final V value, final String errorMessage) {
		Objects.requireNonNull(predicate);
		try {
			return predicate.test(value)
					? of(value)
					: failure(errorMessage);
		}
		catch (Exception e) {
			return failure(new IllegalStateException("Exception while evaluating: " + value, e));
		}
	}

	/**
	 * Returns a {@code Œ} describing the given value, if
	 * non-{@code null}, otherwise returns a failure {@code Œ} with given error message.
	 *
	 * @param value        the possibly-{@code null} value to describe
	 * @param errorMessage the failure message if the value is {@code null}
	 * @param <V>          the type of the value
	 * @return a {@code Œ} with a value if the specified value is non-{@code null}, otherwise a failure {@code Ø}
	 */
	public static <V> Œ<V> ofNullable(final V value, final String errorMessage) {
		return value == null
				? failure(errorMessage)
				: of(value);
	}

	/**
	 * Executes supplying function, if successful it returns a {@code Œ} with the result of the function,
	 * otherwise returns a failure {@code Œ}.
	 *
	 * @param supplier the supplying function that produces an {@code Œ} to be returned
	 * @param <V>      the type of the value
	 * @return a {@code Œ} with the result of the function, otherwise returns a failure {@code Œ}.
	 */
	public static <V> Œ<V> doTry(_$<V> supplier) {
		Objects.requireNonNull(supplier);
		try {
			return of(supplier.get());
		}
		catch (Exception e) {
			return failure(e);
		}
	}
}
